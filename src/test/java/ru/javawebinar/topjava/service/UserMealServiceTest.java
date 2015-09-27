package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator populator;

    private final static LocalDate startDate = LocalDate.of(2015, Month.MAY, 29);
    private final static LocalDate endDate = LocalDate.of(2015, Month.MAY, 31);
    private final static LocalTime startTime = LocalTime.of(9, 0);
    private final static LocalTime endTime = LocalTime.of(11, 0);
    private final static LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
    private final static LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
    private final static LocalDateTime newDateTime = LocalDateTime.of(2015, Month.SEPTEMBER, 27, 19, 35);

    @Before
    public void setUp() {
        populator.execute();
    }

    @Test
    public void testGet() {
        UserMeal meal = service.get(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        service.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void testDelete() {
        service.delete(USER_MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test
    public void testGetBetweenDates() {
        Collection<UserMeal> meals = service.getBetweenDates(startDate, endDate, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL), meals);
    }

    @Test
    public void testGetBetweenDatesOutOfRange() {
        Collection<UserMeal> meals = service.getBetweenDates(startDate, endDate, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), meals);
    }

    @Test
    public void testGetBetweenDateTimes() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL), meals);
    }

    @Test
    public void testGetBetweenDateTimesOutOfRange() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), meals);
    }

    @Test
    public void testGetAll() {
        Collection<UserMeal> meals = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL), meals);
    }

    @Test
    public void testGetAllEmptyList() {
        Collection<UserMeal> meals = service.getAll(3);
        MATCHER.assertCollectionEquals(Collections.emptyList(), meals);
    }

    @Test
    public void testUpdate() {
        TestUserMeal updated = new TestUserMeal(ADMIN_MEAL);
        updated.setCalories(2500);
        updated.setDescription("Пицца");
        updated.setDateTime(Timestamp.valueOf(newDateTime));
        service.update(updated.asMeal(), ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        TestUserMeal updated = new TestUserMeal(ADMIN_MEAL);
        updated.setCalories(2500);
        updated.setDescription("Пицца");
        updated.setDateTime(Timestamp.valueOf(newDateTime));
        service.update(updated.asMeal(), USER_ID);
    }

    @Test
    public void testSave() {
        TestUserMeal saved = new TestUserMeal(newDateTime, "Курочка", 1300);
        UserMeal userMeal = service.save(saved, USER_ID);
        saved.setId(userMeal.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL, saved), service.getAll(USER_ID));
    }
}