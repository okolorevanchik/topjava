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
import java.util.stream.Collectors;

public class UserMealsUtil {

    private static List<UserMealWithExceed> mealWithExceedList = new ArrayList<UserMealWithExceed>() {{
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 8, 0), "Завтрак", 500, false));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Ланч", 2500, false));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 14, 0), "Обед", 1500, false));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 17, 0), "Полдник", 3500, true));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 19, 0), "Ужин", 2500, true));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Чаёк", 1500, true));
        add(new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 1, 23, 0), "Ночной бутерброд", 4999, false));
    }};


    public static void addMeals(UserMealWithExceed userMealWithExceed) {
        mealWithExceedList.add(userMealWithExceed);
    }

    public static UserMealWithExceed getMeals(int id) {
        return mealWithExceedList.get(id);
    }

    public static void updateMeals(UserMealWithExceed userMealWithExceed, int id) {
        deleteMeals(id);
        mealWithExceedList.add(id, userMealWithExceed);
    }

    public static void deleteMeals(int id) {
        mealWithExceedList.remove(id);
    }

    public static List<UserMealWithExceed> getAllMeals() {
        return mealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um->TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um->new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesSumByDate.get(um.getDateTime().toLocalDate())> caloriesPerDay))
                .collect(Collectors.toList());
    }
}
