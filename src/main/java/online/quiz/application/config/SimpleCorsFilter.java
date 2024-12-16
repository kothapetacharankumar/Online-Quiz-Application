package online.quiz.application.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse= (HttpServletResponse) response;
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        String originHeader=httpServletRequest.getHeader("origin");
        httpServletResponse.setHeader("Access-Control-Allow-Origin",originHeader);
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST, GET,PUT,OPTIONS,DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","Authorization, Content-Type, X-Requested-With");


        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod()))
        {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }
        else chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
