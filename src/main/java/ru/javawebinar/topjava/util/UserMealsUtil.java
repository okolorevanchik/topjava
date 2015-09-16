package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserMealsUtil {

    private static AtomicLong aLong = new AtomicLong(0);

    private static List<UserMeal> mealList = new ArrayList<UserMeal>() {{
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }};

    public static long generateId() {
        return aLong.incrementAndGet();
    }

    public static UserMeal getMeals(long id) {
        return mealList.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst().get();
    }

    public static void saveOrUpdate(UserMeal userMeal) {
        Long id = userMeal.getId();
        deleteMeals(id);
        mealList.add(userMeal);
    }

    public static void deleteMeals(long id) {
        UserMeal userMealFomList = getMeals(id);
        if (userMealFomList != null)
            mealList.remove(userMealFomList);
    }

    public static List<UserMeal> getAllMeals() {
        return mealList;
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
