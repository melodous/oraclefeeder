package oraclefeeder.core.persistence.connection;

import oraclefeeder.properties.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDB {

    private static final OracleDB instance;
    private static Connection connection;
    static {
        instance = new OracleDB();
    }

    public static Connection connection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            try {
                Class.forName(Settings.propertie().getDbDriver());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String connectionString = "jdbc:oracle:thin:@" + Settings.propertie().getDbHost() + ":" + Settings.propertie().getDbPort() + ":" + Settings.propertie().getDbDatabase();
            String username = Settings.propertie().getDbUser();
            String password = Settings.propertie().getDbPassword();
            connection = DriverManager.getConnection(connectionString, username, password);

        }
        return connection;
    }
}