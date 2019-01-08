package ua.nure.plotnykova.usermanagement.db;

import junit.framework.TestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactoryImpl;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private User user;
    private UserDao dao;
    private ConnectionFactory connectionFactory;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader()
                .getResourceAsStream("usersDataSet.xml"));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao(connectionFactory);
        user = constructUser();

    }

    public void testCreateUser() {
        assertNull(user.getId());
        try {
            user = dao.create(user).get();
        } catch (DatabaseException e) {
            fail(e.toString());
        }
        assertNotNull(user.getId());
    }

    public void testFindAll() {
        try {
            List<User> users = dao.findAll();
            assertNotNull("Collection is null", users);
            assertEquals("Collection size.", 2, users.size());
        } catch (DatabaseException e) {
            Assert.fail(e.toString());
        }


    }

    private static User constructUser() throws ParseException {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Sokolov");
        user.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse("18.12.1995"));
        return user;
    }
}