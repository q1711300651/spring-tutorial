package my.spring.dao.orm;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;

/**
 * Пример реализации трансляции исключение фреймоврок в исколючение собственные доченрние исключения
 * Spring DataAccessException
 *
 * <beans>
 *     что бы добавить в проект трнаслятор исключений нужно сделать его бином, тогда все @Repository анотированные
 *     классы, будут автоматичекси подкюченны к транслятору
 *     <bean class="org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor"/>
 *     <bean id="myProductDao" class="product.ProductDaoImpl"/>
 * </beans>
 */
public class ExampleORMExceptionTranslator {


    public static class ExampleHibernateExceptionTranslator extends HibernateExceptionTranslator {

        @Override
        protected DataAccessException convertHibernateAccessException( HibernateException ex ) {
            return super.convertHibernateAccessException( ex );
        }
    }


    public static class ExampleJPAExceptionTranslater implements PersistenceExceptionTranslator {


        @Override
        public DataAccessException translateExceptionIfPossible( RuntimeException ex ) {
            return null;
        }

    }
}
