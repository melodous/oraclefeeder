package oraclefeeder.properties.xml.mapping.querys;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
@XmlRootElement(name="Column")
public class XmlQColumn {

    private String name;
    private String metricName;

    public String getName() {
        return name;
    }

    @XmlElement(name="Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getMetricName() {
        return metricName;
    }

    @XmlElement(name="MetricName")
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
}
