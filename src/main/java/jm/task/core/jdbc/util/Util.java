package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class Util {
        private static final String url = "jdbc:mysql://localhost:3306/mydbtest";
        private static final String name = "root";
        private static final String password = "777514";
        private static  Connection connection;
        private static SessionFactory sessionFactory;

        public static Connection getConnection()  {
            if (connection== null) {
                try {
                    connection = DriverManager.getConnection(url, name, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return connection;
        }
        public  static SessionFactory getSessionFactory(){
            if (sessionFactory == null){
                try {
                    Configuration configuration = new Configuration();
                    Properties properties = new Properties();
                    properties.put(Environment.DRIVER,"com.mysql.jdbc.Driver");
                    properties.put(Environment.URL,url);
                    properties.put(Environment.USER,name);
                    properties.put(Environment.PASS,password);
                    properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
                    properties.put(Environment.SHOW_SQL,"true");
                    properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                    properties.put(Environment.HBM2DDL_AUTO,"create-drop");
                    configuration.setProperties(properties);
                    configuration.addAnnotatedClass(User.class);
                    ServiceRegistry serviceRegistry =  new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return  sessionFactory; }

    }




