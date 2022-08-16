package com.my.web.command;

import com.my.PATH;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class noCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);

        return PATH.PAGE_ERROR_PAGE;
    }
}
