package my.spring.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

/**
 * Можно наследоваться от SQLErrorCodeSQLExceptionTranslator, для предоставление собственнз видов исключения
 */
public class ExampleSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {


    public static class MySQLException extends DataAccessException {

        private int code;

        public MySQLException( String msg, int code ) {
            super( msg );
            this.code = code;
        }

        public MySQLException( String msg, Throwable cause, int code ) {
            super( msg, cause );
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode( int code ) {
            this.code = code;
        }
    }

    @Override
    protected DataAccessException customTranslate( String task, String sql, SQLException sqlEx ) {
        if (sqlEx.getErrorCode() == -12345) {
            return new MySQLException(task, sqlEx, -112345);
        }
        return null;
    }
}
