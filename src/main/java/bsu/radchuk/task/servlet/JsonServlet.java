package bsu.radchuk.task.servlet;

import bsu.radchuk.task.model.Message;
import com.google.gson.Gson;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(urlPatterns = {"/check"})
public class JsonServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        var message = Message.builder().status(SC_OK).data("success").build();
        resp.setStatus(SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(message));
    }
}
