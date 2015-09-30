package oraclefeeder.core.threads;

import oraclefeeder.core.domain.Statistic;
import oraclefeeder.core.statistics.Capturer;
import oraclefeeder.sender.Sender;

import java.util.List;

/**
 * Created by Alberto Pascual on 24/08/15.
 */
public class MetricsCollector implements Runnable {

    private final Capturer capturer;
    private Sender sender;

    public MetricsCollector(Capturer capturer, Sender sender) {
        this.capturer = capturer;
        this.sender = sender;
    }

    public void run(){
        long start_time=System.currentTimeMillis();
        List<Statistic> metrics = this.capturer.getMetrics();
        long serverIn = (System.currentTimeMillis() - start_time);
        this.sendAllMetrics(metrics);
        System.out.println("Hilo: " + Thread.currentThread().getName() + " TIME: " + serverIn + " CANTIDAD DE METRICAS: " + metrics.size());
    }

    public void sendAllMetrics(List<Statistic> metrics){
        for(Statistic metric:metrics){
            this.sender.send(metric.generateMetric()+"\n");
        }
    }
}
