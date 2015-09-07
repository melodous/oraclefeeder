package oraclefeeder.core;

import oraclefeeder.core.persistence.connection.OracleDB;
import oraclefeeder.core.threads.ThreadManager;
import oraclefeeder.exception.OracleFeederException;
import oraclefeeder.properties.xml.ReadQueryXml;
import oraclefeeder.properties.xml.mapping.Metric;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alberto Pascual Corpas on 03/08/15.
 */
public class OracleFeeder {
    public static void main(String[] args) throws SQLException, JAXBException, OracleFeederException {
        List<Metric> metrics = ReadQueryXml.getMetricConfig();
        ThreadManager threadManager = new ThreadManager(metrics, OracleDB.connection());
        threadManager.run();
//        for(Metric metric:metrics){
//            System.out.println("Prefix " + metric.getPrefix());
//            System.out.println("Statement " + metric.getStatement());
//            System.out.println("CacheTime " + metric.getCacheTimeSec() );
//            System.out.println("Cache " + metric.getCache());
//            System.out.println("map-por-hacer ");
//            System.out.println("IntanceFrom " + metric.getInstanceFrom());
//            System.out.println("IntanceFromType " + metric.getInstanceFromType());
//            for(Map.Entry<String,String> entrys:metric.getColumns().entrySet()){
//                System.out.println("Column " + entrys.getKey() + " / Type " + entrys.getKey());
//            }
//            for(Query query:metric.getQuery()) {
//                System.out.println("Name " + query.getName());
//                System.out.println("Statement " + query.getStatement());
//                System.out.println("InstanceFrom " + query.getInstanceFrom());
//                System.out.println("InstanceFromType " + query.getInstanceFromType());
//                System.out.println("ColumnMetricName " + query.getColumnMetricName());
//                for(Map.Entry<String,String> values: query.getValues().entrySet()){
//                    System.out.println("Name " + values.getKey() + " / MetricName " + values.getValue());
//                }
//            }
//
//        }

    }
}
