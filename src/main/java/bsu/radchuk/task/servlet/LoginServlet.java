package bsu.radchuk.task.servlet;

import bsu.radchuk.task.model.Message;
import bsu.radchuk.task.model.User;
import bsu.radchuk.task.service.LoginService;
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
            io.write(Message.builder()
                                .status(400)
                                .data("Invalid parameters.")
                                .build());
            return;
        }
        User user = null;
        LoginService loginService = new LoginService();
        try {
            user = loginService.loginUser(login, password);
        } catch (ServiceException exception) {
            io.write(Message.builder()
                    .status(503)
                    .data("Service is unavailable, please try again later.")
                    .build());
            return;
        }
        if (user == null) {
            response.setStatus(200);
            io.write(Message.builder()
                    .status(200)
                    .data("Invalid login or password.")
                    .build());
            return;
        }
        request.getSession().setAttribute("user", user.getId());
        response.setStatus(200);
        io.write(Message.builder()
                            .status(200)
                            .data("Successfully logged in.")
                            .build());
    }
}
