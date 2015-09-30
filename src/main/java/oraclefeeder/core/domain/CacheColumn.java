package oraclefeeder.core.domain;

/**
 * Created by Alberto Pascual on 21/09/15.
 */
public class CacheColumn {

    private String name;
    private String value;
    private String type;

    public CacheColumn() {
    }

    public CacheColumn(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
