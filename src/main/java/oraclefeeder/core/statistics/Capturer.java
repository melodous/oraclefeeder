package oraclefeeder.core.statistics;

import oraclefeeder.core.domain.CacheColumn;
import oraclefeeder.core.domain.CacheIterateGroup;
import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.core.domain.Statistic;
import oraclefeeder.core.persistence.DynamicSelect;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.properties.xml.mapping.Query;
import oraclefeeder.properties.xml.mapping.querys.Parameter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Capturer {

    private List<CacheIterateGroup> cacheIterateGroups;
    private List<Metric> metrics;
    private Connection connection;


    public Capturer(List<CacheIterateGroup> cacheIterateGroups, List<Metric> metrics, Connection connection) {
        this.cacheIterateGroups = cacheIterateGroups;
        this.metrics = metrics;
        this.connection = connection;
    }

    public List<Statistic> getMetrics(){
        List<Statistic> statistics = new LinkedList<Statistic>();
        for(CacheIterateGroup cacheIterateGroup: this.cacheIterateGroups){
            Metric metric = this.findMetricById(cacheIterateGroup.getId());
            if(metric != null){
                List<Query> querys = metric.getQuery();

                List<String> arguments = new LinkedList<String>();
                List<String> argumentsIN = new LinkedList<String>();
                for(CacheResult cacheResult: cacheIterateGroup.getCacheResultList()){
                    for(Query query:querys){
                        arguments.clear();
                        argumentsIN.clear();
                        for(Parameter parameter:query.getParameters()){
                            if((parameter.getType() == null || parameter.getType().equals("literal"))&& parameter.getArgument().equals("IN")){
                                argumentsIN.add(parameter.getValue());
                            }
                            if((parameter.getType() != null && parameter.getType().equals("column"))) {
                                for(CacheColumn cacheColumn:cacheResult.getCacheColumns()) {
                                    if (parameter.getValue().equals(cacheColumn.getName())) {
                                        arguments.add(cacheColumn.getValue());
                                    }
                                }
                            }
                        }
                        DynamicSelect dynamicSelect = new DynamicSelect(this.connection);
                        dynamicSelect.preparedStatement(query.getStatement(), arguments, argumentsIN);
                        ResultSet queryResultSet = dynamicSelect.executeQuery();
                        statistics.addAll(this.getAllMetrics(queryResultSet, query, cacheResult, cacheIterateGroup));
                        dynamicSelect.close();

                    }
                }
            }
        }
        return statistics;
    }

    public List<Statistic> getAllMetrics(ResultSet resultSet, Query query, CacheResult cacheResult, CacheIterateGroup cacheIterateGroup){
        List<Statistic> metrics = new LinkedList<Statistic>();
        try {
            while(resultSet.next()){
                Statistic metric = new Statistic();
                for(Map.Entry<String, String> values:query.getValues().entrySet())  {
                    if("value".equals(values.getKey())){
                        metric.setValue(resultSet.getString(values.getKey()));
                    }
                }
                metric.setConfigPrefix(Settings.propertie().getGraphitePrefix());
                metric.setPrefixMetricGroup(cacheIterateGroup.getPrefix());
                metric.setQueryName(query.getName());
                metric.setInstanceName(cacheResult.getInstacesFrom());
                metric.setMetricName(resultSet.getString(query.getColumnMetricName()));
                metric.setTimesTamp(System.currentTimeMillis() / 1000L);
                metrics.add(metric);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return metrics;
    }



    public Metric findMetricById(Integer id){
        Metric metricFound = null;
        for(Metric metric: this.metrics){
            if(id.equals(metric.getId())){
                metricFound = metric;
            }
        }
        if(metricFound == null) {
            throw new NullPointerException();
        } else {
            return metricFound;
        }
    }
}
