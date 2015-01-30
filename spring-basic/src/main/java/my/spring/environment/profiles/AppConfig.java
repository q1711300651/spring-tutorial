package my.spring.environment.profiles;

import my.spring.environment.ContextParam;
import my.spring.environment.profiles.annotations.Developer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 *  Пример раализции конфига с несколькими профалами ( вариантами зборки контекста )
 */
public class AppConfig {

    /**
     * Првый метод, использует состовную конфигурацию
     */
    @Developer @Bean(name = "ctxParam")
    public ContextParam contextDevParam() {
        ContextParam contextParam = new ContextParam();
        contextParam.setType( "Dev" );
        return contextParam;
    }

    /**
     * Второй метод, реализует отрицателный зависимоть к профайлу dev
     */
    @Profile( "!dev" ) @Bean(name = "ctxParam")
    public ContextParam contextNotDevParam() {
        ContextParam contextParam = new ContextParam();
        contextParam.setType( "Not Dev" );
        return contextParam;
    }

    /**
     * Профалер по умолчанию. Если не указан не один профайлер то будет взят этот по умолчанию,
     * иначе данный бин не инициализируеться
     */
    @Profile( "default" ) @Bean(name = "ctxDefParam")
    public ContextParam contextDefaultParam() {
        ContextParam contextParam = new ContextParam();
        contextParam.setType( "Default" );
        return contextParam;
    }


}
