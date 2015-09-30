package oraclefeeder.utils;

import oraclefeeder.core.domain.CacheIterateGroup;
import oraclefeeder.core.domain.ThreadCacheResult;
import oraclefeeder.properties.xml.mapping.Metric;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alberto Pascual on 22/09/15.
 */
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
            int aux = 0;
            if(restCount <= rest) {
                aux = minQuerysForThread + 1;
            } else {
                aux = minQuerysForThread;
            }
            querysForThreadReal.add(aux);
        }
        return querysForThreadReal;
    }
}
