package ua.nure.plotnykova.usermanagement.db;

import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Works with database.
 */
public interface UserDao {
    /**
     * Create new user.
     *
     * @param user user which should be insert.
     * @return created user.
     * @throws DatabaseException database exception
     */
    Optional<User> create(User user) throws DatabaseException;

    /**
     * Update existing user.
     *
     * @param user data for updating user
     * @throws DatabaseException database exception
     */
    void update(User user) throws DatabaseException;

    /**
     * Delete user.
     *
     * @param userId user's id that should be deleting
     * @throws DatabaseException database exception
     */
    void delete(long userId) throws DatabaseException;

    /**
     * Obtain user by id,
     *
     * @param userId user's id which should be finding.
     * @return user.
     * @throws DatabaseException database exception
     */
    Optional<User> find(long userId) throws DatabaseException;

    /**
     * Obtain user list.
     *
     * @return all users.d
     * @throws DatabaseException database exception
     */
    List<User> findAll() throws DatabaseException;

    /**
     * Return connection factory.
     *
     * @return connection factory
     */
    ConnectionFactory getConnectionFactory();

    /**
     * Set connection factory.
     *
     * @param connectionFactory connection factory
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);

    default void closeResultSet(ResultSet resultSet) {
        if (Objects.nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot close resultSet.");
            }
        }
    }


    default void closeStatement(Statement statement) {
        if (Objects.nonNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot close statement.");
            }
        }
    }

    default void closeConnection(Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot close connection.");
            }
        }
    }
}
