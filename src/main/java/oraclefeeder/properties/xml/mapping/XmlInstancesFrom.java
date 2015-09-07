package oraclefeeder.properties.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
@XmlRootElement(name="InstancesFrom")
public class XmlInstancesFrom {

    private String instancesFrom;
    private String type;

    public String getInstancesFrom() {
        return instancesFrom;
    }

    @XmlValue
    public void setInstancesFrom(String instancesFrom) {
        this.instancesFrom = instancesFrom;
    }

    public String getType() {
        return type;
    }

    @XmlAttribute(name="type")
    public void setType(String type) {
        this.type = type;
    }


}
