package ua.nure.plotnykova.usermanagement.db;

import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.xml.crypto.Data;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Hsqldb implementation of {@link UserDao}
 */
public class HsqldbUserDao implements UserDao {

    private static final String CREATE_USER = "INSERT INTO users (first_name, last_name, date_of_birth) VALUES (?,?,?)";
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth FROM users";
    private static final String GET_USER_BY_ID = "SELECT id, first_name, last_name, date_of_birth FROM users WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, date_of_birth = ? WHERE id = ?";

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keys = null;
        CallableStatement callableStatement = null;
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int rowAffected = statement.executeUpdate();
            if (rowAffected != 1) {
                throw new DatabaseException("Number of the inserted rows: " + rowAffected);
            }
            callableStatement = connection.prepareCall("call IDENTITY()");
            keys = callableStatement.executeQuery();

            if (keys.next()) {
                user.setId(keys.getLong(1));
            }

            return user;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot create new user.");
        } finally {
            closeResultSet(keys);
            closeStatement(statement);
            closeStatement(callableStatement);
            closeConnection(connection);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Cannot update user");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    @Override
    public void delete(long userId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Cannot delete specified user.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    @Override
    public Optional<User> find(long userId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Optional<User> user = Optional.empty();
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = Optional.of(parseUser(resultSet));
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot obtain user");
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        List<User> users = new LinkedList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(GET_ALL_USERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(parseUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Cannot obtain users");
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(connection);
        }
        return users;
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setFirstName(resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
        user.setDateOfBirth(resultSet.getDate(4));
        return user;
    }


}
