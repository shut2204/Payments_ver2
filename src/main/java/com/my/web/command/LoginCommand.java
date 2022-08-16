package com.my.web.command;

import com.my.PATH;
import com.my.db.CustomerDAO;
import com.my.db.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends Command{

    private static CustomerDAO customerDAO;

    public LoginCommand() {
        try {
            customerDAO = new CustomerDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LoginCommand(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_LOGIN;

        String login = request.getParameter("login");

        String password = request.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()){
            request.setAttribute("error", "data entered incorrectly");
            return forward;
        }

        Customer customer = customerDAO.findUserByLogin(login);
        if (customer == null || !password.equals(customer.getPassword_customer())){
            request.setAttribute("error", "Password is wrong");
            return forward;
        }

        if (customer.getRole().equals("user")){
            forward = PATH.PAGE_CABINET_USER;
        }

        session.setAttribute("customer", customer);
        session.setAttribute("login", login);

        return forward;
    }
}
