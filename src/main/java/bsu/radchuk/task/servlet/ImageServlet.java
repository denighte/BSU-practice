package bsu.radchuk.task.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String imgPath = request.getParameter("path");
        response.setContentType("image/png");
        if (imgPath == null) {
            response.setStatus(400);
            return;
        }
        //TODO: NTFS FILESYSTEM fix (and comment the first line after commented).
//        String rootPath = URLDecoder.decode(getServletContext().getContextPath(), "UTF-8");
//        if (rootPath.startsWith("/")) {
//            rootPath = rootPath.substring(1);
//        }
//        InputStream img = Files.newInputStream(Paths.get(rootPath + imgPath));

        InputStream img = getServletContext().getResourceAsStream("/" + imgPath);
        OutputStream output = response.getOutputStream();
        if (img == null) {
            response.setStatus(503);
            return;
        }
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(img);
                WritableByteChannel outputChannel = Channels.newChannel(output);
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            long size = 0;
            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }
        }
    }
}
