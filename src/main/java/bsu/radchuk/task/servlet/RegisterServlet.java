package bsu.radchuk.task.servlet;

import bsu.radchuk.task.model.Message;
import bsu.radchuk.task.model.User;
import bsu.radchuk.task.service.UserService;
import bsu.radchuk.task.service.ServiceException;
import bsu.radchuk.task.io.RestIO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        RestIO io = new RestIO(response);
        String strUser = request.getParameter("user");
        if (strUser == null) {
            io.info(400, "Invalid parameters");
            return;
        }
        User user = io.read(strUser, User.class);
        if (user == null
           || user.getPasswordHash() == null
           || user.getLogin() == null)
        {
            io.info(400, "Invalid parameters.");
            return;
        }
        UserService loginService = new UserService();
        try {
            loginService.registerUser(user);
        } catch (ServiceException exception) {
            io.info(503, "Service is unavailable.");
        }
        io.info(200, "Successfully registered.");
        request.getSession().setAttribute("user", user.getId());
    }
}
