package my.spring.security.data;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

/**
 * Пример интеграции Spring Security с Spring Data
 */
public class DataIntegrationSample {


    /**
     * Интеграция со Spring Data через Java конфигурацию
     * XML аналог:
     *      <bean class="org.springframework.security.data.repository.query.SecurityEvaluationContextExtension"/>
     */
    @Bean
    private SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    /**
     * Пример реализации запроса с использованием параметров Spring Security
     * Так же смотри:
     *      spring-security/references/el verification/Common Built-In Expressions.txt
     */
    public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

        @Query("SELECT m FROM Message m where m.id = ?#{proncepal?.id}")
        Page<Message> findInbox(Pageable pageable);
    }

    public class Message {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(final long id) {
            this.id = id;
        }
    }

}
