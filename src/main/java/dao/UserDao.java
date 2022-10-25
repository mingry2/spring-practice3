package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) {
        try {
            Connection c = connectionMaker.makeConnection();

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

    public User getById(String id){

        try {
            Connection c = connectionMaker.makeConnection();

            PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = null;
            if(rs.next()) {
                user = new User(rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("password"));
            }
            rs.close();
            ps.close();
            c.close();

            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao(new AWSConnectionMaker());
//        userDao.add();
//        User user = userDao.getById("1");
//        System.out.println(user.getName());

    }
}
