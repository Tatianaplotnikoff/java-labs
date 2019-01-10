package ua.nure.plotnykova.usermanagement.db.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private String driver;
    private String url;
    private String userName;
    private String password;

    public ConnectionFactoryImpl(String driver, String url, String userName, String password) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public ConnectionFactoryImpl(Properties properties) {
        driver = properties.getProperty("connection.driver");
        url = properties.getProperty("connection.url");
        userName = properties.getProperty("connection.user");
        password = properties.getProperty("connection.password");
    }

    @Override
    public Connection createConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Driver not found.");
        }

        return DriverManager.getConnection(url, userName, password);
    }
}
