package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class MockUserRepositoryImpl implements UserRepository {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MockUserRepositoryImpl.class);

    private List<User> users = new CopyOnWriteArrayList<>();

    {
        users.add(new User(1, "user1", "user1@gmail.com", "12345", Role.ROLE_USER));
        users.add(new User(2, "user2", "user2@gmail.com", "54321", Role.ROLE_USER));
        users.add(new User(3, "admin1", "admin@gmail.com", "zYtYfcnjkmrjUkeg", Role.ROLE_USER, Role.ROLE_ADMIN));
        users.add(new User(4, "user3", "user3@gmail.com", "123456", Role.ROLE_USER));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return null;
    }
}
