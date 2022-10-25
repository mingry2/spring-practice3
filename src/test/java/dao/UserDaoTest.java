package dao;

import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addAndGet(){
        UserDao userDao = new UserDao();

        String id = "4";

        userDao.add(new User(id, "DDD", "4444"));
        User user = userDao.getById(id);

        assertEquals("DDD", user.getName());

    }

}