package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;

public class UserMeal {

    protected Long id;

    protected LocalDateTime dateTime;

    protected String description;

    protected int calories;

    public UserMeal(Long id, LocalDateTime dateTime, String description, int calories) {
        if (id == null) this.id = UserMealsUtil.generateId();
        else this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

}
