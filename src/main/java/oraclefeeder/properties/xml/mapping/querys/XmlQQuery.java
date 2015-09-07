package oraclefeeder.properties.xml.mapping.querys;

import oraclefeeder.properties.xml.mapping.XmlInstancesFrom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 5/08/15.
 */
@XmlRootElement(name="Query")
public class XmlQQuery {
    private String name;
    private String statement;
    private XmlInstancesFrom instanceFrom;
    private List<XmlQResult> xmlQResult;

    public String getName() {
        return name;
    }

    @XmlElement(name="Name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getStatement() {
        return statement;
    }

    @XmlElement(name="Statement", required = true)
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public XmlInstancesFrom getInstanceFrom() {
        return instanceFrom;
    }

    @XmlElement(name="InstancesFrom", required = true)
    public void setInstanceFrom(XmlInstancesFrom instanceFrom) {
        this.instanceFrom = instanceFrom;
    }

    public List<XmlQResult> getXmlQResult() {
        return xmlQResult;
    }

    @XmlElement(name="Result")
    public void setXmlQResult(List<XmlQResult> xmlQResult) {
        this.xmlQResult = xmlQResult;
    }

}
