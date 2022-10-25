package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class UserDao {
    public void add() throws ClassNotFoundException, SQLException {
        Map<String, String> evn = System.getenv();
        String dbHost = evn.get("DB_HOST");
        String dbUser = evn.get("DB_USER");
        String dbPassword = evn.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(dbHost, dbUser, dbPassword);

    }

    public static void main(String[] args) {

    }
}
