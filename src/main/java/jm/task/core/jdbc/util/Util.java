package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private Util(){}
    private static  SessionFactory sessionFactory;
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

    public static SessionFactory getSessionFactory() {
        Logger logger = Logger.getLogger(Util.class.getName());
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
                logger.info("SessionFactory connection correct!");
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("SessionFactory failed!");
            }
        }
        return sessionFactory;
    }

}




