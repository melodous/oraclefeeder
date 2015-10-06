package oraclefeeder.core.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaticSelect {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public StaticSelect(Connection connection) {
        this.connection = connection;
    }

    public ResultSet executeQuery(String select) {
        ResultSet resultSet = null;
        try {
            this.preparedStatement = this.connection.prepareStatement(select);
            resultSet = this.preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void close() {
        try {
            this.preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
