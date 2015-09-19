package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println("\n" + adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));

            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            System.out.println("\nactual userMeals.toString(): " + userMealRestController
                    .save(new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 19, 0, 9), "Завтрак", 500)));
            System.out.println("\nactual true: " + userMealRestController.delete(1, 1));
            System.out.println("\nactual false: " + userMealRestController.delete(2, 2));
            System.out.println("\nactual userMeals.toString(): " + userMealRestController.get(2, 1));
            System.out.println("\nactual null: " + userMealRestController.get(2, 2));
            System.out.println("\nactual collection of objects UserMeals: " + userMealRestController.getAll(1));
            System.out.println("\nactual empty collection: " + userMealRestController.getAll(2));
            System.out.println("\nactual true: " + userMealRestController
                    .update(new UserMeal(2, LocalDateTime.of(2015, Month.SEPTEMBER, 19, 0, 9), "Едааа", 1500, 1)));
            System.out.println("\nactual false: " + userMealRestController
                    .update(new UserMeal(2, LocalDateTime.of(2015, Month.SEPTEMBER, 19, 0, 9), "Едааа", 1500, 2)));
        }
    }
}
