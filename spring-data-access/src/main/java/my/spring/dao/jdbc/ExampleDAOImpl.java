package my.spring.dao.jdbc;

import my.spring.dao.ExampleDAO;
import my.spring.dao.ExampleEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Пример реализации JDBC DAO обьеста
 *
 * При работе спринг берет на себя
 * Открытие/закрытие соединений
 * подготовка и выполнение SQL statement
 * работу с транзакциями
 * обработку исключений
 * обход резльтатов запроса
 *
 *
 * JdbcTemplate - классический Spring JDBC способ работы и наиболее популярный. Это низкоуровневый и все отспльные так
 *                или иначе используют его
 * NamedParameterJdbcTemplate - Предостовляет именновыанные параметры, в отличие от традиционных "?" JDBC.
 *                              Такой способ более простой в использовании множества параметров в SQL
 * SimpleJdbcInsert and SimpleJdbcCall - оптимизируют мета иннормацию базы данных для избовление от количестве не нужной
 *                                       конфигурации. Этот способ прост позволяет выполнять запросы передав имя
 *                                       таблицы/процедуры и карту параметртов(и имени столюцов табицы). Это работает
 *                                       если база данных предостовляет достаточно информации.Если нет, то нужно указыать
 *                                       в ручную
 * RDBMS Objects including MappingSqlQuery, SqlUpdate и StoredProcedure - требут создавать многоразовый потокобезопасный
 *                                       обьект.Этот способ реализован как JDO Query, где выполнеяеться опредение строки
 *                                       запроса, обьявляються параметры и компилируеться запрос. После, методы могу
 *                                       выполняться множество раз с разными входными параметрами
 *
 * Пользуйтесь
 */
@Repository
public class ExampleDAOImpl implements ExampleDAO {

    //Потоко безовапсный класс, что занчит что его можно сконфигурировать в контексте и внедрять в соотетсвующие DAO
    //классы или для создовать для каждого DAO свой
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    // если один и тот же RowMapper реализуться по всему DAO будет более уместно использовать его как шаблон для всех запросов
    private static final RowMapper<ExampleEntity> EXAMPLE_ENTITY_ROW_MAPPER = ( rs, rowNum ) -> {
        ExampleEntity result = new ExampleEntity();
        result.setName( rs.getString( "name" ) );
        result.setSomeDouble( rs.getDouble( "someDouble" ) );
        result.setId( rs.getInt( "id" ) );
        return result;
    };

    @Inject
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate( dataSource );
        this.jdbcTemplate.setExceptionTranslator( new ExampleSQLErrorCodesTranslator() );
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate( dataSource );

    }


    /**
     * Другие пример использования JdbcTemplate
     */
    private void someOverJdbcTemplateExamples() {
        Integer countOfActors = this.jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_actors", Integer.class );
        Integer countOfJoeActors = this.jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM t_actors WHERE first_name = ?", Integer.class, "Joe" );

        String lastName = this.jdbcTemplate.queryForObject(
                "select last_name from t_actor where id = ?",
                new Object[]{1212L}, String.class);

        //создать таблицу
        this.jdbcTemplate.execute("create table mytable (id integer, name varchar(100))");

        int someInt = 10;
        // вызов процедуры
        this.jdbcTemplate.update(
                "call SUPPORT.REFRESH_ACTORS_SUMMARY(?)",someInt);
    }

    /**
     * Другие пример использования NamedParameterJdbcTemplate
     */
    private void someOverNamdeJdbcTemplateExamples() {
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("fist_name", "first_name_param");

        Integer count = this.namedJdbcTemplate.queryForObject(
                "select count(*) from T_ACTOR where first_name = :first_name", namedParameters, Integer.class );

        // Либо с помошью карты:
        Map<String, String> mapOfNamedParameters = Collections.singletonMap( "first_name", "first_name_param" );

        count = this.namedJdbcTemplate.queryForObject(
                "select count(*) from T_ACTOR where first_name = :first_name", mapOfNamedParameters, Integer.class );

        // Можно использовать значение полей классов, для выполенение SQL запросов:

        ExampleEntity exampleEntity = new ExampleEntity();
        namedParameters = new BeanPropertySqlParameterSource( exampleEntity );
        count = this.namedJdbcTemplate.queryForObject(
                "select count(*) from example_entity_table where name = :name and some_double = :someDouble",
                namedParameters, Integer.class );

    }

    /*
        Обновление пакетами
        Используя обновление пакетами вы уменашете количество запросов к базе данных
     */

    public int[] batchUpdate(final List<ExampleEntity> entities ) {
        int[] updateCounts = jdbcTemplate.batchUpdate(
                "update example_entity_table set name = ?, someDouble = ?  where id = ?",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues( PreparedStatement ps, int i ) throws SQLException {
                        ps.setString( 1, entities.get( i ).getName() );
                        ps.setDouble( 2, entities.get( i ).getSomeDouble() );
                        ps.setLong( 3, entities.get( i ).getId() );
                    }

                    @Override
                    public int getBatchSize() {
                        return entities.size();
                    }
                } );


        /*
            или использовать NamedParameterJdbcTemplate:
            Вместо использования ? используеться название полей сущности
          */
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch( entities.toArray() );
        int[] updateCounts2 = namedJdbcTemplate.batchUpdate(
                "update t_actor set first_name = :firstName, last_name = :lastName where id = :id",
                batch);

        /*
        batchUpdate вернет массив количества строк, для которых подействовала обновление, если количество строк
         не досупны, вернеться -2
         */
        return updateCounts;


    }

    @Override
    public void create( ExampleEntity entity ) {
        //jdbcTamplate
        this.jdbcTemplate.update(
                "insert into example_entity_table (id,name,someDouble) VALUES (?,?,?)",
                entity.getId(), entity.getName(), entity.getSomeDouble());
    }

    @Override
    public ExampleEntity read(long id) {
        ExampleEntity entity;

        //jdbcTamplate
        entity = this.jdbcTemplate.queryForObject( "SELECT example_entity_table name, someDouble, id FROM where id = ?",
                new Object[] { id }, ( rs, rowNum ) -> {
                    ExampleEntity result = new ExampleEntity();
                    result.setName( rs.getString( "name" ) );
                    result.setSomeDouble( rs.getDouble( "someDouble" ) );
                    result.setId( rs.getInt( "id" ) );
                    return result;
                } );



        return entity;
    }

    @Override
    public void update( ExampleEntity entity ) {
        //jdbcTamplate
        this.jdbcTemplate.update(
                "update example_entity_table set name = ?, someDouble = ?  where id = ?",
                entity.getName(), entity.getSomeDouble(), entity.getId());
    }

    @Override
    public void delete( ExampleEntity entity ) {
        //jdbcTamplate
        this.jdbcTemplate.update(
                "delete from example_entity_table where id = ?", entity.getId());
    }


}
