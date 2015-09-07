package oraclefeeder.properties.xml.mapping;

import java.util.Map;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
public class Query {

    private String name;
    private String statement;
    private String instanceFrom;
    private String instanceFromType;
    private Map<String, String> values; //map value can be null
    private String columnMetricName;
    //param/type
    private Map<String, String> paramns;

    public Map<String, String> getParamns() {
        return paramns;
    }

    public void setParamns(Map<String, String> paramns) {
        this.paramns = paramns;
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

    public String getInstanceFrom() {
        return instanceFrom;
    }

    public void setInstanceFrom(String instanceFrom) {
        this.instanceFrom = instanceFrom;
    }

    public String getInstanceFromType() {
        return instanceFromType;
    }

    public void setInstanceFromType(String instanceFromType) {
        this.instanceFromType = instanceFromType;
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
