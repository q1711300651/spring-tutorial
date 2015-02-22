package my.spring.testing;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = ConfigApp.class)
@ActiveProfiles("dev")

public class ConfigClassApplicationContextTests {
}
