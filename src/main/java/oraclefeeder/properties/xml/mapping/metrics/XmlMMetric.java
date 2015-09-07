package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 5/08/15.
 */
@XmlRootElement(name="Metric")
public class XmlMMetric {

    private XmlMQueryFile xmlMQueryFile;
    private XmlMIterateGroups xmlMIterateGroups;

    public XmlMQueryFile getXmlMQueryFile() {
        return xmlMQueryFile;
    }

    @XmlElement(name="QueryFile")
    public void setXmlMQueryFile(XmlMQueryFile xmlMQueryFile) {
        this.xmlMQueryFile = xmlMQueryFile;
    }

    public XmlMIterateGroups getXmlMIterateGroups() {
        return xmlMIterateGroups;
    }

    @XmlElement(name="IterateGroups")
    public void setXmlMIterateGroups(XmlMIterateGroups xmlMIterateGroups) {
        this.xmlMIterateGroups = xmlMIterateGroups;
    }

}
