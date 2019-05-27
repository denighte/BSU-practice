package by.radchuk.task.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Deprecated
@Slf4j
@WebFilter(urlPatterns = {"/*"})
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long elapsed = System.currentTimeMillis() - start;
        StringBuilder data = new StringBuilder("servlet");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            data.setLength(0);
            data.append(httpRequest.getMethod()).append(" - ")
                .append(httpRequest.getRequestURL());
        }
        log.info(data.append(" - ").append(elapsed).append(" ms.").toString());
    }

    @Override
    public void destroy() {
    }
}
