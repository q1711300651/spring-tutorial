package my.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 *  Пример конфигурации spring security через java класс
 *  Подобная конфигурация позволяет:
 *      1.Выполнить запрос на авторизацию на кажлый урл
 *      2.сгенерировать форму login
 *      3.позволяет пользователю с именем "user" и паролем "password", авторизироваться как пользователь
 *      4.позволяет выйти
 *
 *  Подобную конфигурацию можно использоваель паралельно с другой конфигурацией Security, есдинственное что
 *  нужно это наследоваться от WebSecurityConfigurerAdapter
 *
 */
public class JavaBasedConfiguration extends WebSecurityConfigurerAdapter {


    /**
     * Конфигурация правили авторизации
     *
     * Простая авторизация в на соотетсвие
     */
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser( "user" ).password( "password" ).roles( "USER" ).and()
                .withUser( "admin" ).password( "password" ).roles( "USER", "ADMIN" );
    }

    /**
     * Конфигурация правили авторизации
     *
     * Авторизация через JDBC
     */
    @Autowired
    public void configureGlobal_2( AuthenticationManagerBuilder auth ) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource( null )
                .withDefaultSchema()
                .withUser( "user" ).password( "password" ).roles( "USER" ).and()
                .withUser( "admin" ).password( "password" ).roles( "USER", "ADMIN" );
    }

    /**
     * Конфигурация правили авторизации
     *
     * Авторизация через LDAP
     *
     * Данный пример использует конфигурационный файл:
     *          references/ldap/users.ldif
     */
    @Autowired
    public void configureGlobal_3( AuthenticationManagerBuilder auth ) throws Exception {
        auth
            .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups");
    }
    /**
     * SecurityConfig - содержит информацию о том как авторизировать пользователей.
     * Поведение можно переопределить в методе configure( HttpSecurity http )
     */
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
            .authorizeRequests() // запрос на атвторизацию
                .anyRequest() // любой запрос
                .authenticated() // авторизировать
                .and() // и
            .formLogin() // отправить на форму авторизации
                .and() // и
            .httpBasic(); // авторизировать пользователя с базовой авторизацией
    }

    /**
     * Пример регистрации собственной страниц авторизации
     */
    protected void configure_2( HttpSecurity http ) throws Exception {
        http
            .authorizeRequests() // запрос на атвторизацию
                .anyRequest() // любой запрос
                .authenticated() // авторизировать
                .and() // и
            .formLogin() // отправить на форму авторизации
                .loginPage( "/loginPage" ) // регистраци ссылки на страницу с формой авторизации
                .permitAll(); // Указывам что доступ к этой страницы разрешон всем пользователям,
                              // не в зависимости от авторизации
    }

    /**
     * Пример регистрации настройки прав доступа для урл запросов
     */
    protected void configure_3( HttpSecurity http ) throws Exception {
        http
            .authorizeRequests() // запрос на атвторизацию
                .antMatchers( "/resources/**", "/signup", "/about" ).permitAll() // открыть доступ всем для
                                                                                // следующий урлов
                .antMatchers( "admin/**" ).hasRole( "ADMIN" ) // доступ только пользователю с ролью ADMIN
                .antMatchers( "/db/**" ).access( "hasRole('ADMIN') and hasRole('DBA')" ) // открыть доступ если
                                                                                        // выполниться булевое условие
                .anyRequest().authenticated()
                .and();
        // ...
    }

    /**
     * Настройка передачи хедеров
     */
    protected void configure_heeders( HttpSecurity http ) throws Exception {
        http
                .headers() // настройка хедеров
                    .defaultsDisabled() // отключение хедеров по умолчанию
                    .frameOptions() // настройка правил поведение с фреймам
                        .sameOrigin() // SAMEORIGIN смотри xml описание или .deny()
                    .contentTypeOptions() // Предовтращает "угадывание" загружаемых файлов для защиты от XSS
                        .and()
                .cacheControl() // отключить кеш для security запросов
                    .and()
                .httpStrictTransportSecurity() // Настройка HSTS смотри xml
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                .and().addHeaderWriter(new StaticHeadersWriter("key", "value")) // добаляет соственный хедер
                .disable(); // отключение хедеров
    }

    /**
     * Настройка исключений для не кешированных ссылок в Spring Security
     * Обычно используеться для подгрузки в кеш браузера ресурсных файлов, таких как JavaScript, CSS
     */
    protected void configure_heeders(ResourceHandlerRegistry registry) throws Exception {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(31556926);
    }
}
