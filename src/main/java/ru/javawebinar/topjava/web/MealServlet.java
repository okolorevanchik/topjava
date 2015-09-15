package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        req.setCharacterEncoding("UTF-8");

        String delNumber = req.getParameter("del_number");
        if (delNumber != null) {
            req.setAttribute("flag_editor", false);
            UserMealsUtil.deleteMeals(Integer.parseInt(delNumber));
        }

        String editNumber = req.getParameter("edit_number");
        if (editNumber != null) {
            req.setAttribute("flag_editor", true);
            int id = Integer.parseInt(editNumber);
            UserMealWithExceed meals = UserMealsUtil.getMeals(id);
            req.setAttribute("date", meals.getDateTime());
            req.setAttribute("description", meals.getDescription());
            req.setAttribute("calories", meals.getCalories());
            req.setAttribute("exceed", meals.isExceed());
            req.setAttribute("number", id);
        }

        listToRequestDispatcher(req, resp);
    }

    private void listToRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserMealWithExceed> list = UserMealsUtil.getAllMeals();
        req.setAttribute("list", list);
        getServletConfig().getServletContext().getRequestDispatcher("/mealList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        req.setCharacterEncoding("UTF-8");

        req.setAttribute("flag_editor", false);
        String newMeans = req.getParameter("new");
        if (newMeans != null) {
            boolean newMeansFlag = Boolean.parseBoolean(newMeans);
            if (newMeansFlag) {
                LocalDateTime currentDate = LocalDateTime.parse(req.getParameter("local_date_new"));
                String descriptionNew = req.getParameter("description_new");
                int caloriesNew = Integer.parseInt(req.getParameter("calories_new"));
                String stringExceedNew = req.getParameter("exceed_new");
                boolean exceedNew = false;
                if (stringExceedNew != null && stringExceedNew.equals("on")) {
                    exceedNew = true;
                }
                UserMealsUtil.addMeals(new UserMealWithExceed(currentDate, descriptionNew, caloriesNew, exceedNew));
            }
        }

        String number = req.getParameter("number");
        if (number != null) {
            int numberInt = Integer.parseInt(number);
            LocalDateTime currentDate = LocalDateTime.parse(req.getParameter("local_date"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            String stringExceed = req.getParameter("exceed");
            boolean exceed = false;
            if (stringExceed != null && stringExceed.equals("on")) {
                exceed = true;
            }
            UserMealsUtil.updateMeals(new UserMealWithExceed(currentDate, description, calories, exceed), numberInt);
        }


        listToRequestDispatcher(req, resp);
    }
}
