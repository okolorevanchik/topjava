package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.ApplicationContextProvider;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private UserMealRestController restController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = ApplicationContextProvider.getAppCtx();
        restController = appCtx.getBean(UserMealRestController.class);
        ApplicationContextProvider.closeAppCtx();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")), null);
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        restController.save(userMeal, LoggedUser.id());
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = LoggedUser.id();

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", UserMealsUtil.getWithExceeded(
                    restController.getAll(userId, LocalDateTime.MIN, LocalDateTime.MAX), 2000));
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        } else if (action.equals("filter")) {
            LocalDateTime startDateTime = getDateTime(request, "startDate", "startTime", LocalDateTime.MIN);
            LocalDateTime endDateTime = getDateTime(request, "endDate", "endTime", LocalDateTime.MAX);
            request.setAttribute("mealList",
                    UserMealsUtil.getWithExceeded(restController.getAll(userId, startDateTime, endDateTime), 2000));
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            restController.delete(id, userId);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    restController.get(getId(request), userId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private LocalDateTime getDateTime(HttpServletRequest request,
                                      String dateParameter, String timeParameter,
                                      LocalDateTime nominal) {
        String date = request.getParameter(dateParameter);
        String time = request.getParameter(timeParameter);

        LocalDate localDate = (date.isEmpty()) ? nominal.toLocalDate() : LocalDate.parse(date);
        LocalTime localTime = (time.isEmpty()) ? nominal.toLocalTime() : LocalTime.parse(time);

        return LocalDateTime.of(localDate, localTime);
    }
}
