package my.spring.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  StoredProcedure - абтсракция для сохраненных процедур RDBMS
 */
public class GetSysdateProcedure extends StoredProcedure {

    private static final String SQL = "sysdate";


    private GetSysdateProcedure getSysdate;

    @Autowired
    public void init(DataSource dataSource) {
        this.getSysdate = new GetSysdateProcedure(dataSource);
    }

    public Date getSysdate() {
        return getSysdate.execute();
    }

    public GetSysdateProcedure(DataSource dataSource) {
        setDataSource(dataSource);
        // указать что это функция
        setFunction(true);
        setSql(SQL);

        // Обявить In(SqlParameter) InOut(SqlInOutParameter) Out(SqlOutParameter) параметры
        declareParameter(new SqlOutParameter("date", Types.DATE));
        compile();
    }

    public Date execute() {
        // the sysdate sproc has no input parameters, so an empty Map is supplied...
        Map<String, Object> results = execute(new HashMap<>());
        Date sysdate = (Date) results.get("date");
        return sysdate;
    }

}
