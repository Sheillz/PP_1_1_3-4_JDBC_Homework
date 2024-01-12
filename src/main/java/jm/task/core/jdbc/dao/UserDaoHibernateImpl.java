package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
   private final   SessionFactory sessionFactory = Util.getSessionFactory();
    private final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {
        // empty for task
    }
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id INTEGER NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255),age INTEGER, PRIMARY KEY(id))").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred while creating the table" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("DB created successful by JDBC");
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred while trying to drop" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("The table was successfully drop");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            session.beginTransaction();
            User user = new User(name, lastName,age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred while trying to save" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("The save was successful");
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred when trying to remove by id" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("The remove by id was successful");
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> getList = new ArrayList<>();
        try (session) {
            session.beginTransaction();
            getList = session.createQuery(" FROM User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred while trying to get all users" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("All users have been successfully get");
        return getList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.info("An error occurred while trying to clean users table" + e.getMessage());
            session.getTransaction().rollback();
        }
        logger.info("Clean users table is completed");
    }
}

