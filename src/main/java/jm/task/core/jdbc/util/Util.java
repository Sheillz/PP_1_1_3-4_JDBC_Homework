package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class Util {
    private Util(){}
        public static Connection getConnection(){
            Logger logger = Logger.getLogger(Util.class.getName());
            Connection connection;
            Properties properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/jdbci.properties")){
                properties.load(fileInputStream);
                String URL = properties.getProperty("URL");
                String NANE = properties.getProperty("NANE");
                String PASSWORD = properties.getProperty("PASSWORD");
                connection = DriverManager.getConnection(URL,NANE,PASSWORD);
            } catch (IOException  | SQLException e ){
                throw new RuntimeException(e);
            }
            return connection;
        }
   private static SessionFactory sessionFactory;
    public  static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER,"com.mysql.jdbc.Driver");
                properties.put(Environment.URL,"jdbc:mysql://localhost:3306/mydbtest");
                properties.put(Environment.USER,"root");
                properties.put(Environment.PASS,"777514");
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




