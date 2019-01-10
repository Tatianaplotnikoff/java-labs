package ua.nure.plotnykova.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        UserDao userDao = null;
        try {
            Class userDaoClass = Class.forName(properties.getProperty("dao.ua.nure.usermanagement.db.UserDao"));
            userDao = (UserDao) userDaoClass.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException("Class user dao not found");
        }
        return userDao;

    }
}
