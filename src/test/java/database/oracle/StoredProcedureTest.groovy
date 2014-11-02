package database.oracle
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.google.common.base.Function
import com.google.common.collect.Sets
import oracle.jdbc.OracleTypes
import org.slf4j.LoggerFactory
import org.spockframework.util.Nullable
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.support.AbstractSqlTypeValue
import org.springframework.jdbc.core.support.SqlLobValue
import spock.lang.Specification
import spock.lang.Unroll
import util.Dates

import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.ZERO
import static oracle.jdbc.OracleTypes.*
/**
 * @author mohammo on 11/2/2014.
 */

class StoredProcedureTest extends Specification {

    def "StoredProcedure"() {
        when:
        def sp = new StoredProcedure<String>()

        then:
        def params = sp.getOutputParameters()
        params.size() == 4
        params.get(0).getName() == "oStatusCode"
        params.get(1).getName() == "oStatusDesc"
        params.get(2).getName() == "oFailedRecords"
        params.get(3).getName() == "oExecutedSQL"

        params.get(0).getSqlType() == NUMERIC
        params.get(1).getSqlType() == VARCHAR
        params.get(2).getSqlType() == VARCHAR
        params.get(3).getSqlType() == VARCHAR
    }

    @Unroll
    def "stringInput"() {
        expect:
        def sp = new StoredProcedure<String>().stringInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == VARCHAR
        args.get(param) == value

        where:
        param  | value
        "Name" | "A"
        ""     | "A"
        "Name" | null
    }

    @Unroll
    def "stringsInput "() {
        expect:
        def sp = new StoredProcedure<String>().stringsInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == VARCHAR
        args.get(param) == output

        where:
        param  | value                          | output
        "Name" | Sets.newHashSet("A", "B", "C") | "'A','B','C'"
        "Name" | Sets.newHashSet()              | ""
        "Name" | null                           | null
    }

    @Unroll
    def "enumsInput"() {
        expect:
        def sp = new StoredProcedure<String>().enumsInput(param, value, new ToNameFunction())
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == VARCHAR
        args.get(param) == expected

        where:
        param  | value                   | expected       | size
        "Name" | null                    | null           | 1
        "Name" | []                      | ""             | 1
        "Name" | [Color.RED]             | "'red'"        | 1
        "Name" | [Color.RED, Color.BLUE] | "'red','blue'" | 2
    }

    private static enum Color {
        RED("red"), BLUE("blue"), GREEN("green")

        private String code

        Color(String code) {
            this.code = code
        }
    }

    private static class ToNameFunction implements Function<Color, String> {
        @Override
        String apply(@Nullable Color color) {
            return color.code
        }
    }

    @Unroll
    def "numericInput"() {
        expect:
        def sp = new StoredProcedure<String>().numericInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == NUMERIC
        args.get(param) == value

        where:
        param  | value
        "Name" | null
        "Name" | 1
        "Name" | 1.0
    }

    @Unroll
    def "dateInput - LocalDate"() {
        expect:
        def sp = new StoredProcedure<String>().dateInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == VARCHAR
        args.get(param) == output

        where:
        param  | value                       | output
        "Name" | null                        | null
        "Name" | Dates.localDate(2014, 1, 1) | "01-Jan-2014"
    }

    @Unroll
    def "dateInput - Date"() {
        expect:
        def sp = new StoredProcedure<String>().dateInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == VARCHAR
        args.get(param) == output

        where:
        param  | value                  | output
        "Name" | null                   | null
        "Name" | Dates.date(2014, 1, 1) | "01/01/2014"
    }

    @Unroll
    def "clobInput"() {
        expect:
        def sp = new StoredProcedure<String>().clobInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == OracleTypes.CLOB
        args.get(param) instanceof SqlLobValue

        where:
        param  | value
        "Name" | null
        "Name" | "A"
    }

    @Unroll
    def "collectionInput"() {
        expect:
        def sp = new StoredProcedure<String>().collectionInput(param, value)
        def params = sp.getInputParameters()
        def args = sp.getArguments()

        params.size() == 1
        def parameter = params.get(0)
        parameter.name == param
        parameter.sqlType == ARRAY
        args.get(param) instanceof AbstractSqlTypeValue

        where:
        param  | value
        "Name" | null
        "Name" | ["A", "B"]
    }

