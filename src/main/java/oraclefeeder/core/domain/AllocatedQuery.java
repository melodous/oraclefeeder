package oraclefeeder.core.domain;

import oraclefeeder.properties.xml.mapping.Metric;

/**
 * Created by Alberto Pascual on 22/09/15.
 */
public class AllocatedQuery {

    private Metric metric;
    private CacheIterateGroup cacheIterateGroup;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public CacheIterateGroup getCacheIterateGroup() {
        return cacheIterateGroup;
    }

    public void setCacheIterateGroup(CacheIterateGroup cacheIterateGroup) {
        this.cacheIterateGroup = cacheIterateGroup;
    }
}
