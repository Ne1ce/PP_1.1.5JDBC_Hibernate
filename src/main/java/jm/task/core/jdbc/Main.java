package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Lena","Petrova", (byte) 20);
        System.out.println("User с именем Lena добавлен в базу данных");
        us.saveUser("Ivan","Sidorov", (byte) 23);
        System.out.println("User с именем Ivan добавлен в базу данных");
        us.saveUser("Artem","Vasin", (byte) 33);
        System.out.println("User с именем Artem добавлен в базу данных");
        us.cleanUsersTable();
        us.dropUsersTable();


    }
}
