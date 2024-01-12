package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private  final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {}
    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate( "CREATE TABLE  IF NOT EXISTS user " +
                    "(id BIGINT not NULL AUTO_INCREMENT,  name VARCHAR(100), " +
                    "lastName VARCHAR(100), age  SMALLINT,PRIMARY KEY(id))");
        } catch (SQLException e) {
            logger.info("An error occurred while creating the table" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("DB created successful by JDBC");
    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            logger.info("An error occurred while trying to drop" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("The table was successfully drop");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate( "INSERT INTO user(name, lastName, age) " +
                    "VALUES('" + name + "', '" + lastName + "', '" + age + "')");
        } catch (SQLException e) {
            logger.info("An error occurred while trying to save" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("The save was successful");
    }
    @Override
    public void removeUserById(long id) {
        try ( PreparedStatement preparedStatement = connection.prepareStatement( "DELETE FROM user WHERE id = ? ")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.info("An error occurred when trying to remove by id" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("The remove by id was successful");
    }
    @Override
    public List<User> getAllUsers() {
        List<User> addList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");
             ResultSet rs  = preparedStatement.executeQuery(); ){
            while (rs.next()){
                User user = new User( rs.getString("name"),rs.getString("lastName"),rs.getByte("age"));
                user.setId(rs.getLong("id"));
                addList.add(user);
            }
        } catch (SQLException e) {
            logger.info("An error occurred while trying to get all users" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("All users have been successfully get");
        return addList;
    }
    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            logger.info("An error occurred while trying to clean users table" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Clean users table is completed");
    }
}