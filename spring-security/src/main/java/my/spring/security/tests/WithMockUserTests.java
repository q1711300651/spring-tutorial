package my.spring.security.tests;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import my.spring.security.tests.annotations.WithMockCustomUser;
import my.spring.security.tests.example.MessageService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ComponentScan(basePackages = "my.spring.security.tests.example.*")
public class WithMockUserTests {

    @Inject
    private MessageService messageService;

    /**
     * Данный тест будет выполнен из под пользователем user с паролем password и ролью ROLE_USER.
     * Так же можно указать на уровне класса, для запуска все
     */
    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testMessageWithMockUser() throws Exception {
        final String message = messageService.getMessage();
        System.out.println(message);
    }

    /**
     * Осуществляет запрос к UserDetailsService перед выполнением теста.
     * Как и WithMockUser можно указывать на уровне класса
     */
    @Test
    @WithUserDetails("user")
    public void testMessageWithUserDetails() throws Exception {
        final String message = messageService.getMessage();
        System.out.println(message);
    }

    /**
     * Пример реализации теста с собственным контекстом Spring Security
     * и подключением через аннотацию
     */
    @Test
    @WithMockCustomUser(name = "customName", username = "user")
    public void testMessageWithCustomContext() throws Exception {
        final String message = messageService.getMessage();
        System.out.println(message);
    }

}


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
class WebSecurityTests {

    @Inject
    private WebApplicationContext context;

    private MockMvc mockMvc;

    /**
     * Для реализации тестов с веб и Spring Security можно воспользоваться
     * SecurityMockMvcConfigurers.springSecurity() - предоставит все инициализационные установки для работы с
     * тестовым mockMvc
     */
    @Before
    public void startup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     *  Пример добавление пользователя к тестовому запросу
     */
    @Test
    public void runWithUser() throws Exception {
        // Пользователь с ролью
        mockMvc.perform(get("/").with(user("user").roles("ADMIN")));

        // передать пользовательские деталям
        UserDetails userDetails = null;
        mockMvc.perform(get("/").with(user(userDetails)));

        // передать атутенификационный обьект
        Authentication authentication = null;
        mockMvc.perform(get("/").with(authentication(authentication)));

        // передать контекст
        SecurityContext context = null;
        mockMvc.perform(get("/").with(securityContext(context)));
    }

    /**
     * Так же можно использовать аннотации
     */
    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    public void runWithUserAnnotaion() throws Exception {
        mockMvc.perform(get("/"));
    }

    /**
     * Пример простых способов форимрования запросов для Spring Security
     */
    @Test
    public void testRequestBuilder() throws Exception {

        /*
            Данный метод выполнят пост запрос по урлу
            /login с параметрами:
                username = user
                password = password
         */
        mockMvc.perform(formLogin());

        /*
            Настройка собственных параметров логина
         */
        mockMvc.perform(formLogin("/myLogin")
                .user("userKey", "userValue")
                .password("passwordKey", "passwordValue"));


        /*
            Метод для тестирования логаута
         */
        mockMvc.perform(SecurityMockMvcRequestBuilders.logout("/myLogout"));

    }

    /**
     * Пример простых способов проверки результатов запросов Spring Security
     */
    @Test
    public void testRequestBuilderMatchers() throws Exception {

        /*
            Пример проверки результата на неверный логин
         */
        mockMvc.perform(formLogin().password("invalid"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());

        /*
            Проверка авторизации админа
         */
        mockMvc.perform(formLogin().user("admin"))
                .andExpect(authenticated().withRoles("USER", "ADMIN"));

    }
}
