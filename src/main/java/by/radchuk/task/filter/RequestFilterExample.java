package by.radchuk.task.filter;

import by.radchuk.task.controller.annotation.WebRequestFilter;
import by.radchuk.task.controller.filter.RequestFilter;

import javax.servlet.http.HttpServletRequest;

@WebRequestFilter(url = {"/check"})
public class RequestFilterExample implements RequestFilter {
    @Override
    public void filter(HttpServletRequest request) {
        System.out.println(1);
    }

}
