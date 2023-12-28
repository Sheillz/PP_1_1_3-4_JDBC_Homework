package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {}
    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate( "CREATE TABLE  IF NOT EXISTS user " +
                    "(id BIGINT not NULL AUTO_INCREMENT,  name VARCHAR(100), " +
                    "lastName VARCHAR(100), age  SMALLINT,PRIMARY KEY(id))");
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the table" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("DB created successful by JDBC");
    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.out.println("An error occurred while trying to drop" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("The table was successfully drop");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate( "INSERT INTO user(name, lastName, age) " +
                    "VALUES('" + name + "', '" + lastName + "', '" + age + "')");
        } catch (SQLException e) {
            System.out.println("An error occurred while trying to save" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("The save was successful");
    }
    @Override
    public void removeUserById(long id) {
        try ( PreparedStatement preparedStatement = connection.prepareStatement( "DELETE FROM user WHERE id = ? ")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred when trying to remove by id" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("The remove by id was successful");
    }
    @Override
    public List<User> getAllUsers() {
        List<User> addList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs  = statement.executeQuery("SELECT * FROM user");
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                addList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while trying to get all users" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("All users have been successfully get");
        return addList;
    }
    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            System.out.println("An error occurred while trying to clean users table" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Clean users table is completed");
    }
}