    def "cursorOutput with RowMapper"() {
        def rowMapper = Mock(RowMapper)
        when:
        def sp = new StoredProcedure<String>().cursorOutput("Name", rowMapper)

        then:
        sp.outputParamName == "Name"
        def parameters = sp.outputParameters
        parameters.size() == 5
        def outParameter = parameters.get(parameters.size() - 1)
        outParameter.sqlType == CURSOR
        outParameter.rowMapper == rowMapper
    }

    def "cursorOutput with RowCallbackHandler"() {
        def rowCallbackHandler = Mock(RowCallbackHandler)
        when:
        def sp = new StoredProcedure<String>().cursorOutput("Name", rowCallbackHandler)

        then:
        sp.outputParamName == "Name"
        def parameters = sp.outputParameters
        parameters.size() == 5
        def outParameter = parameters.get(parameters.size() - 1)
        outParameter.sqlType == CURSOR
        outParameter.rowCallbackHandler == rowCallbackHandler
    }

    def "cursorOutput with ResultSetExtractor"() {
        def resultSetExtractor = Mock(ResultSetExtractor)
        when:
        def sp = new StoredProcedure<String>().cursorOutput("Name", resultSetExtractor)

        then:
        sp.outputParamName == "Name"
        def parameters = sp.outputParameters
        parameters.size() == 5
        def outParameter = parameters.get(parameters.size() - 1)
        outParameter.sqlType == CURSOR
        outParameter.resultSetExtractor == resultSetExtractor
    }

    def "stringOutput with RowMapper"() {
        def rowMapper = Mock(RowMapper)
        when:
        def sp = new StoredProcedure<String>().stringOutput("Name", rowMapper)

        then:
        sp.outputParamName == "Name"
        def parameters = sp.outputParameters
        parameters.size() == 5
        def outParameter = parameters.get(parameters.size() - 1)
        outParameter.sqlType == VARCHAR
        outParameter.rowMapper == rowMapper
    }

    def "beforeSpCallLogMessage"() {
        setup:
        Logger root = LoggerFactory.getLogger(StoredProcedure.class);
        def logLevel = root.getLevel()

        def sp = new StoredProcedure<String>()
                .packageName("PKG").storedProcName("SP")
                .stringInput("Param1", "String")
                .stringsInput("Param2", ["String1", "String2"])
                .stringOutput("OutputParam", Mock(RowMapper))
        when:
        root.setLevel(Level.DEBUG);
        def logMsg = sp.beforeSpCallLogMessage()
        then:
        logMsg == "CALL PKG.SP ( Param1 IN, Param2 IN, OutputParam OUT, oStatusCode OUT, oStatusDesc OUT, oExecutedSQL OUT ) \nParam1 = String \nParam2 = 'String1','String2' \n"

        when:
        root.setLevel(Level.INFO);
        logMsg = sp.beforeSpCallLogMessage()

        then:
        logMsg == ""

        cleanup:
        root.setLevel(logLevel);
    }

    @Unroll
    def "afterSpCallLogMessage"() {
        setup:
        Logger root = LoggerFactory.getLogger(StoredProcedure.class);
        def logLevel = root.getLevel()

        def sp = new StoredProcedure<String>()
                .packageName("PKG").storedProcName("SP")
                .stringInput("Param1", "String")
                .stringsInput("Param2", ["String1", "String2"])
                .stringOutput("OutputParam", Mock(RowMapper))

        when: "Log Level is DEBUG"
        root.setLevel(Level.DEBUG);
        def logMsg = sp.afterSpCallLogMessage()
        then:
        logMsg == "Results for PKG.SP () \n"

        when: "Log Level is DEBUG and results are not empty"
        root.setLevel(Level.DEBUG);

        sp.setResults(["oStatusCode": "Value1", "oStatusDesc": "Value2", "oFailedRecords": "Id=ErrorMsg", "oExecutedSQL": "Value4"])
        logMsg = sp.afterSpCallLogMessage()
        then:
        logMsg == "Results for PKG.SP () \n" +
                "oStatusCode = Value1\n" +
                "oStatusDesc = Value2\n" +
                "oFailedRecords = {Id=ErrorMsg}\n" +
                "oExecutedSQL = Value4\n"

        when: "Log level is INFO"
        root.setLevel(Level.INFO);
        logMsg = sp.afterSpCallLogMessage()

        then:
        logMsg == ""

        cleanup:
        root.setLevel(logLevel);
    }

