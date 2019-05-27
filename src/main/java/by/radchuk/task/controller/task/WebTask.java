package by.radchuk.task.controller.task;

import by.radchuk.task.controller.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebTask {
    String getPath();
    String getMethod();
    String getRequestContentType();
    byte getSecurityLevel();
    Response execute(HttpServletRequest request,
                     HttpServletResponse response);
}
