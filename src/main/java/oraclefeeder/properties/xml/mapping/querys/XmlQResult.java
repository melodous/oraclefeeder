package oraclefeeder.properties.xml.mapping.querys;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="Result")
public class XmlQResult {

    private String columnMetricName;
    private List<XmlQColumn> xmlQColumns;

    public String getColumnMetricName() {
        return columnMetricName;
    }

    @XmlElement(name="ColumnMetricName")
    public void setColumnMetricName(String columnMetricName) {
        this.columnMetricName = columnMetricName;
    }

    public List<XmlQColumn> getXmlQColumns() {
        return xmlQColumns;
    }

    @XmlElement(name="Column")
    public void setXmlQColumns(List<XmlQColumn> xmlQColumns) {
        this.xmlQColumns = xmlQColumns;
    }
}
