package my.spring.testing;
;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Демонтсрация выполнение теста для веб среды
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = "classpath:/testing/webapp/bean.xml")
@WebAppConfiguration
public class TestRequestScopeBeans {

    @Inject
    private UserService userService;
    @Inject
    private MockHttpServletRequest request;
    @Inject
    private MockHttpSession session;


    /**
     * Тест использует бины с циклом жизни "запрос"
     */
    @Test
    public void requestScopeTest() {
        request.setParameter( "user", "new user" );
        request.setParameter( "pswd", "new password" );
        String result = userService.loginUser();
        assertEquals("user new user has login with password new password", result);
    }

    /**
     * Тест для с сессией
     */
    @Test
    public void sessionScope() {
        session.setAttribute( "theme", "blue" );
        UserPreferences preferences = userService.preferences();

        assertNotNull( preferences );
        assertEquals( "blue", preferences.getTheme()  );
    }

}
