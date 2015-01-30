package my.spring.configuration.parent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Пример родительского класса конфигурации, который импортиться через атрибут @Import в классе AppConfig
 */
@Configuration
public class ParentAppConfig {

    @Bean
    public ParentService myParentService() {
        return new ParentService();
    }
}