    def "checkForErrors"() {
        def sp = new StoredProcedure<String>()
                .packageName("PKG").storedProcName("SP")
                .stringInput("Param1", "String")
                .stringsInput("Param2", ["String1", "String2"])
                .stringOutput("OutputParam", Mock(RowMapper))

        when: "Has Error"
        sp.setResults(["oStatusCode": "Value1", "oStatusDesc": "Value2", "oFailedRecords": "Id=ErrorMsg", "oExecutedSQL": "Value4"])
        sp.checkForErrors()

        then:
        thrown(DataAccessException)

        when: "Has No Error"
        sp.setResults(["oStatusCode": ZERO, "oStatusDesc": "Value2", "oExecutedSQL": "Value4"])
        sp.checkForErrors()

        then:
        notThrown(DataAccessException)
    }

    @Unroll
    def "hasErrors"() {
        setup:
        def sp = new StoredProcedure<String>()
                .packageName("PKG").storedProcName("SP")
                .stringInput("Param1", "String")
                .stringsInput("Param2", ["String1", "String2"])
                .stringOutput("OutputParam", Mock(RowMapper))

        expect:
        sp.setResults(results)
        sp.hasError() == errorExpected

        where:
        desc                                                              | results                                                                                                    | errorExpected
        "Results = null"                                                  | null                                                                                                       | false
        "Results != null but statusCode is null"                          | ["oStatusCode": null, "oStatusDesc": "Value2", "oFailedRecords": null, "oExecutedSQL": "Value4"]           | false
        "Results != null, statusCode is 0"                                | ["oStatusCode": ZERO, "oStatusDesc": "Value2", "oFailedRecords": null, "oExecutedSQL": "Value4"]           | false
        "Results != null, statusCode is 1"                                | ["oStatusCode": ONE, "oStatusDesc": "Value2", "oFailedRecords": null, "oExecutedSQL": "Value4"]            | true
        "Results != null, statusCode is 0, but failedRecords is not null" | ["oStatusCode": ZERO, "oStatusDesc": "Value2", "oFailedRecords": "", "oExecutedSQL": "Value4"]             | true
        "Results != null, statusCode is 0, but failedRecords is not null" | ["oStatusCode": ZERO, "oStatusDesc": "Value2", "oFailedRecords": "id=errorText", "oExecutedSQL": "Value4"] | true
    }

    def "getErrorCode"() {
        setup:
        def sp = new StoredProcedure<String>().packageName("PKG").storedProcName("SP")
        sp.setResults(["oStatusCode": "123"])
        when:
        def code = sp.getErrorCode()
        then:
        code == "123"
    }

    def "getErrorMessage"() {
        setup:
        def sp = new StoredProcedure<String>().packageName("PKG").storedProcName("SP")
        sp.setResults(["oStatusDesc": "123"])
        when:
        def msg = sp.getErrorMessage()
        then:
        msg == "123"
    }

    def "getExecutedSQL"() {
        setup:
        def sp = new StoredProcedure<String>().packageName("PKG").storedProcName("SP")
        sp.setResults(["oExecutedSQL": "123"])
        when:
        def msg = sp.getExecutedSQL()
        then:
        msg == "123"
    }

    @Unroll
    def "getFailedRecords"() {
        expect:
        def sp = new StoredProcedure<String>().packageName("PKG").storedProcName("SP")
        sp.setResults(results)
        def map = sp.getFailedRecords()
        map.size() == size

        where:
        results                                      | size
        ["oFailedRecords": "keyvalue"]               | 0
        ["oFailedRecords": "keyvalue1|keyvalue2"]    | 0
        ["oFailedRecords": "key1=value"]             | 1
        ["oFailedRecords": "key1=value|key2=value2"] | 2
    }
}