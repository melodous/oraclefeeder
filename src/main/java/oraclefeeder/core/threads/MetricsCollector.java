package oraclefeeder.core.threads;

import oraclefeeder.core.statistics.Capturer;

/**
 * Created by Alberto Pascual on 24/08/15.
 */
public class MetricsCollector implements Runnable {

    private final Capturer capturer;

    public MetricsCollector(Capturer capturer) {
        this.capturer = capturer;
    }

    public void run(){
        long start_time=System.currentTimeMillis();
        this.capturer.getMetrics();
        long serverIn = (System.currentTimeMillis() - start_time);
        //System.out.println("TIME: " + serverIn);
    }
}
