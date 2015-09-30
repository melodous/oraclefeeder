package oraclefeeder.core;

import oraclefeeder.core.persistence.connection.OracleDB;
import oraclefeeder.core.threads.ThreadManager;
import oraclefeeder.exception.OracleFeederException;
import oraclefeeder.properties.xml.ReadQueryXml;
import oraclefeeder.properties.xml.mapping.Metric;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alberto Pascual Corpas on 03/08/15.
 */
public class OracleFeeder {
    public static void main(String[] args) throws SQLException, JAXBException, OracleFeederException {
        List<Metric> metrics = ReadQueryXml.getMetricConfig();
        Connection orableDB = OracleDB.connection();
        ThreadManager threadManager = new ThreadManager(metrics, orableDB);
        threadManager.run();
    }
}
