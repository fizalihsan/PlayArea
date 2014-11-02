package database.oracle;

/**
 * @author mohammo on 11/2/2014.
 */

import com.google.common.base.Function;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.jdbc.core.support.SqlLobValue;
import util.Dates;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import static java.sql.Types.NUMERIC;
import static java.sql.Types.VARCHAR;
import static oracle.jdbc.OracleTypes.CLOB;
import static oracle.jdbc.OracleTypes.CURSOR;
import static org.springframework.util.CollectionUtils.isEmpty;
import static util.Collections.enumsToCommaDelimitedStringWithQuotes;
import static util.Collections.toCommaSeparatedStringWithQuotes;

/**
 * @author mohammo on 5/7/2014.
 */

public final class StoredProcedure<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoredProcedure.class);

    private static final String ERROR_CODE_PARAM = "oStatusCode";
    private static final String ERROR_MESSAGE_PARAM = "oStatusDesc";
    private static final String FAILED_RECORDS_PARAM = "oFailedRecords";
    private static final String EXECUTED_SQL_PARAM = "oExecutedSQL";

    private JdbcTemplate jdbcTemplate;
    private String packageName;
    private String storedProcName;
    private String outputParamName;
    private List<SqlOutParameter> outputParameters = new ArrayList<>();
    private List<SqlParameter> inputParameters = new ArrayList<>();
    private Map<String, Object> arguments = new TreeMap<>();
    private Map<String, Object> results;

    public StoredProcedure() {
        this.outputParameters.add(new SqlOutParameter(ERROR_CODE_PARAM, NUMERIC));
        this.outputParameters.add(new SqlOutParameter(ERROR_MESSAGE_PARAM, VARCHAR));
        this.outputParameters.add(new SqlOutParameter(FAILED_RECORDS_PARAM, VARCHAR));
        this.outputParameters.add(new SqlOutParameter(EXECUTED_SQL_PARAM, VARCHAR));
    }

    public StoredProcedure<T> jdbcTemplate(final JdbcTemplate jdbcTemplateLocal) {
        this.jdbcTemplate = jdbcTemplateLocal;
        return this;
    }

    public StoredProcedure<T> packageName(final String packageNameLocal) {
        this.packageName = packageNameLocal;
        return this;
    }

    public StoredProcedure<T> storedProcName(final String storedProcNameLocal) {
        this.storedProcName = storedProcNameLocal;
        return this;
    }

    public StoredProcedure<T> stringInput(String name, String value) {
        this.inputParam(name, VARCHAR, value);
        return this;
    }

    public StoredProcedure<T> stringsInput(String name, Collection<String> values) {
        this.inputParam(name, VARCHAR, toCommaSeparatedStringWithQuotes(values));
        return this;
    }

    public <E extends Enum<E>> StoredProcedure<T> enumsInput(String name, Collection<E> values, Function<E, String> function) {
        this.inputParam(name, VARCHAR, enumsToCommaDelimitedStringWithQuotes(values, function));
        return this;
    }

    public StoredProcedure<T> numericInput(String name, Number value) {
        this.inputParam(name, NUMERIC, value);
        return this;
    }

    public StoredProcedure<T> dateInput(String name, LocalDate date) {
        String value = date == null ? null : Dates.toString(date,Dates.DD_MMM_YYYY);
        this.inputParam(name, VARCHAR, value);
        return this;
    }

    public StoredProcedure<T> dateInput(String name, Date date) {
        String value = date == null ? null : Dates.toFormatMMddyyyy(date);
        this.inputParam(name, VARCHAR, value);
        return this;
    }

    public StoredProcedure<T> clobInput(String name, String value) {
        LOGGER.debug("CLOB Input: {} = {}", name, value);
        this.inputParameters.add(new SqlParameter(name, CLOB));
        this.arguments.put(name, new SqlLobValue(value));
        return this;
    }

    public StoredProcedure<T> collectionInput(String name, Collection value) {
        final Object[] arrayInput = value == null ? null : value.toArray(new Object[]{});
        this.inputParameters.add(new SqlParameter(name, Types.ARRAY, "ARRAY_TABLE"));

        // Add input value
        this.arguments.put(name, new AbstractSqlTypeValue() {
            @Override
            protected Object createTypeValue(Connection connection, int sqlType, String typeName) throws SQLException {
                return connection.createArrayOf(typeName, arrayInput);
            }
        });

        return this;
    }

    private StoredProcedure<T> inputParam(String name, Integer type, Object value) {
        this.inputParameters.add(new SqlParameter(name, type));
        this.arguments.put(name, value);
        return this;
    }

    public StoredProcedure<T> cursorOutput(final String outputParamNameLocal, RowMapper<?> rowMapper) {
        this.outputParamName = outputParamNameLocal;
        this.outputParameters.add(new SqlOutParameter(this.outputParamName, CURSOR, rowMapper));
        return this;
    }

    public StoredProcedure<T> cursorOutput(final String outputParamNameLocal, RowCallbackHandler rowCallbackHandler) {
        this.outputParamName = outputParamNameLocal;
        this.outputParameters.add(new SqlOutParameter(this.outputParamName, CURSOR, rowCallbackHandler));
        return this;
    }

    public StoredProcedure<T> cursorOutput(final String outputParamNameLocal, ResultSetExtractor<T> resultSetExtractor) {
        this.outputParamName = outputParamNameLocal;
        this.outputParameters.add(new SqlOutParameter(this.outputParamName, CURSOR, resultSetExtractor));
        return this;
    }

    public StoredProcedure<T> stringOutput(final String outputParamNameLocal, RowMapper<?> rowMapper) {
        this.outputParamName = outputParamNameLocal;
        this.outputParameters.add(new SqlOutParameter(this.outputParamName, VARCHAR, rowMapper));
        return this;
    }

    @SuppressWarnings({"unchecked", "PMD.AvoidCatchingGenericException"})
    public T call() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate).withCatalogName(this.packageName).withProcedureName(this.storedProcName).declareParameters(this.outputParameters.toArray(new SqlOutParameter[]{}));

        if(!isEmpty(this.inputParameters)) {
            jdbcCall.declareParameters(this.inputParameters.toArray(new SqlParameter[]{}));
        }

        try {
            LOGGER.debug("{}", beforeSpCallLogMessage());

            this.results = jdbcCall.execute(this.arguments);

            LOGGER.debug("{}", afterSpCallLogMessage());
        } catch (Exception e) {
            String message = "Exception invoking SP " + this.packageName + "." + this.storedProcName + " : Exception: " + e.getMessage();
            LOGGER.error("{}", message, e);
            throw new DataAccessException(message, e);
        }

        checkForErrors();

        return (T) this.results.get(this.outputParamName);
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    protected String beforeSpCallLogMessage() {
        // Perform this complex string concatenation only if debug is enabled
        if(LOGGER.isDebugEnabled()) {
            String message = "CALL " + this.packageName + "." + this.storedProcName + " ( ";

            StringBuilder inputParams = new StringBuilder();
            for (SqlParameter parameter : this.inputParameters) {
                inputParams.append(parameter.getName()).append(" IN, ");
            }

            String outputParams = this.outputParamName + " OUT, " + this.ERROR_CODE_PARAM + " OUT, " + this.ERROR_MESSAGE_PARAM + " OUT, " + this.EXECUTED_SQL_PARAM + " OUT ) \n";

            StringBuilder inputValues = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.arguments.entrySet()) {
                String value = entry.getValue() == null ? "null" : entry.getValue().toString();
                inputValues.append(entry.getKey()).append(" = ").append(value).append(" \n");
            }

            return message + inputParams + outputParams + inputValues;
        }
        return "";
    }

    protected String afterSpCallLogMessage() {
        // Perform this complex string concatenation only if debug is enabled
        if(LOGGER.isDebugEnabled()) {
            StringBuilder outputMessage = new StringBuilder().append("Results for ").append(this.packageName).append(".").append(this.storedProcName).append(" () \n");

            if(!isEmpty(this.results)) {
                outputMessage.append(ERROR_CODE_PARAM).append(" = ").append(getErrorCode()).append("\n");
                outputMessage.append(ERROR_MESSAGE_PARAM).append(" = ").append(getErrorMessage()).append("\n");
                outputMessage.append(FAILED_RECORDS_PARAM).append(" = ").append(getFailedRecords()).append("\n");
                outputMessage.append(EXECUTED_SQL_PARAM).append(" = ").append(getExecutedSQL()).append("\n");
            }

            return outputMessage.toString();
        }
        return "";
    }

    /**
     * Check for errors and throw exception
     */
    protected void checkForErrors() {
        if(hasError()) {
            String message = "Exception invoking SP " + this.packageName + "." + this.storedProcName + " : Error Code: " + getErrorCode() + " - Error Msg: " + getErrorMessage();
            Map<String, String> failedRecords = getFailedRecords();
            LOGGER.error("{} : \nFailed Records = {}", message, failedRecords);
            throw new DataAccessException(message, failedRecords);
        }
    }

    /**
     * Checks if the SP invocation has errors
     *
     * @return true, if the SP invocation has errors
     */
    protected boolean hasError() {
        if(this.results!=null){
            Object errorCode = results.get(ERROR_CODE_PARAM);
            if(errorCode!=null && !BigDecimal.ZERO.equals(errorCode)){
                return true;
            }

            Object failedRecords = results.get(FAILED_RECORDS_PARAM);
            if(failedRecords!=null){
                return true;
            }
        }

        return false;
    }

    /**
     * Return the SP output parameter 'oStatusCode' value
     *
     * @return value from output parameter 'oStatusCode'
     */
    public Object getErrorCode() {
        return this.results.get(ERROR_CODE_PARAM);
    }

    /**
     * Return the SP output parameter 'oStatusDesc' value
     *
     * @return value from output parameter 'oStatusDesc'
     */
    public Object getErrorMessage() {
        return this.results.get(ERROR_MESSAGE_PARAM);
    }

    /**
     * Return the SP output parameter 'oExecutedSQL' value
     *
     * @return value from output parameter 'oExecutedSQL'
     */
    public Object getExecutedSQL() {
        return this.results.get(EXECUTED_SQL_PARAM);
    }

    /**
     * Translates the SP output parameter failedRecords into a map of failed row
     * id and error message
     *
     * @return map of failed row id and error message
     */
    public Map<String, String> getFailedRecords() {
        Map<String, String> idVsErrorMsg = new HashMap<>();

        String failedRecordString = (String) this.results.get(FAILED_RECORDS_PARAM);
        if(failedRecordString != null) {
            String[] failedRecords = failedRecordString.split("\\|");

            for (String failedRecord : failedRecords) {
                String[] idAndError = failedRecord.split("=");

                if((idAndError != null) && (idAndError.length == 2)) {
                    idVsErrorMsg.put(idAndError[0], idAndError[1]);
                }
            }
        }
        return idVsErrorMsg;
    }

    protected String getStoredProcName() {
        return this.storedProcName;
    }

    protected String getPackageName() {
        return this.packageName;
    }

    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected List<SqlParameter> getInputParameters() {
        return this.inputParameters;
    }

    protected Map<String, Object> getArguments() {
        return this.arguments;
    }

    protected List<SqlOutParameter> getOutputParameters() {
        return this.outputParameters;
    }

    protected String getOutputParamName() {
        return this.outputParamName;
    }

    protected void setResults(Map<String, Object> results) {
        this.results = results;
    }
}