package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.ApplicationContextProvider;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);

    private AdminRestController restController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = ApplicationContextProvider.getAppCtx();
        restController = appCtx.getBean(AdminRestController.class);
        ApplicationContextProvider.closeAppCtx();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to userList");
        request.setCharacterEncoding("UTF-8");

        List<User> users = restController.getAll();
        request.setAttribute("users", users);

        request.setAttribute("userId", LoggedUser.id());
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            int userId = Integer.parseInt(id);
            LoggedUser.setId(userId);
        }
        response.sendRedirect("users");
    }
}
