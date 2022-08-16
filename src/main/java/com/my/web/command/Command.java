package com.my.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
