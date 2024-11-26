package domain.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DataBaseConnection {

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            InputStream input = DataBaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
