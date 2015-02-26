package my.spring.dao.jdbc;

import my.spring.dao.ExampleEntity;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Класс инкопсулирует в себе выполнение запроса к таблице и форимрования обьекта резултата.
 * Должен быть переопределен, так как MappingSqlQuery имеет абстактный метод mapRow, что в принцепе не удивительно
 */
public class ExampleEntityQuery extends MappingSqlQuery<ExampleEntity> {

    ExampleEntityQuery( DataSource ds ) {
        super( ds, "SELECT id, name, some_double from example_entity_table where id = ?" );
        //нужно указать каждый параметр
        super.declareParameter( new SqlParameter( "id", Types.INTEGER ) );
        compile(); // после компиляции этот запрос будет потоко безопасным
    }

    @Override
    protected ExampleEntity mapRow( ResultSet rs, int rowNum ) throws SQLException {
        ExampleEntity result = new ExampleEntity();
        result.setName( rs.getString( "name" ) );
        result.setSomeDouble( rs.getDouble( "someDouble" ) );
        result.setId( rs.getInt( "id" ) );
        return result;
    }
}