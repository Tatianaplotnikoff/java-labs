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
import java.util.Optional;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private User user;
    private UserDao dao;
    private ConnectionFactory connectionFactory;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement", "sa", "");
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

    public void testFindById() {
        try {
            Optional<User> user = dao.find(1000L);
            assertTrue("User is null",user.isPresent());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testDeleteUser() {
        try {
            long userId = 1000L;
            dao.delete(userId);
            Optional<User> userOptional = dao.find(userId);
            assertFalse("User is exists", userOptional.isPresent());

        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testUpdateUser() {
        try {
            long userId = 1000;
            String expectedName = "Harry";
            user.setFirstName(expectedName);
            user.setId(userId);
            dao.update(user);
            Optional<User> userOptional = dao.find(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                assertEquals("Name is not equals", expectedName, user.getFirstName());
            }
        } catch (DatabaseException e) {
            fail(e.toString());
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