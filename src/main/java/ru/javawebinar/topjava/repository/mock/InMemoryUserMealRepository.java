package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 17, 10, 0), "Завтрак", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 17, 13, 0), "Обед", 1000), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 17, 20, 0), "Ужин", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 17, 23, 0), "Ужин#2", 500), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 18, 10, 0), "Завтрак", 300), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 18, 13, 0), "Обед", 800), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 18, 20, 0), "Ужин", 300), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 18, 23, 0), "Ужин#2", 300), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 10, 0), "Завтрак", 200), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 13, 0), "Обед", 700), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 20, 0), "Ужин", 200), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 23, 0), "Ужин#2", 100), 1);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 10, 0), "Завтрак", 1000), 2);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 13, 0), "Обед", 500), 2);
        save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 20, 0), "Ужин", 510), 3);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        LOG.info("save " + userMeal);

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            userMeal.setUserId(userId);
        } else {
            if (get(userMeal.getId(), userId) == null)
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
    public Collection<UserMeal> getAll(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LOG.info("getAll");
        return repository.values().stream()
                .filter(userMeal -> userMeal.getUserId() == userId)
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }
}

