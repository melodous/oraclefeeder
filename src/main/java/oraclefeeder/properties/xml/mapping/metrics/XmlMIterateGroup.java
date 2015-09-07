package oraclefeeder.properties.xml.mapping.metrics;

import oraclefeeder.properties.xml.mapping.XmlInstancesFrom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 12/08/15.
 */
@XmlRootElement(name="IterateGroup")
public class XmlMIterateGroup {

    private String id;
    private String prefix;
    private XmlMMapPrefixSuffix xmlMMapPrefixSuffix;
    private XmlMStatement xmlMStatement;
    private XmlInstancesFrom instanceFrom;
    private XmlMResult result;
    private XmlMQuerys xmlMQuerys;

    public String getId() {
        return id;
    }

    @XmlAttribute(name="id")
    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    @XmlElement(name="Prefix")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public XmlMMapPrefixSuffix getXmlMMapPrefixSuffix() {
        return xmlMMapPrefixSuffix;
    }

    @XmlElement(name="MapPrefixSuffix")
    public void setXmlMMapPrefixSuffix(XmlMMapPrefixSuffix xmlMMapPrefixSuffix) {
        this.xmlMMapPrefixSuffix = xmlMMapPrefixSuffix;
    }

    public XmlMStatement getXmlMStatement() {
        return xmlMStatement;
    }

    @XmlElement(name="Statement")
    public void setXmlMStatement(XmlMStatement xmlMStatement) {
        this.xmlMStatement = xmlMStatement;
    }

    public XmlInstancesFrom getInstanceFrom() {
        return instanceFrom;
    }

    @XmlElement(name="InstancesFrom", required = true)
    public void setInstanceFrom(XmlInstancesFrom instanceFrom) {
        this.instanceFrom = instanceFrom;
    }

    public XmlMResult getResult() {
        return result;
    }

    @XmlElement(name="Result")
    public void setResult(XmlMResult result) {
        this.result = result;
    }

    public XmlMQuerys getXmlMQuerys() {
        return xmlMQuerys;
    }

    @XmlElement(name="Querys")
    public void setXmlMQuerys(XmlMQuerys xmlMQuerys) {
        this.xmlMQuerys = xmlMQuerys;
    }
}
