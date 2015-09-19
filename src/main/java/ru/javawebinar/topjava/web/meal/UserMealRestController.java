package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    public UserMeal save(UserMeal userMeal) {
        return service.save(userMeal, LoggedUser.id());
    }

    public boolean delete(int id, int userId) {
        try {
            service.delete(id, userId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public UserMeal get(int id, int userId) {
        try {
            return service.get(id, userId);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public Collection<UserMeal> getAll(int userId) {
        return service.getAll(userId);
    }

    public boolean update(UserMeal userMeal) {
        try {
            service.update(userMeal, LoggedUser.id());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
