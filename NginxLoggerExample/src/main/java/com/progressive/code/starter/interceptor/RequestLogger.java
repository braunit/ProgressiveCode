package com.progressive.code.starter.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/* Name "RequestLogger" to be able to use @Autowired in the configuration */
@Component("RequestLogger")
public class RequestLogger implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        RequestLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler)
            throws Exception {

        /* Logs the "real" remote ip address, as set by Nginx */
        LOGGER.info("Page {} was requested by IP {}", request.getRequestURI(),
            request.getHeader("X-Forwarded-For"));

        /* Logs the referrer, as set by Nginx */
        LOGGER.info("Referrer is {}", request.getHeader("X-Forwarded-Referrer"));
        
        /* Logs the query string */
        LOGGER.info("Parameters / Query String is {}", request.getQueryString());

        /* Logs the user agent (e.g. Browser and Version */
        LOGGER.info("User Agent {}", request.getHeader("user-agent"));

        /* Logs the accepted language */
        LOGGER.info("Accepted Language {}", request.getLocale());
        
        /* Log all headers */
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
        	String headerName = headerNames.nextElement();
        	LOGGER.info("key {}, value {}", headerName, request.getHeader(headerName));
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                Object handler,ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                Object handler, Exception ex) throws Exception {
    }
}