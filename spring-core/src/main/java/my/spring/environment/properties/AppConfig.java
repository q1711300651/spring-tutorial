package my.spring.environment.properties;

import my.spring.environment.ContextParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Configuration
// получить источник настроек по динамически формировнному пути
@PropertySource( "classpath:${default.property.source}/include.properties" )
public class AppConfig {

    @Inject
    private Environment environment;

    @Bean
    public ContextParam ctxParam() {
        ContextParam contextParam = new ContextParam();

        //Получение параметров из среды, и заполнение их в качкстве полей бина
        contextParam.setType( environment.getProperty( "insert.property.value" ) );
        return contextParam;
    }

}
