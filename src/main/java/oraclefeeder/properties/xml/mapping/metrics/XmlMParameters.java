package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 17/08/15.
 */
@XmlRootElement(name="Parameters")
public class XmlMParameters {

    private List<XmlMParameter> xmlMParameter;

    public List<XmlMParameter> getXmlMParameter() {
        return xmlMParameter;
    }

    @XmlElement(name="Parameter")
    public void setXmlMParameter(List<XmlMParameter> xmlMParameter) {
        this.xmlMParameter = xmlMParameter;
    }
}
