package my.spring.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Используеться для форимровании звпроса на обновление
 * используй метод update, по анологии с execute(..).
 *
 */
public class ExampleEntityUpdate extends SqlUpdate {

    public ExampleEntityUpdate(DataSource ds) {
        setDataSource(ds);
        setSql("update customer set credit_rating = ? where id = ?");
        declareParameter(new SqlParameter("creditRating", Types.NUMERIC));
        declareParameter(new SqlParameter("id", Types.NUMERIC));
        compile();
    }

    /**
     * SQLUpdate можно переопределить добавив уточняющие методы, хотя и его легко параметризировать устанваливая
     * SQL запрос и declareParameter параметры
     */
    public int execute(int id, int rating) {
        return update(rating, id);
    }
}
