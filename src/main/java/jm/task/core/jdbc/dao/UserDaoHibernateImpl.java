package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final  String CREATE_TABLE_SQL = "CREATE TABLE  IF NOT EXISTS users " +
            "(id BIGINT not NULL AUTO_INCREMENT,  name VARCHAR(100), " +
            "lastName VARCHAR(100), age  SMALLINT,PRIMARY KEY(id))";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS user";
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
       try (Session session = Util.getSessionFactory().getCurrentSession()){
         transaction = session.beginTransaction();
           User user = new User();
         session.createNativeQuery(String.format(CREATE_TABLE_SQL, user.getClass().getSimpleName())).executeUpdate();
         transaction.commit();
       }catch (Exception e){
           transaction.rollback();
           e.printStackTrace();
       }
        System.out.println("DB created successful ");
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(String.format(DROP_TABLE, user.getClass().getSimpleName())).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
    }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = new User(name,lastName,age);
            session.save(user);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List <User> getList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            getList.addAll(session.createNativeQuery(String.format("SELECT * FROM user",
                    user.getClass().getSimpleName())).getResultList());
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
        return getList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction   transaction =null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            session.createNativeQuery(String.format("TRUNCATE TABLE user",
                    user.getClass().getSimpleName())).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }

    }
}
