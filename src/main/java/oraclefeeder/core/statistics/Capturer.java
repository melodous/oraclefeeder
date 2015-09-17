package oraclefeeder.core.statistics;

import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.core.persistence.DynamicSelect;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.properties.xml.mapping.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto Pascual on 24/08/15.
 */
public class Capturer {

    private List<CacheResult> cacheResults;
    private List<Metric> metrics;
    private Connection connection;


    public Capturer(List<CacheResult> cacheResults, List<Metric> metrics, Connection connection) {
        this.cacheResults = cacheResults;
        this.metrics = metrics;
        this.connection = connection;
    }

    public void getMetrics(){

        List<MetricCapturer> metricCapturers = new LinkedList<MetricCapturer>();

        for(CacheResult cacheResult:this.cacheResults){
            Metric metric = this.findMetricById(cacheResult.getId());
            if(metric.getStatement() != null){
                List<Query> querys = metric.getQuery();
                for(Query query:querys){
                    List<String> arguments = new LinkedList<String>();
                    List<String> parametersIN = new LinkedList<String>();
                    Map<String,String> targeInstance = new HashMap<String, String>();
                    for(Map.Entry<String,String> param:query.getParamns().entrySet()){
                        if(param.getValue() != null && param.getValue().equals("column")){
                            //System.out.println(param.getValue());
                            Map.Entry<String, Map<String,String>> fristResult = cacheResult.getResult().entrySet().iterator().next();
                            for(Map.Entry<String,String> result: fristResult.getValue().entrySet()){
                                if(result.getKey().equals(param.getKey())) {

                                    String value = result.getValue();
                                    arguments.add(result.getValue());
                                    targeInstance.put(value, fristResult.getKey());
                                }
                            }
                            cacheResult.getResult().remove(fristResult.getKey());
//                            Map<String, String> para = cacheResult.getResult().get(0);
//                            for(Map.Entry<String,String> result: para.entrySet()){
//                                if(result.getKey().equals(param.getKey())) {
//                                    arguments.add(result.getValue());
//                                }
//                            }
                        } else {
                            parametersIN.add(param.getKey());
                        }
                    }
                    //System.out.println(arguments.get(0));
                    DynamicSelect dynamicSelect = new DynamicSelect(this.connection);
                    dynamicSelect.preparedStatement(query.getStatement(), arguments, parametersIN);
                    ResultSet resultSet = dynamicSelect.executeQuery();
                    try {
                        while(resultSet.next()) {
                            MetricCapturer metricCapturer = new MetricCapturer();
                            metricCapturer.setPrefix(metric.getPrefix());
                            if(metric.getInstanceFromType().equals("column")) {
                                //System.out.println(metric.getInstanceFrom());
                                //metricCapturer.setMetricInstanceForm(resultSet.getString(metric.getInstanceFrom()));
                            } else {
                                //TODO
                            }
                            String t = targeInstance.get(resultSet.getString("target_name"));

                            metricCapturer.setMetricInstanceForm("");
                            metricCapturer.setMetricName(resultSet.getString("metric_column"));
                            metricCapturer.setValue(resultSet.getString("value"));
                            metricCapturers.add(metricCapturer);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    dynamicSelect.close();

                }
            }
       }

    }



    public Metric findMetricById(Integer id){
        Metric metricFound = null;
        for(Metric metric: this.metrics){
            if(id == metric.getId()){
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
