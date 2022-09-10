package com.my;

import com.my.exception.AppException;
import com.my.web.command.Command;
import com.my.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@WebServlet(name = "Controller",  value = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(process(req,resp)).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(process(req,resp));
    }

    private String process(HttpServletRequest request, HttpServletResponse response) throws IOException {


        // extract command name from the request
        String commandName = request.getParameter("command");

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);

        // execute command and get forward address
        String forward = PATH.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException e) {
            response.sendError(500, "Cant process command");
        }
        // go to forward
        return forward;
    }
}
