package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepository implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final LoggerWrapper LOG = LoggerWrapper.get(InMemoryUserMealRepository.class);

    {
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), 1);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        LOG.info("save " + userMeal);

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            userMeal.setUserId(userId);
        } else {
            if (userMeal.getUserId() != userId)
                return null;
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete " + id);

        UserMeal userMeal = get(id, userId);
        if (userMeal == null)
            return false;

        repository.remove(id);
        return true;
    }

    @Override
    public UserMeal get(int id, int userId) {
        LOG.info("get" + id);

        UserMeal userMeal = repository.get(id);

        if (userMeal.getUserId() == userId)
            return userMeal;

        return null;
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        LOG.info("getAll");

        Collection<UserMeal> collection = repository.values();
        boolean isContains = collection.stream()
                .allMatch(userMeal -> userMeal.getUserId() == userId);

        if (isContains)
            return collection;

        return Collections.emptyList();
    }
}

