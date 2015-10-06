package oraclefeeder.core;

import oraclefeeder.core.logs.L4j;
import oraclefeeder.core.threads.ThreadManager;
import oraclefeeder.exception.OracleFeederException;
import oraclefeeder.properties.xml.ReadQueryXml;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.utils.Utils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class OracleFeeder {
    public static void main(String[] args) throws SQLException, JAXBException, OracleFeederException {
        File f = new File(".");
        System.out.println(f.getAbsolutePath());
        L4j.getL4j().info("##########################################");
        L4j.getL4j().info("# Oracle grid control metrics collection #");
        L4j.getL4j().info("#             OracleFeeder               #");
        L4j.getL4j().info("##########################################");
        L4j.getL4j().info("Loading settings: query.xml, metrics.xml");
        List<Metric> metrics = ReadQueryXml.getMetricConfig();
        ThreadManager threadManager = new ThreadManager(metrics, Utils.getOracleDBConnection());
        threadManager.run();
    }
}
