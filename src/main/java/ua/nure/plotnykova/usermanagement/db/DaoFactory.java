package ua.nure.plotnykova.usermanagement.db;

import sun.dc.pr.PRError;
import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactoryImpl;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DaoFactory {

    private final Properties properties;
    private static DaoFactory daoFactory;

    public static synchronized DaoFactory getInstance() {
        if(Objects.isNull(daoFactory)) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    private DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException("setting.properties not found");
        }
    }


    private ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty("connection.driver");
        String url = properties.getProperty("connection.url");
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    public UserDao getUserDao() {
        UserDao userDao = null;
        Class userDaoClass = null;
        try {
            userDaoClass = Class.forName(properties.getProperty("dao.knure.ctde.usermanagement.db.UserDao"))
            userDao = (UserDao) userDaoClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Class user dao not found");
        }
        userDao.setConnectionFactory(getConnectionFactory());
        return userDao;
    }
}
