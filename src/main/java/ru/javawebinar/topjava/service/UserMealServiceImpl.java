package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id, userId), id);
    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id, userId), id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.getAll(userId, startDateTime, endDateTime);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) throws NotFoundException {
        return ExceptionUtil.check(repository.save(userMeal, userId), userMeal.toString());
    }
}
