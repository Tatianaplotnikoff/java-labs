package ua.nure.plotnykova.usermanagement.db;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoFactoryTest extends TestCase {

    public void testGetUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            UserDao userDao = daoFactory.getUserDao();
            assertNotNull("UserDao instance is null", userDao);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }
}