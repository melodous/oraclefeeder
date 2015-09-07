package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Alberto Pascual on 17/08/15.
 */
@XmlRootElement(name="Statement")
public class XmlMStatement {

    private String cache;
    private String CacheTimeSec;
    private String statement;

    public String getCache() {
        return cache;
    }

    @XmlAttribute(name="cache")
    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getCacheTimeSec() {
        return CacheTimeSec;
    }

    @XmlAttribute(name="cacheTime")
    public void setCacheTimeSec(String cacheTimeSec) {
        CacheTimeSec = cacheTimeSec;
    }

    public String getStatement() {
        return statement;
    }

    @XmlValue
    public void setStatement(String statement) {
        this.statement = statement;
    }
}
