package my.spring.dao.jdbc;

import my.spring.dao.ExampleDAO;
import my.spring.dao.ExampleEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Пример реализации JDBC DAO обьекта
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
 * SimpleJdbcInsert and SimpleJdbcCall - оптимизируют мета информацию базы данных для избовление от большого количества
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

    /*
        Позволяет выполнять параметризированные запросы с именными параметрми
     */
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    /*
        Предоствоялет простой спосбо загрузки обьектов в базу
     */
    private SimpleJdbcInsert exampleEntityInsert;

    /*
        Предостовляет простой способ вызова процедур
     */
    private SimpleJdbcCall exampleEntetyCall;


    private ExampleEntityQuery exampleEntityQuery;


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
        this.exampleEntityInsert = new SimpleJdbcInsert( dataSource )
                .withTableName( "example_entity_table" )
                .usingGeneratedKeyColumns( "id" );
        this.exampleEntetyCall = new SimpleJdbcCall( dataSource ).withProcedureName( "read_entities" );

        // либо описать процедуру

        // сделать возращающие значение карты, не зависящие от регистра
        this.jdbcTemplate.setResultsMapCaseInsensitive(true);
        this.exampleEntetyCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("read_entities")
                .withoutProcedureColumnMetaDataAccess()
                .useInParameterNames("in_id")
                .declareParameters(
                        new SqlParameter("in_id", Types.NUMERIC),
                        new SqlOutParameter("out_name", Types.VARCHAR),
                        new SqlOutParameter("out_some_double", Types.DOUBLE)
                );

        this.exampleEntityQuery = new ExampleEntityQuery( dataSource );
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

    /*
    Пример обращение к такой процедуре (MySQL)

    CREATE PROCEDURE read_entities (
        IN in_id INTEGER,
        OUT out_name VARCHAR(100),
        OUT out_some_double float,
    BEGIN
        SELECT name, some_double
        INTO out_name, out_double
        FROM example_entity_table where id = in_id;
    END;
     */
    public void invokeProcedure() {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("in_id", 1);
        Map out = exampleEntetyCall.execute(in);
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setId( 1 );
        exampleEntity.setName( ( String ) out.get( "out_name" ) );
        exampleEntity.setSomeDouble( ( Double ) out.get( "out_some_dobule" ) );

    }

    /**
     * Пример работы с CLOB/BLOB интерфесом lobHandle
     */
    public void blobClobExamples() throws IOException {
        File blobIn = new File( "someFile.file" );
        InputStream blobIs = new FileInputStream( blobIn );

        File clobIn = new File( "large.txt" );
        InputStream clobs = new FileInputStream( clobIn );
        InputStreamReader clobReader = new InputStreamReader( clobs );

        LobHandler lobHandler = new DefaultLobHandler();
        jdbcTemplate.execute( "INSERT INTO lob_table (id, a_clob, a_blob) VALUES (?, ?, ?)",
                new AbstractLobCreatingPreparedStatementCallback( lobHandler ) {
                    @Override
                    protected void setValues( PreparedStatement ps, LobCreator lobCreator )
                            throws SQLException, DataAccessException {
                        ps.setLong( 1, 1L );
                        lobCreator.setClobAsCharacterStream( ps, 2, clobReader, ( int ) clobIn.length() );
                        lobCreator.setBlobAsBinaryStream( ps, 3, blobIs, ( int ) blobIn.length() );
                    }
                } );
        blobIs.close();
        clobReader.close();
    }

    @Override
    public void create( ExampleEntity entity ) {
        //jdbcTamplate
        this.jdbcTemplate.update(
                "insert into example_entity_table (id,name,someDouble) VALUES (?,?,?)",
                entity.getId(), entity.getName(), entity.getSomeDouble());

        //получить автоматически згенерированный ключ
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into example_entity_table (name,someDouble) VALUES (?,?,?)" );
            ps.setString( 1, entity.getName() );
            ps.setDouble( 2, entity.getSomeDouble() );
            return ps;
        }, keyHolder );

        long key = keyHolder.getKey().longValue();

        // SimpleJdbcInsert
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put( "id", entity.getId() ); // Если ключ генерирветься автоматически то не нужно укзывать
        parameters.put( "name", entity.getName() );
        parameters.put( "someDouble", entity.getSomeDouble() );
        /*
            execute принемает простую карту
         */
        exampleEntityInsert.execute( parameters );

        // получит сгенерированный ключ
        key = exampleEntityInsert.executeAndReturnKey( parameters ).longValue();


        // использования SqlParameterSource
        //BeanPropertySqlParameterSource:
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource( entity );
        key = exampleEntityInsert.executeAndReturnKey( parameterSource ).longValue();

        //MapSqlParameterSource
        parameterSource = new MapSqlParameterSource()
                .addValue("name", entity.getName())
                .addValue("someDouble", entity.getSomeDouble());
        Number newId = exampleEntityInsert.executeAndReturnKey(parameterSource);
        entity.setId(newId.longValue());
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
