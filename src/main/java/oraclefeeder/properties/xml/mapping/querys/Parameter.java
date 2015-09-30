package oraclefeeder.properties.xml.mapping.querys;

/**
 * Created by Alberto Pascual on 22/09/15.
 */
public class Parameter {

    private String type;
    private String argument;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
