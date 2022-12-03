package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Users(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(30),lastName VARCHAR(30),age INT);";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS Users;";
    private static final String SQL_ADD_USER = "INSERT INTO Users (name,lastName,age) VALUES (?,?,?);";
    private static final String SQL_DELETE_USER = "DELETE FROM Users WHERE id = ?;";
    private static final String SQL_GET_USERS = "select * from Users;";
    private static final String SQL_DELETE_ALL = "DELETE FROM Users;";
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(SQL_ADD_USER)) {
            connection.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);
            ps.setString(1,name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);

        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_GET_USERS)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_DELETE_ALL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
