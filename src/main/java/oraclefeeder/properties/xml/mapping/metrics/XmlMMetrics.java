package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 5/08/15.
 */
@XmlRootElement(name="Metrics")
public class XmlMMetrics {

    private List<XmlMMetric> xmlMMetrics;

    public List<XmlMMetric> getXmlMMetrics() {
        return this.xmlMMetrics;
    }

    @XmlElement(name="Metric")
    public void setXmlMMetrics(List<XmlMMetric> xmlMMetrics) {
        this.xmlMMetrics = xmlMMetrics;
    }
}
