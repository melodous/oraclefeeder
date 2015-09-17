package oraclefeeder.core.threads;

import oraclefeeder.core.domain.CacheResult;
import oraclefeeder.core.statistics.Capturer;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alberto Pascual on 21/08/15.
 */
public class ThreadManager implements Runnable {

    private List<Metric>  metrics;
    private CacheTime cacheTime;
    private Connection connection;
    private Integer totalStaticQuerys;

    public ThreadManager(List<Metric> metrics, Connection connection) throws SQLException {
        this.metrics = metrics;
        this.cacheTime = new CacheTime(connection);
        this.connection = connection;
        this.totalStaticQuerys = 0;
    }

    public void run() {
        boolean start = true;
        while(start){
            this.launchThreads(getCache());
            try {
                Thread.sleep(60000000000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CacheResult> getCache(){
        for(Metric metric:this.metrics){
            if(metric.getStatement() != null){
                this.cacheTime.put(metric.getId(), metric);
            } else {
                this.totalStaticQuerys = this.totalStaticQuerys + metric.getQuery().size();
            }
        }
        this.cacheTime.run();
        return allocateQuerys();
    }
    public List<CacheResult> allocateQuerys(){
        Boolean done = false;
        List<CacheResult> cacheResults = null;
        while(!done) {
            if(!this.cacheTime.isRunning()) {
                cacheResults = this.cacheTime.allocateQuerys(this.metrics, this.totalStaticQuerys);
                done = true;
            }
        }
        return cacheResults;
    }

    public void launchThreads(List<CacheResult> cacheResults) {
        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(Settings.propertie().getMaxThreads()));
        for(CacheResult cacheResult: cacheResults) {
            Capturer capturer = new Capturer(cacheResults, this.metrics, this.connection);
            Runnable worker = new MetricsCollector(capturer);
            executor.execute(worker);
        }
        executor.shutdown();
    }

}
