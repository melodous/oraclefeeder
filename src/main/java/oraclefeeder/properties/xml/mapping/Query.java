package oraclefeeder.properties.xml.mapping;

import oraclefeeder.properties.xml.mapping.querys.Parameter;

import java.util.List;
import java.util.Map;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
public class Query {

    private String name;
    private String statement;
    private Map<String, String> values; //map value can be null
    private String columnMetricName;
    //param/type
    private List<Parameter> Parameters;

    public List<Parameter> getParameters() {
        return Parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        Parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public String getColumnMetricName() {
        return columnMetricName;
    }

    public void setColumnMetricName(String columnMetricName) {
        this.columnMetricName = columnMetricName;
    }
}
