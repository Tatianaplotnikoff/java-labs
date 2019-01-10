package ua.nure.plotnykova.usermanagement.db;

import ua.nure.plotnykova.usermanagement.db.factory.ConnectionFactory;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import java.util.*;

public class MockUserDao implements UserDao {

    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(long userId) throws DatabaseException {
        users.remove(userId);
    }

    @Override
    public Optional<User> find(long userId) throws DatabaseException {
        return Optional.of(users.get(userId));
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return new ArrayList<>(users.values());
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return null;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
