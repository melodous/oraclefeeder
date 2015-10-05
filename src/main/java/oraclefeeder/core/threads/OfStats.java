package oraclefeeder.core.threads;

import oraclefeeder.properties.Settings;
import oraclefeeder.sender.Sender;
import oraclefeeder.utils.Utils;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadMXBean;

/**
 * Created by Alberto Pascual on 5/10/15.
 */
public class OfStats implements Runnable {

    private Sender sender;

    public OfStats(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while(true){
            long start_time=System.currentTimeMillis();
            this.getOfStats();
            try {
                long elapsed = (System.currentTimeMillis() - start_time);
                Thread.sleep(Utils.calculateNextIteration(elapsed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getOfStats(){
        Runtime runtime = Runtime.getRuntime();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.os.freememory " + String.valueOf(runtime.freeMemory()) + " " + System.currentTimeMillis() / 1000L + "\n");

        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.os.maxmemory " + String.valueOf(runtime.maxMemory()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.os.totalmemory " + String.valueOf(runtime.totalMemory()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.os.availableprocessors " + String.valueOf(runtime.availableProcessors()) + " " + System.currentTimeMillis() / 1000L + "\n");
        for(GarbageCollectorMXBean garbageCollectorMXBean: ManagementFactory.getGarbageCollectorMXBeans()){
            String name = garbageCollectorMXBean.getName().replace(" ", "_");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.gc." + name + ".gc_collection_count " + String.valueOf(garbageCollectorMXBean.getCollectionCount()) + " " + System.currentTimeMillis() / 1000L + "\n");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.gc." + name + ".gc_collection_time" + String.valueOf(garbageCollectorMXBean.getCollectionTime()) + " " + System.currentTimeMillis() / 1000L + "\n");
        }
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memory.heapUsage_init " + String.valueOf(memoryMXBean.getHeapMemoryUsage().getInit()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memory.heapUsage_used " + String.valueOf(memoryMXBean.getHeapMemoryUsage().getUsed()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memory.heapUsage_committed " + String.valueOf(memoryMXBean.getHeapMemoryUsage().getCommitted())  + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memory.heapUsage_max " + String.valueOf(memoryMXBean.getHeapMemoryUsage().getMax()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.compiler.total_compilation_time " + String.valueOf(compilationMXBean.getTotalCompilationTime()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.classloading.class_count " + String.valueOf(classLoadingMXBean.getLoadedClassCount()) + " " + System.currentTimeMillis() / 1000L + "\n");
        for(MemoryPoolMXBean memoryPoolMXBean:ManagementFactory.getMemoryPoolMXBeans()){
            String name = memoryPoolMXBean.getName().replace(" ", "_");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memoryPool." + name + ".init " + String.valueOf(memoryPoolMXBean.getUsage().getInit()) + " " + System.currentTimeMillis() / 1000L + "\n");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memoryPool." + name + ".used " + String.valueOf(memoryPoolMXBean.getUsage().getUsed()) + " " + System.currentTimeMillis() / 1000L + "\n");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memoryPool." + name + ".committed " + String.valueOf(memoryPoolMXBean.getUsage().getCommitted()) + " " + System.currentTimeMillis() / 1000L + "\n");
            this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.memoryPool." + name + ".max " + String.valueOf(memoryPoolMXBean.getUsage().getMax()) + " " + System.currentTimeMillis() / 1000L + "\n");
        }
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.threads.daemon " + String.valueOf(threadMXBean.getDaemonThreadCount()) + " " + System.currentTimeMillis() / 1000L + "\n");
        this.sender.send(Settings.propertie().getGraphitePrefix() + ".of_stats.jvm.threads.thread_count " + String.valueOf(threadMXBean.getThreadCount())  + " " + System.currentTimeMillis() / 1000L + "\n");
    }
}
