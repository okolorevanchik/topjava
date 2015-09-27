package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 3;

    public static final UserMeal USER_MEAL = new UserMeal(USER_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final UserMeal ADMIN_MEAL = new UserMeal(ADMIN_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static class TestUserMeal extends UserMeal {

        public TestUserMeal(UserMeal um) {
            this(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories());
        }

        public TestUserMeal(LocalDateTime dateTime, String description, int calories) {
            this(null, dateTime, description, calories);
        }

        public TestUserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
            super(id, dateTime, description, calories);
        }

        public UserMeal asMeal() {
            return new UserMeal(this);
        }

        @Override
        public String toString() {
            return "UserMeal{" +
                    "dateTime=" + dateTime +
                    ", description='" + description + '\'' +
                    ", calories=" + calories +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestUserMeal that = (TestUserMeal) o;
            return Objects.equals(this.id, that.id)
                    && Objects.equals(this.dateTime, that.dateTime)
                    && Objects.equals(this.description, that.description)
                    && this.calories == that.calories;
        }
    }

}
