package oraclefeeder.core.persistence.connection;

import oraclefeeder.properties.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDB {

    private static final OracleDB instance;
    private static Connection connec;
    static {
        instance = new OracleDB();
    }

    public static Connection connection() throws SQLException {
        if(instance.connec == null || instance.connec.isClosed()) {
            try {
                Class.forName(Settings.propertie().getDbDriver());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String connectionString = "jdbc:oracle:thin:@" + Settings.propertie().getDbHost() + ":" + Settings.propertie().getDbPort() + ":" + Settings.propertie().getDbDatabase();
            String username = Settings.propertie().getDbUser();
            String password = Settings.propertie().getDbPassword();
            instance.connec = DriverManager.getConnection(connectionString, username, password);

        }
        return instance.connec;
    }

    public static void close() throws SQLException {
        if(!instance.connec.isClosed()){
            instance.connec.close();
        }
    }

}