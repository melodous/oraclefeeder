package oraclefeeder.core.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DynamicSelect {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private boolean isPrepared;

    public DynamicSelect(Connection connection) {
        this.connection = connection;
        this.isPrepared = false;
    }

    public void preparedStatement(String select, List<String> arguments, List<String> parametersIN) {
        try {
            StringBuilder selectIN = new StringBuilder();
            if(parametersIN != null || parametersIN.size() != 0) {
                for(int i = 0; i<parametersIN.size(); i++){
                    selectIN.append("?,");
                }
                arguments.addAll(parametersIN);
                select = select + " (" + selectIN.deleteCharAt(selectIN.length()-1) + ")";
            }
            this.preparedStatement = this.connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int numberArgument = 1;
            for(String argument:arguments){
                this.preparedStatement.setString(numberArgument++, argument);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.isPrepared = true;
    }

    public ResultSet executeQuery() {
        ResultSet rs = null;
        if(isPrepared) {
            try {
                rs = this.preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void close() {
        try {
            this.preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void coonectionClose() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}