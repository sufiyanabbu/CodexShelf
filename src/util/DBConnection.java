package util;

import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    private static final String URL = "";
    private static final String USER = "root";
    private static final String PASSWORD = "StrongPassword@13";
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
