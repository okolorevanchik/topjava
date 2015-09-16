package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("flag_editor", false);

        String action = req.getParameter("action");
        if (action != null) {
            String id = req.getParameter("id");
            Long longId = Long.parseLong(id);
            switch (action) {
                case "delete":
                    UserMealsUtil.deleteMeals(longId);
                    break;
                case "edit":
                    req.setAttribute("flag_editor", true);
                    UserMeal userMeal = UserMealsUtil.getMeals(longId);
                    req.setAttribute("userMeal", userMeal);
                    break;
                default: break;
            }
        }

        listToRequestDispatcher(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("flag_editor", false);

        String action = req.getParameter("action");
        if (action != null) {
            UserMealsUtil.saveOrUpdate(getPickedObject(req));
        }

        listToRequestDispatcher(req, resp);
    }


    private void listToRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserMeal> userMeals = UserMealsUtil.getAllMeals();
        List<UserMealWithExceed> userMealWithExceeds = UserMealsUtil
                .getFilteredMealsWithExceeded(userMeals, LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("userMealWithExceeds", userMealWithExceeds);
        getServletConfig().getServletContext().getRequestDispatcher("/mealList.jsp").forward(req, resp);
    }

    private UserMeal getPickedObject(HttpServletRequest req) {
        String id = req.getParameter("id");
        Long longId = id.isEmpty() ? null : Long.parseLong(id);
        LocalDateTime time = LocalDateTime.parse(req.getParameter("local_date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        return new UserMeal(longId, time, description, calories);
    }
}
