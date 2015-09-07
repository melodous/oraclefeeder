package oraclefeeder.properties.xml.mapping.querys;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="Querys")
public class XmlQQuerys {

    private List<XmlQQuery> xmlQQueries;

    public List<XmlQQuery> getXmlQQueries() {
        return this.xmlQQueries;
    }

    @XmlElement(name="Query")
    public void setXmlQQueries(List<XmlQQuery> xmlQQueries) {
        this.xmlQQueries = xmlQQueries;
    }
}