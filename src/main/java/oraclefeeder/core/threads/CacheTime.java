package oraclefeeder.core.threads;

import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.core.persistence.StaticSelect;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alberto Pascual on 21/08/15.
 */
public class CacheTime implements Runnable {

    private Map<Integer,Metric> metrics;
    private List<CacheResult> cacheResult;
    private Connection connection;
    private Boolean isRunning;

    public CacheTime(Connection connection) {
        this.metrics = new HashMap<Integer, Metric>();
        this.connection = connection;
    }

    public void put(Integer id, Metric metric){
        this.metrics.put(id,metric);
    }

    public void run(){



        this.isRunning = true;
        Integer totalRows = 0;
        this.cacheResult = new LinkedList<CacheResult>();
        for(Map.Entry<Integer,Metric> metricMap:this.metrics.entrySet()){
            CacheResult cacheResult = new CacheResult();
            Metric metric = metricMap.getValue();
            StaticSelect staticSelect = new StaticSelect(this.connection);
            ResultSet resultSet = staticSelect.executeQuery(metric.getStatement());
            Integer rows = 0;
            if(resultSet != null) {
                try {
                    Map<String, Map<String,String>> allResults = new LinkedHashMap<String, Map<String, String>>();
                    while(resultSet.next()) {
                        Set<String> uniqueKey = new HashSet<String>();
                        Map<String, String> rowResult = new HashMap<String, String>();
                        for (Map.Entry<String, String> columns : metric.getColumns().entrySet()) {
                            rowResult.put(columns.getKey(), resultSet.getString(columns.getKey()));
                            rows++;
                        }
                        if(allResults.containsKey(resultSet.getString(metric.getInstanceFrom()) + resultSet.getString("host_name") + resultSet.getString("database_name"))) {
                            System.out.println(resultSet.getString(metric.getInstanceFrom()) + " " + resultSet.getString("host_name") + " " + resultSet.getString("database_name"));
                        } else {

                        }
                        allResults.put(resultSet.getString(metric.getInstanceFrom()) + resultSet.getString("host_name") + resultSet.getString("database_name"), rowResult);
                    }
                    cacheResult.setResult(allResults);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Error
            }
            totalRows = totalRows + rows * metricMap.getValue().getQuery().size();
            cacheResult.setId(metric.getId());
            cacheResult.setTotalRows(totalRows);
            totalRows = 0;
            this.cacheResult.add(cacheResult);
            staticSelect.close();
        }
        this.isRunning = false;
    }

    public List<CacheResult> allocateQuerys(List<Metric> metrics, Integer totalStaticQuerys){

        Integer maxThreads =  Integer.valueOf(Settings.propertie().getMaxThreads());
        Integer querysCount = 0;

        Map<Integer,CacheResult> mapResult = new HashMap<Integer, CacheResult>();
        for(CacheResult cacheResult:this.cacheResult){
            querysCount = querysCount + cacheResult.getTotalRows();
            mapResult.put(cacheResult.getId(), cacheResult);
        }

        querysCount = querysCount + totalStaticQuerys;
        Integer queryForThread = querysCount / maxThreads;
        Integer rest = querysCount % maxThreads;

        List<CacheResult> cacheResultList = new LinkedList<CacheResult>();
        for(Metric metric:metrics){
            CacheResult cacheResult = mapResult.get(metric.getId());

            if(cacheResult != null){
                Integer max = 0;
                CacheResult cacheResultForThread;
                Map<String,Map<String, String>> result = new HashMap<String, Map<String, String>>();
                for(Map.Entry<String, Map<String,String>> listMapResult:cacheResult.getResult().entrySet()){
                    max++;
                    if(max%queryForThread == 0){
                        result.put(listMapResult.getKey(), listMapResult.getValue());
                        cacheResultForThread = new CacheResult();
                        cacheResultForThread.setTotalRows(cacheResult.getTotalRows());
                        cacheResultForThread.setId(cacheResult.getId());
                        cacheResultForThread.setResult(result);
                        cacheResultList.add(cacheResultForThread);

                        result =  new HashMap<String, Map<String, String>>();
                    } else {
                        result.put(listMapResult.getKey(), listMapResult.getValue());
                    }
                }
            }
        }
        return cacheResultList;
    }

    public List<CacheResult> getCacheResult() {
        return cacheResult;
    }

    public Boolean isRunning() {
        return isRunning;
    }

}
