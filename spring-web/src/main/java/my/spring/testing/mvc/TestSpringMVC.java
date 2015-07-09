package my.spring.testing.mvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringJUnit4ClassRunner.class )
@WebAppConfiguration
@ContextConfiguration("classpath:/testing/mvc/bean.xml")
public class TestSpringMVC {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * Для настройки тестовго мок мвц нужно внедрить веб контекст, что позволит
     * проверить среду более полно
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( wac )
                .alwaysExpect( status().isOk() ) // установка проверки для всех MockMvc запросов, нельзя переопределить
                // .addFilters(  ) можно так же регестрировать фильтры
                .build();
    }
//  Либо внедрить класс контроллер на прямую, без зазрузки контеста и сфокусироваться только на веб части
//  что более близко в модульному тестировнию, так же можно добавит мок сервисы для контроллера через standaloneSetup
//    @Before
//    public void setUp2() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup( YourConroller ).build();
//    }

    /**
     * Этот тест использует mockMvc, что бы иметировать get запрос, и проверяет вернувшиеся параметры
     */
    @Test
    public void getAccount() throws Exception {
        /*
        метод perform предостволяет разные варианты для имитирования http запроса
        post("/hotels/{id}", 42) Пост
        fileUpload("/doc").file("a1", "ABC".getBytes("UTF-8")) загрзука файла
        get("/hotels?foo={foo}", "bar") передача параметров через шаблоны URI
        get("/hotels").param("foo", "bar") или через сервлет параметры
         */

        this.mockMvc.perform( get( "/account/1" ) // тип и путь запроса
                .accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) ) // меди тип запроса
                /*
                    MockMvcResultMatchers.* предостоляет множество стандартных стратическиз проверок на
                    полученный результат. Проверки деляться на две категории:
                        1. Проверки полей ответа
                        2. Проверки специфичные к Spring MVC (какие исключение был вызваны/обарботанны,
                           какой контент модели, какое представление было выбранно и т.п):

                                mockMvc.perform(post("/persons"))
                                    .andExpect(status().isOk())
                                    .andExpect(model().attributeHasErrors("person"));

                        Получить прямой доступ к резульату запроса можно так:
                        MvcResult mvcResult = mockMvc.perform(post("/persons")).andExpect(status().isOk()).andReturn();

                 */
                .andDo( print() ) // Метод выведет результат в System.out
                .andExpect( status().isOk() ) // проверка на статус
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) ) // проеврка типа ответа
                .andExpect( jsonPath( "$.name" ).value( "Lee" ) ); // парсинг и проверка поля name в json теле
    }

}
