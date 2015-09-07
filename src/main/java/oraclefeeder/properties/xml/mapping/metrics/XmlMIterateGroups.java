package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 12/08/15.
 */
@XmlRootElement(name="IterateGroups")
public class XmlMIterateGroups {

    private List<XmlMIterateGroup> xmlMIterateGroups;

    public List<XmlMIterateGroup> getXmlMIterateGroups() {
        return xmlMIterateGroups;
    }

    @XmlElement(name="IterateGroup")
    public void setXmlMIterateGroups(List<XmlMIterateGroup> xmlMIterateGroups) {
        this.xmlMIterateGroups = xmlMIterateGroups;
    }

}

