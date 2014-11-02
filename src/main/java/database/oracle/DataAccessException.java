package database.oracle;

/**
 * @author mohammo on 11/2/2014.
 */

import java.util.HashMap;
import java.util.Map;

public class DataAccessException extends RuntimeException {
    private Map<String, String> failedRecords = new HashMap<>();

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Map<String, String> failedRecords) {
        super(message);
        this.failedRecords = failedRecords;
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    protected DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Map<String, String> getFailedRecords() {
        return failedRecords;
    }

}
