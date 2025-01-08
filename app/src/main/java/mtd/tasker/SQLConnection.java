package mtd.tasker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tasker";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin123";

    private static final class InstanceHolder {
        private static final SQLConnection INSTANCE = new SQLConnection();
    }

    public static SQLConnection getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
