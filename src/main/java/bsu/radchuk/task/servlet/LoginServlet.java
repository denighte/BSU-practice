package bsu.radchuk.task.servlet;

import bsu.radchuk.task.model.Message;
import bsu.radchuk.task.model.User;
import bsu.radchuk.task.service.UserService;
import bsu.radchuk.task.service.ServiceException;
import bsu.radchuk.task.io.RestIO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {
        RestIO io = new RestIO(response);
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || password == null) {
            response.setStatus(400);
            io.info(400, "Invalid parameters.");
            return;
        }
        User user = null;
        UserService loginService = new UserService();
        try {
            user = loginService.loginUser(login, password);
        } catch (ServiceException exception) {
            io.info(503, "Service is unavailable.");
            return;
        }
        if (user == null) {
            response.setStatus(200);
            io.info(200, "Invalid login or password.");
            return;
        }
        request.getSession().setAttribute("user", user.getId());
        response.setStatus(200);
        io.info(200, "Successfully logged in.");
    }
}
