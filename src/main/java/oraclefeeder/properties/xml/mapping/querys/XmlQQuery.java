package oraclefeeder.properties.xml.mapping.querys;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 5/08/15.
 */
@XmlRootElement(name="Query")
public class XmlQQuery {
    private String id;
    private String statement;
    private List<XmlQResult> xmlQResult;

    public String getId() {
        return id;
    }

    @XmlElement(name="id", required = true)
    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    @XmlElement(name="Statement", required = true)
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<XmlQResult> getXmlQResult() {
        return xmlQResult;
    }

    @XmlElement(name="Result")
    public void setXmlQResult(List<XmlQResult> xmlQResult) {
        this.xmlQResult = xmlQResult;
    }

}
