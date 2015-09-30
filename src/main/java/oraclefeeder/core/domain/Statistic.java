package oraclefeeder.core.domain;

/**
 * Created by Alberto Pascual on 30/09/15.
 */
public class Statistic {

    private String configPrefix;
    private String instanceForm;
    private String prefixMetricGroup;
    private String queryName;
    private String instanceName;
    private String metricName;
    private String value;
    private Long timesTamp;
    private final String separator = ".";

    public String generateMetric(){
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

    public String getConfigPrefix() {
        return configPrefix;
    }

    public void setConfigPrefix(String configPrefix) {
        this.configPrefix = configPrefix;
    }

    public String getInstanceForm() {
        return instanceForm;
    }

    public void setInstanceForm(String instanceForm) {
        this.instanceForm = instanceForm;
    }

    public String getPrefixMetricGroup() {
        return prefixMetricGroup;
    }

    public void setPrefixMetricGroup(String prefixMetricGroup) {
        this.prefixMetricGroup = prefixMetricGroup;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(Long timesTamp) {
        this.timesTamp = timesTamp;
    }

    public String getSeparator() {
        return separator;
    }
}
