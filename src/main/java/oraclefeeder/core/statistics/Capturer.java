package oraclefeeder.core.statistics;

import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.properties.xml.mapping.Metric;

import java.util.List;

/**
 * Created by Alberto Pascual on 24/08/15.
 */
public class Capturer {

    private List<CacheResult> cacheResults;
    private List<Metric> metrics;


    public Capturer(List<CacheResult> cacheResults, List<Metric> metrics) {
        this.cacheResults = cacheResults;
        this.metrics = metrics;
    }

    public void getMetrics(){
        for(CacheResult cacheResult:this.cacheResults){
            Metric metric = this.findMetricById(cacheResult.getId());

//            for(Map<String, String>  result:cacheResult.getResult()){
//                for(Map.Entry<String,String> test:result.entrySet()){
//                    System.out.println("Value: " + test.getValue() + " Key: " + test.getKey());
//                }
//            }
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
