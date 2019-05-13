package bsu.radchuk.task.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet(urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part file = req.getPart("file");
        Path path = Paths.get(getServletContext().getRealPath("/"), "/resources", file.getSubmittedFileName());
        Files.copy(file.getInputStream(), path);
        resp.getOutputStream().print("OK, bytes wrote: " + file.getInputStream().available());
    }
}
