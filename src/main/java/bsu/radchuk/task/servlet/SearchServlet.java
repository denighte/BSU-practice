package bsu.radchuk.task.servlet;

import bsu.radchuk.task.dao.DaoException;
import bsu.radchuk.task.dao.PhotoPostDao;
import bsu.radchuk.task.io.RestIO;
import bsu.radchuk.task.model.PhotoPost;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        RestIO io = new RestIO(response);
        String data = request.getParameter("search");
        String type = request.getParameter("type");
        if (data == null || type == null) {
            io.info(400, "Invalid parameters");
            return;
        }

        int number = 0;
        if (type.equals("last")) {
            try {
                number = Integer.parseInt(data);
            } catch (NumberFormatException exception) {
                io.info(400, "Invalid parameters");
                return;
            }
        }

        if (type.equals("date")) {
            try {
                Date.valueOf(data);
            } catch (IllegalArgumentException exception) {
                io.info(400, "Invalid parameters");
                return;
            }
        }

        List<PhotoPost> posts = Collections.emptyList();
        try(PhotoPostDao dao = new PhotoPostDao()) {
            switch (type) {
                case "last":
                    posts = dao.findLast(number);
                    break;
                case "author":
                    posts = dao.findByAuthor(data);
                    break;
                case "date":
                    posts = dao.findByDate(data);
                    break;
                case "hashtag":
                    posts = dao.findByHashtag("%#" + data + "%");
                    break;
            }
        } catch (DaoException exception) {
            io.info(503,
                    "Service is unavailable, please try again later.");
            return;
        }
        io.write(posts);

    }
}
