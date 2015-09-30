package oraclefeeder.core.threads;

import oraclefeeder.core.domain.CacheColumn;
import oraclefeeder.core.domain.CacheIterateGroup;
import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.core.domain.ThreadCacheResult;
import oraclefeeder.core.persistence.StaticSelect;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto Pascual on 21/08/15.
 */
public class CacheTime implements Runnable {

    private Map<Integer,Metric> metrics;
    private Connection connection;
    private ThreadCacheResult threadCacheResult;
    private Boolean test;

    public CacheTime(Connection connection, ThreadCacheResult threadCacheResult) {
        this.metrics = new HashMap<Integer, Metric>();
        this.connection = connection;
        this.threadCacheResult = threadCacheResult;
    }

    public void put(Integer id, Metric metric){
        this.metrics.put(id,metric);
    }

    public void run(){
        this.test = true;
        this.threadCacheResult.isRunning(true);
        Long intervalSec = Long.valueOf(Settings.propertie().getThreadsIntervalSec());
        Long intervalMili = intervalSec * 1000;
        Boolean start = true;

        while(start){

            this.threadCacheResult.getCacheIterateGroups().clear();
            CacheIterateGroup cacheIterateGroup;

            for(Map.Entry<Integer,Metric> metricMap:this.metrics.entrySet()){
                Metric metric = metricMap.getValue();
                cacheIterateGroup = new CacheIterateGroup();
                Boolean isInstanceFromInConf = false;
                cacheIterateGroup.setId(metric.getId());
                cacheIterateGroup.setPrefix(metric.getPrefix());

                if(metric.getInstanceFrom() != null) {
                    isInstanceFromInConf = true;
                }
                StaticSelect staticSelect = new StaticSelect(this.connection);
                ResultSet resultSet = staticSelect.executeQuery(metric.getStatement());
                if(resultSet != null) {
                    if(resultSet != null) {
                        try {
                            int auxKey = 0;
                            while(resultSet.next()){
                                CacheResult cacheResult = new CacheResult();
                                String key = null;
                                String instanceFrom = null;
                                List<CacheColumn> cacheColumns = new LinkedList<CacheColumn>();
                                for (Map.Entry<String, String> columns : metric.getColumns().entrySet()) {
                                    CacheColumn cacheColumn = new CacheColumn(columns.getKey(), resultSet.getString(columns.getKey()));
                                    if(columns.getValue() != null) {
                                        String type = columns.getValue().toUpperCase();
                                        if(type.equals("KEY")) {
                                            key = resultSet.getString(columns.getKey());
                                        } else if(type.equals("INSTANCESFROM") && !isInstanceFromInConf) {
                                            instanceFrom = resultSet.getString(columns.getKey());
                                        }
                                    }
                                    cacheColumns.add(cacheColumn);
                                }
                                if(isInstanceFromInConf){
                                    cacheResult.setInstacesFrom(metric.getInstanceFrom());
                                } else {
                                    cacheResult.setInstacesFrom(instanceFrom);
                                }
                                cacheResult.setCacheColumns(cacheColumns);
                                if(key != null) {
                                    cacheIterateGroup.getCacheResult().put(key, cacheResult);
                                } else {
                                    cacheIterateGroup.getCacheResult().put(String.valueOf(auxKey), cacheResult);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Error
                    }
                    cacheIterateGroup.setTotalQuerys(cacheIterateGroup.getCacheResult().size());
                    cacheIterateGroup.convertMapToList();
                    this.threadCacheResult.add(cacheIterateGroup);
                    staticSelect.close();
                }
            }
            try {
                this.test = false;
                this.threadCacheResult.isRunning(false);
                Thread.sleep(300000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<Integer,List<CacheIterateGroup>> allocateQuerys(List<Metric> metrics, Integer totalStaticQuerys){

        Integer maxThreads =  Integer.valueOf(Settings.propertie().getMaxThreads());

        List<Integer> totalQuerysForThread = Utils.calculateQuerysForThreadReal(this.threadCacheResult, metrics, maxThreads);

        Map<Integer,List<CacheIterateGroup>> allocate = new HashMap<Integer, List<CacheIterateGroup>>();
        List<CacheIterateGroup> cacheIterateGroups = new LinkedList<CacheIterateGroup>();
        int threadId = 0

                ;
        Boolean join = false;
        for (CacheIterateGroup cacheIterateGroup : this.threadCacheResult.getCacheIterateGroups()) {
            List<Integer> auxTotalQuerys = new LinkedList<Integer>();
            for(Integer totalQuery: totalQuerysForThread) {
                List<CacheResult> cacheResults;
                if(totalQuery <= cacheIterateGroup.getCacheResultList().size()){
                    cacheResults = new LinkedList<CacheResult>(cacheIterateGroup.getCacheResultList().subList(0,totalQuery));
                    cacheIterateGroup.getCacheResultList().removeAll(cacheResults);

                    CacheIterateGroup cacheIterateGroupAux = new CacheIterateGroup();
                    cacheIterateGroupAux.setId(cacheIterateGroup.getId());
                    cacheIterateGroupAux.setTotalQuerys(cacheIterateGroup.getTotalQuerys());
                    cacheIterateGroupAux.setPrefix(cacheIterateGroup.getPrefix());
                    cacheIterateGroupAux.setCacheResultList(cacheResults);
                    if(join) {
                        List<CacheIterateGroup> aux2 = allocate.get(threadId-1);
                        aux2.add(cacheIterateGroupAux);
                        join = false;
                    } else {
                        List<CacheIterateGroup> aux = new LinkedList<CacheIterateGroup>();
                        aux.add(cacheIterateGroupAux);
                        allocate.put(threadId++, aux);
                        //System.out.println(cacheResults.size() + " - " + totalQuery);
                    }

                } else if (totalQuery < cacheIterateGroup.getCacheResult().size() && cacheIterateGroup.getCacheResult().size() != 0) {
                    cacheResults = new LinkedList<CacheResult>(cacheIterateGroup.getCacheResultList());
                    cacheIterateGroup.getCacheResultList().removeAll(cacheResults);
                    auxTotalQuerys.add(totalQuery - cacheResults.size());
                    if(cacheResults.size() != 0) {
                        CacheIterateGroup cacheIterateGroupAux = new CacheIterateGroup();
                        cacheIterateGroupAux.setId(cacheIterateGroup.getId());
                        cacheIterateGroupAux.setTotalQuerys(cacheIterateGroup.getTotalQuerys());
                        cacheIterateGroupAux.setPrefix(cacheIterateGroup.getPrefix());
                        cacheIterateGroupAux.setCacheResultList(cacheResults);

                        List<CacheIterateGroup> aux = new LinkedList<CacheIterateGroup>();
                        aux.add(cacheIterateGroupAux);
                        allocate.put(threadId++, aux);
                        join = true;;
                    }
                } else {
                    auxTotalQuerys.add(totalQuery);
                }
            }

            totalQuerysForThread.clear();
            totalQuerysForThread.addAll(auxTotalQuerys);
        }

       return allocate;
    }

}
