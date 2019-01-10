package ua.nure.plotnykova.usermanagement.db;

import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactoryImpl;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class DaoFactory {

    private static final String DAO_FACTORY = "dao.factory";
    private static DaoFactory daoFactory;

    protected static Properties properties;

    static  {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException("setting.properties not found");
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (Objects.isNull(daoFactory)) {
            try {
                Class<?> factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                daoFactory = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return daoFactory;
    }

    public static void init(Properties prop) {
        properties = prop;
        daoFactory = null;
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public abstract UserDao getUserDao();
}
