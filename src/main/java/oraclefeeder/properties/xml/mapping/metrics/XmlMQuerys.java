package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 17/08/15.
 */
@XmlRootElement(name="Querys")
public class XmlMQuerys {

    private List<XmlMQuery> xmlMQueries;

    public List<XmlMQuery> getXmlMQueries() {
        return xmlMQueries;
    }

    @XmlElement(name="Query")
    public void setXmlMQueries(List<XmlMQuery> xmlMQueries) {
        this.xmlMQueries = xmlMQueries;
    }
}
