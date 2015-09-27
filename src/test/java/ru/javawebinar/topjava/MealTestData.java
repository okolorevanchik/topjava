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

    public static final int MEAL_ID = START_SEQ + 2;

    public static final UserMeal um1 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final UserMeal um2 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final UserMeal um3 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final UserMeal um4 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final UserMeal um5 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final UserMeal um6 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final UserMeal aum1 = new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final UserMeal aum2 = new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин ", 510);

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
