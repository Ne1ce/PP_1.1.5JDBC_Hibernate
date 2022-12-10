package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Lena","Petrova", (byte) 20);
        us.saveUser("Ivan","Sidorov", (byte) 23);
        us.saveUser("Artem","Vasin", (byte) 33);
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
