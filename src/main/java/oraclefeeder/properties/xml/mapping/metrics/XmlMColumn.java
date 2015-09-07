package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
@XmlRootElement(name="Column")
public class XmlMColumn {

    private String name;
    private String type;

    public String getType() {
        return type;
    }

    @XmlElement(name="Type")
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name="Name")
    public void setName(String name) {
        this.name = name;
    }
}
