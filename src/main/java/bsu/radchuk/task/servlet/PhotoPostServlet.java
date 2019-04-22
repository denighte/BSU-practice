package bsu.radchuk.task.servlet;

import bsu.radchuk.task.dao.DaoException;
import bsu.radchuk.task.dao.PhotoPostDao;
import bsu.radchuk.task.io.RestIO;
import bsu.radchuk.task.model.PhotoPost;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Enumeration;

@MultipartConfig
@WebServlet(urlPatterns = "/post")
public class PhotoPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        RestIO io = new RestIO(response);
        String requestedId = request.getParameter("id");
        int id = 0;
        if (requestedId == null) {
            io.info(400, "Invalid parameters");
            return;
        }
        try {
            id = Integer.parseInt(requestedId);
        } catch (NumberFormatException exception) {
            io.info(400, "Invalid parameters");
            return;
        }

        PhotoPost post = null;
        try (PhotoPostDao dao = new PhotoPostDao()) {
            post = dao.find(id);
        } catch (DaoException exception) {
            io.info(503,
                    "Service is unavailable, please try again later.");
            return;
        }

        if (post == null) {
            io.info(200, "Error: no such photo post.");
            return;
        }

        //if http/2.0 is enabled photo of the post could be loaded in that way.
        //request.newPushBuilder().path(post.getSrc()).push();

        io.write(post);

    }

    //upload post example:
    //{"description":"Some description", "author":"some author", "date":"2001-02-03"}
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        RestIO io = new RestIO(response);
        Part file = request.getPart("file");
        String postToUpload = request.getParameter("post");
        if (postToUpload == null) {
            io.info(400, "Invalid parameters");
            return;
        }
        PhotoPost post = io.read(postToUpload, PhotoPost.class);
        if (post == null
         || post.getAuthor() == null
         || post.getDate() == null) {
            io.info(400, "Invalid parameters");
            return;
        }
        //NOTE: date should be in JDBC format (yyyy-[m]m-[d]d)
        try {
            Date.valueOf(post.getDate());
        } catch (IllegalArgumentException exception) {
            io.info(400, "Invalid parameters");
            return;
        }

        String filename = file.getSubmittedFileName();
        String rootUrl = getServletContext().getResource("/").getPath();
        String rootPath = URLDecoder.decode(rootUrl, "UTF-8");
        //TODO: NTFS FILESYSTEM fix
//        if (rootPath.startsWith("/")) {
//            rootPath = rootPath.substring(1);
//        }
        //TODO: add unique filename creation.
        String relativeFilePath = "img/" + filename;
        String absoluteFilePath = rootPath + relativeFilePath;
        post.setSrc(relativeFilePath);
        try {
            Files.copy(file.getInputStream(), Paths.get(absoluteFilePath));
        } catch (IOException exception) {
            io.info(503, "Couldn't save the file.");
            return;
        }
        int id = 0;
        try (PhotoPostDao dao = new PhotoPostDao()) {
            id = dao.insert(post);
        } catch (DaoException exception) {
            io.info(503, "Service is unavailable.");
            return;
        }
        response.getWriter().write("{\"id\":" + id + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {
        RestIO io = new RestIO(response);
        String requestedId = request.getParameter("id");
        int id = 0;
        if (requestedId == null) {
            response.setStatus(400);
            io.info(400, "Invalid parameters");
            return;
        }
        try {
            id = Integer.parseInt(requestedId);
        } catch (NumberFormatException exception) {
            io.info(400, "Invalid parameters");
            return;
        }
        int result = 0;
        //TODO: add transaction mode for dao.
        try (PhotoPostDao dao = new PhotoPostDao()) {
            PhotoPost post = dao.find(id);
            String rootUrl = getServletContext().getResource("/").getPath();
            String rootPath = URLDecoder.decode(rootUrl, "UTF-8");
            //TODO: NTFS FILESYSTEM fix
//          if (rootPath.startsWith("/")) {
//              rootPath = rootPath.substring(1);
//          }
            try {
                Files.delete(Paths.get(rootPath + post.getSrc()));
            } catch (IOException | NullPointerException exception) {
                io.info(200, "Failed to delete post.");
                return;
            }

            result = dao.delete(id);
        } catch (DaoException exception) {
            io.info(503, "Service is unavailable.");
            return;
        }

        if (result == 0) {
            io.info(200, "Failed to delete post.");
            return;
        }
        io.info(200, "Photo post successfully deleted.");
    }
}
