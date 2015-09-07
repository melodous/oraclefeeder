package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
@XmlRootElement(name="Parameter")
public class XmlMParameter {

    private String parameter;
    private String type;

    public String getParameter() {
        return parameter;
    }

    @XmlValue
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }
}
