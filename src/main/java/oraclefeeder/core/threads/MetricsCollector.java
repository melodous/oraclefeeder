package oraclefeeder.core.threads;

import oraclefeeder.core.domain.Statistic;
import oraclefeeder.core.statistics.Capturer;
import oraclefeeder.properties.Settings;
import oraclefeeder.sender.Sender;

import java.util.List;

public class MetricsCollector implements Runnable {

    private final Capturer capturer;
    private Sender sender;
    private int threadNum;

    public MetricsCollector(Capturer capturer, Sender sender, int threadNum) {
        this.capturer = capturer;
        this.sender = sender;
        this.threadNum = threadNum;
    }

    public void run(){
        Thread.currentThread().setName(String.valueOf(this.threadNum));
        long start_time=System.currentTimeMillis();
        List<Statistic> metrics = this.capturer.getMetrics();
        long serverIn = (System.currentTimeMillis() - start_time);
        this.sendAllMetrics(metrics);
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.metrics.workers." + Thread.currentThread().getName() + ".retrieve_time " + serverIn + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.metrics.workers." + Thread.currentThread().getName() + ".number_metrics " + metrics.size() + " " + System.currentTimeMillis() / 1000L + "\n");
    }

    public void sendAllMetrics(List<Statistic> metrics){
        for(Statistic metric:metrics){
            this.sender.send(metric.generateMetric()+"\n");
        }
    }
}