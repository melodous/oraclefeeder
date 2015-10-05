package oraclefeeder.utils;

import oraclefeeder.core.domain.CacheIterateGroup;
import oraclefeeder.core.domain.ThreadCacheResult;
import oraclefeeder.core.logs.L4j;
import oraclefeeder.core.persistence.connection.OracleDB;
import oraclefeeder.properties.Settings;
import oraclefeeder.properties.xml.mapping.Metric;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static int countIterateQuerys(ThreadCacheResult threadCacheResult){
        int totalQuerys = 0;
        for(CacheIterateGroup cacheIterateGroup: threadCacheResult.getCacheIterateGroups()){
            totalQuerys = totalQuerys + cacheIterateGroup.getCacheResult().size();
        }
        return totalQuerys;
    }

    public static int countSingleQuery(List<Metric> metrics){
        int totalSingleQuery = 0;
        for(Metric metric:metrics){
            if(metric.getStatement() == null) {
                totalSingleQuery = totalSingleQuery + metric.getQuery().size();
            }
        }
        return totalSingleQuery;
    }

    public static List<Integer> calculateQuerysForThreadReal(ThreadCacheResult threadCacheResult, List<Metric> metrics, Integer maxThreads){
        Integer totalQuerys = Utils.countIterateQuerys(threadCacheResult);
        Integer totalSingleQuerys = Utils.countSingleQuery(metrics);
        Integer minQuerysForThread = (totalQuerys + totalSingleQuerys) / maxThreads;
        Integer rest = (totalQuerys + totalSingleQuerys) % maxThreads;
        List<Integer> querysForThreadReal = new LinkedList<Integer>();
        int restCount = 0;
        for(int i = 0; i < maxThreads;i++) {
            restCount++;
            int aux;
            if(restCount <= rest) {
                aux = minQuerysForThread + 1;
            } else {
                aux = minQuerysForThread;
            }
            querysForThreadReal.add(aux);
        }
        return querysForThreadReal;
    }

    public static Connection getOracleDBConnection() throws SQLException {
        boolean isConnectToDB = false;
        Connection orableDB = null;
        while(!isConnectToDB) {
            orableDB = OracleDB.connection();
            isConnectToDB = (orableDB != null && !orableDB.isClosed());
            if(!isConnectToDB) {
                try {
                    Thread.sleep(Settings.propertie().getReconnectTimeoutDB());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        L4j.getL4j().info("Connected to DB (Host: " + Settings.propertie().getDbHost() + " Port: " + Settings.propertie().getDbPort() + " DataBase: "  + Settings.propertie().getDbDatabase() + ")");
        return orableDB;
    }

    public static Long calculateNextIteration(Long delay){
        Long elapsed = null;
        Long auxElapsed = Settings.propertie().getThreadsIntervalSec();
        Boolean calculated = false;
        while(!calculated) {
            if(auxElapsed >= delay) {
                elapsed = auxElapsed - delay;
                calculated = true;
            } else {
                auxElapsed = auxElapsed + Settings.propertie().getThreadsIntervalSec();
            }
        }
        return elapsed;
    }
}
