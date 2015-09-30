package oraclefeeder.properties;

import oraclefeeder.utils.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static Settings instance;
    private String l4jPattern;
    private String l4jPatternConfig;
    private String appName;
    private String l4jName;
    private String l4jGetLogger;
    private String dbHost;
    private String dbPort;
    private String dbDatabase;
    private String dbUser;
    private String dbPassword;
    private String graphiteHost;
    private String graphitePort;
    private String graphiteReconnectTimeout;
    private String graphiteSendBufferSize;
    private String dbDriver;
    private String maxThreads;
    private String threadsIntervalSec;
    private String graphitePrefix;

    private Settings(){}

    public static synchronized Settings propertie(){
        if(instance == null) {
            instance = new Settings();
            instance.readBaseProperties();
            instance.readL4jProperties();
            instance.readGrapbhiteProperties();
        }
        return instance;
    }

    private static void readBaseProperties(){
        FileInputStream fileProperties;
        try {
            fileProperties = new FileInputStream(Constants.ORACLEFEEDER_PROPS);
            Properties confProperties = new Properties();
            confProperties.load(fileProperties);
            instance.setDbHost(confProperties.getProperty("db_host"));
            instance.setDbPort(confProperties.getProperty("db_port"));
            instance.setDbDatabase(confProperties.getProperty("db_database"));
            instance.setDbUser(confProperties.getProperty("db_user"));
            instance.setDbPassword(confProperties.getProperty("db_password"));
            instance.setDbDriver(confProperties.getProperty("of_db_driver"));
            instance.setMaxThreads(confProperties.getProperty("of_use_maxthreads"));
            instance.setThreadsIntervalSec(confProperties.getProperty("of_interval_sec"));
            fileProperties.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readGrapbhiteProperties(){
        FileInputStream fileProperties;
        try {
            fileProperties = new FileInputStream(Constants.GRAPHITE_PROPS);
            Properties confProperties = new Properties();
            confProperties.load(fileProperties);
            instance.setGraphiteHost(confProperties.getProperty("graphite_host"));
            instance.setGraphitePort(confProperties.getProperty("graphite_port"));
            instance.setGraphiteReconnectTimeout(confProperties.getProperty("graphite_reconnectTimeout"));
            instance.setGraphiteSendBufferSize(confProperties.getProperty("graphite_sendBufferSize"));
            instance.setGraphitePrefix(confProperties.getProperty("graphite_metric_prefix"));
            fileProperties.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readL4jProperties(){
        FileInputStream fileProperties;
        try {
            fileProperties = new FileInputStream(Constants.L4J_PROPS);
            Properties confProperties = new Properties();
            confProperties.load(fileProperties);
            instance.setAppName(confProperties.getProperty("appName"));
            instance.setL4jPattern(confProperties.getProperty("l4jPattern"));
            instance.setL4jPatternConfig(confProperties.getProperty("l4jPatternConfig:"));
            instance.setL4jName(confProperties.getProperty("l4jName"));
            instance.setL4jGetLogger(confProperties.getProperty("l4jgetLogger"));
            fileProperties.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getL4jPattern() {
        return l4jPattern;
    }

    public void setL4jPattern(String l4jPattern) {
        this.l4jPattern = l4jPattern;
    }

    public String getL4jPatternConfig() {
        return l4jPatternConfig;
    }

    public void setL4jPatternConfig(String l4jPatternConfig) {
        this.l4jPatternConfig = l4jPatternConfig;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getL4jName() {
        return l4jName;
    }

    public void setL4jName(String l4jName) {
        this.l4jName = l4jName;
    }

    public String getL4jGetLogger() {
        return l4jGetLogger;
    }

    public void setL4jGetLogger(String l4jGetLogger) {
        this.l4jGetLogger = l4jGetLogger;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getGraphiteHost() {
        return graphiteHost;
    }

    public void setGraphiteHost(String graphiteHost) {
        this.graphiteHost = graphiteHost;
    }

    public String getGraphitePort() {
        return graphitePort;
    }

    public void setGraphitePort(String graphitePort) {
        this.graphitePort = graphitePort;
    }

    public String getGraphiteReconnectTimeout() {
        return graphiteReconnectTimeout;
    }

    public void setGraphiteReconnectTimeout(String graphiteReconnectTimeout) {
        this.graphiteReconnectTimeout = graphiteReconnectTimeout;
    }

    public String getGraphiteSendBufferSize() {
        return graphiteSendBufferSize;
    }

    public void setGraphiteSendBufferSize(String graphiteSendBufferSize) {
        this.graphiteSendBufferSize = graphiteSendBufferSize;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(String maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getThreadsIntervalSec() {
        return threadsIntervalSec;
    }

    public void setThreadsIntervalSec(String threadsIntervalSec) {
        this.threadsIntervalSec = threadsIntervalSec;
    }

    public String getGraphitePrefix() {
        return graphitePrefix;
    }

    public void setGraphitePrefix(String graphitePrefix) {
        this.graphitePrefix = graphitePrefix;
    }
}