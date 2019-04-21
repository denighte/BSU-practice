package bsu.radchuk.task.io;

import bsu.radchuk.task.model.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class RestIO {
    private Gson gson = new Gson();
    @NonNull
    private HttpServletResponse response;

    public void write(Object object) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(object));
    }

    public void info(int status, String msg) throws IOException {
        response.setStatus(status);
        write(Message.builder()
                .status(status)
                .data(msg)
                .build());
    }

    public <T> T read(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
