package dao;

import domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private Connection makeConnection() throws SQLException {
        Map<String, String> evn = System.getenv();
            Connection c = DriverManager.getConnection(
                    evn.get("DB_HOST"),
                    evn.get("DB_USER"),
                    evn.get("DB_PASSWORD"));
            return c;

    }
    public void add(User user){
        try {
            Connection c = makeConnection();

            PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?, ?, ?);");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            int status = ps.executeUpdate();
            ps.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getById(String id) {

        try {
            Connection c = makeConnection();

            PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            rs.close();
            ps.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.getById("1");
        System.out.println(user.getName());

    }
}
