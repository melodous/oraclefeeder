package oraclefeeder.core.domain;

import oraclefeeder.properties.Settings;

public class Statistic {

    private String configPrefix;
    private String prefixMetricGroup;
    private String queryName;
    private String instanceName;
    private String metricName;
    private String value;
    private Long timesTamp;

    public String generateMetric(){

        String separator = Settings.propertie().getMetricSeparator();
        String metric = "";

        if(this.configPrefix != null) {
            metric = metric + this.configPrefix + separator;
        }
        if(this.prefixMetricGroup != null) {
            metric = metric + this.prefixMetricGroup + separator;
        }
        if(this.instanceName != null) {
            metric = metric + this.instanceName + separator;
        }

        if(this.queryName != null) {
            metric = metric + this.queryName + separator;
        }

        if(this.metricName != null) {
            metric = metric + this.metricName + " ";
        }
        String valueParset;

        if(this.value != null && this.value.contains(",")) {
            valueParset = this.value.replace(",", ".");
            if(valueParset.indexOf(".") == 0) {
                valueParset = "0" + valueParset;
            }
        } else {
            valueParset = this.value;
        }

        metric = metric + valueParset + " " + this.timesTamp;
        return metric;
    }

    public void setConfigPrefix(String configPrefix) {
        this.configPrefix = configPrefix;
    }

    public void setPrefixMetricGroup(String prefixMetricGroup) {
        this.prefixMetricGroup = prefixMetricGroup;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimesTamp(Long timesTamp) {
        this.timesTamp = timesTamp;
    }
}
