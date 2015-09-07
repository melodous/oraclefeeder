package oraclefeeder.properties.xml.mapping;

import oraclefeeder.properties.xml.mapping.metrics.XmlMMapPrefixSuffix;

import java.util.List;
import java.util.Map;

/**
 * Created by Alberto Pascual on 25/08/15.
 */
public class Metric {

    private Integer id;
    private String prefix;
    private String statement;
    private Integer cacheTimeSec;
    private Boolean cache;
    private XmlMMapPrefixSuffix mapPrefixSuffix;
    private String instanceFrom;
    private String instanceFromType;
    //column/type
    private Map<String, String> columns;
    private List<Query> query;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Integer getCacheTimeSec() {
        return cacheTimeSec;
    }

    public void setCacheTimeSec(Integer cacheTimeSec) {
        this.cacheTimeSec = cacheTimeSec;
    }

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public XmlMMapPrefixSuffix getMapPrefixSuffix() {
        return mapPrefixSuffix;
    }

    public void setMapPrefixSuffix(XmlMMapPrefixSuffix mapPrefixSuffix) {
        this.mapPrefixSuffix = mapPrefixSuffix;
    }

    public String getInstanceFrom() {
        return instanceFrom;
    }

    public void setInstanceFrom(String instanceFrom) {
        this.instanceFrom = instanceFrom;
    }

    public String getInstanceFromType() {
        return instanceFromType;
    }

    public void setInstanceFromType(String instanceFromType) {
        this.instanceFromType = instanceFromType;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

}
