package oraclefeeder.core.threads;

import oraclefeeder.core.domain.CacheIterateGroup;
import oraclefeeder.core.domain.ThreadCacheResult;
import oraclefeeder.core.statistics.Capturer;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.sender.Sender;
import oraclefeeder.sender.graphite.sender.GraphiteSender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager implements Runnable {

    private List<Metric> metrics;
    private CacheTime cacheTime;
    private Connection connection;
    private int totalStaticQuerys;
    private Map<Integer,List<CacheIterateGroup>> cacheIterateGroups;
    private Boolean fristIteration;
    private Sender sender;

    private ThreadCacheResult threadCacheResult;


    public ThreadManager(List<Metric> metrics, Connection connection) throws SQLException {
        this.metrics = metrics;
        this.connection = connection;
        //Setting metric in cacheTime and id
        this.cacheTime = this.readMetrics();
        //Start cacheTime
        new Thread(this.cacheTime, "CacheTime").start();
        this.sender = this.getSender();
    }

    private CacheTime readMetrics(){
        this.threadCacheResult = new ThreadCacheResult();
        this.threadCacheResult.isRunning(true);
        this.threadCacheResult.setCacheIterateGroups(new LinkedList<CacheIterateGroup>());
        CacheTime cache = new CacheTime(this.connection, this.threadCacheResult);
        for(Metric metric:this.metrics){
            if(metric.getStatement() != null){
                cache.put(metric.getId(), metric);
            } else {
                this.totalStaticQuerys = this.totalStaticQuerys + metric.getQuery().size();
            }
        }
        return cache;
    }

    public void run() {
        this.fristIteration = true;
        while(true){
            this.cacheIterateGroups = this.getCache();
            this.launchThreads(this.cacheIterateGroups);

            try {
                Thread.sleep(60000000000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<Integer,List<CacheIterateGroup>> getCache(){

        if(this.fristIteration){
            while(true){
                if(this.threadCacheResult.isRunning() != null && !this.threadCacheResult.isRunning()) {
                    this.fristIteration = Boolean.valueOf(false);
                    return this.cacheTime.allocateQuerys(this.metrics, this.totalStaticQuerys);
                }
            }
        } else {
            if(this.threadCacheResult.isRunning() != null && !this.threadCacheResult.isRunning()) {
                return this.cacheTime.allocateQuerys(this.metrics, this.totalStaticQuerys);
            } else {
                return this.cacheIterateGroups;
            }
        }
    }


    private void launchThreads(Map<Integer,List<CacheIterateGroup>> launchThreadsCache) {
        this.checkConnection();
        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(Settings.propertie().getMaxThreads()));
        for(Map.Entry<Integer,List<CacheIterateGroup>> mapCache: launchThreadsCache.entrySet()){
            Capturer capturer = new Capturer(mapCache.getValue(), this.metrics, this.connection);
            Runnable worker = new MetricsCollector(capturer, this.sender);
            executor.execute(worker);
        }
        executor.shutdown();
    }

    public Sender getSender(){
        GraphiteSender sender = new GraphiteSender();
        sender.init();
        return sender;
    }

    public void checkConnection(){
        //TODO
    }

}
