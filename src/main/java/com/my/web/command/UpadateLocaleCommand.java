package com.my.web.command;

import com.my.PATH;
import com.my.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpadateLocaleCommand extends Command{

    Logger LOG = Logger.getLogger(UpadateLocaleCommand.class);

    public UpadateLocaleCommand() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("UpdateLocaleCommand starts");

        HttpSession session = request.getSession();

        String locale = (String) session.getAttribute("locale");

        String url = request.getRequestURL().toString();
        LOG.debug(url);

        if (locale == null || locale.equals("ru")) session.setAttribute("locale", "en");
        else session.setAttribute("locale", "ru");

        LOG.debug("UpdateLocaleCommand finished");
        LOG.debug(request.getRequestURI());
        return PATH.PAGE_INDEX;
    }
}
