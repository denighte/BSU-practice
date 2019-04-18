package bsu.radchuk.task.servlet;

import bsu.radchuk.task.model.Message;
import bsu.radchuk.task.model.User;
import bsu.radchuk.task.service.LoginService;
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
            throws ServletException, IOException {
        RestIO io = new RestIO(response);
        User user = io.read(request.getParameter("User"), User.class);
        if (user == null) {
            response.setStatus(400);
            io.write(Message.builder()
                    .status(400)
                    .data("Invalid parameters.")
                    .build());
            return;
        }
        LoginService loginService = new LoginService();
        try {
            loginService.registerUser(user);
        } catch (ServiceException exception) {
            io.write(Message.builder()
                    .status(503)
                    .data("Service is unavailable, please try again later.")
                    .build());
        }
        request.getSession().setAttribute("user", user.getId());
    }
}
