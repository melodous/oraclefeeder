package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 17/08/15.
 */
@XmlRootElement(name="Result")
public class XmlMResult {

    private List<XmlMColumn> xmlQColumns;

    public List<XmlMColumn> getXmlMColumns() {
            return xmlQColumns;
        }

    @XmlElement(name="Column")
    public void setXmlMColumns(List<XmlMColumn> xmlQColumns) {
        this.xmlQColumns = xmlQColumns;
    }

}