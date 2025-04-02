package stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseModel {
    //커넥션
    protected Connection connection;
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:system";;
    private static final String USER = "hr";
    private static final String PASSWORD = "hr";
	
    BaseModel(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
