package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 17/08/15.
 */
@XmlRootElement(name="Query")
public class XmlMQuery {

    private String name;
    private String id;
    private XmlMParameters xmlMParameters;


    public String getName() {
        return name;
    }

    @XmlAttribute(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public XmlMParameters getXmlMParameters() {
        return xmlMParameters;
    }

    @XmlElement(name="Parameters")
    public void setXmlMParameters(XmlMParameters xmlMParameters) {
        this.xmlMParameters = xmlMParameters;
    }


    public String getId() {
        return id;
    }
    @XmlAttribute(name="id")
    public void setId(String id) {
        this.id = id;
    }
}
