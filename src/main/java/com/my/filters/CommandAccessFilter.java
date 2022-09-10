package com.my.filters;


import com.my.PATH;
import com.my.db.entity.Customer;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "CommandAccessFilter",urlPatterns = "/com/my/Controller.java")
public class CommandAccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

    private final Map<String, List<String>> accessMap = new HashMap<>();
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");
        if (accessAllowed(servletRequest)) {
            LOG.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";

            servletRequest.setAttribute("errorMessage", errorMessasge);
            LOG.debug("Set the request attribute: errorMessage --> " + errorMessasge);

            servletRequest.getRequestDispatcher(PATH.PAGE_ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        LOG.debug(commandName);
        if (commandName == null || commandName.isEmpty()) {
            LOG.debug("first");
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        Customer customer = (Customer) session.getAttribute("customer");

        String userRole = customer.getRole();
        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
    }

    @Override
    public void init(FilterConfig fConfig) {
        LOG.debug("Filter initialization starts");

        // roles
        accessMap.put("user", asList(fConfig.getInitParameter("user")));
        accessMap.put("admin", asList(fConfig.getInitParameter("admin")));
        LOG.trace("Access map --> " + accessMap);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands --> " + outOfControl);

        LOG.debug("Filter initialization finished");
    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
