package com.my.web.command.InOutSite;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private CustomerDAO customerDAO;
    private PaymentDAO paymentDAO;

    public LoginCommand() {
        try {
            paymentDAO = new PaymentDAO();
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    public LoginCommand(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        HttpSession session = request.getSession();
        String forward = PATH.PAGE_LOGIN;

        String login = request.getParameter("login");
        LOG.debug("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()){
            session.setAttribute("errorLogin", "data entered incorrectly");
            return forward;
        }

        Customer customer = customerDAO.findUserByLogin(login);
        LOG.debug("Found in DB: user --> " + customer);

        if (customer == null || !password.equals(customer.getPassword_customer())){
            session.setAttribute("errorLogin", "Password is wrong");
            return forward;
        }

        if (customer.getStatus().equals("block")){
            session.setAttribute("errorLogin", "Sorry user was blocked");
            return forward;
        }

        if (customer.getRole().equals("user")){
            forward = PATH.PAGE_CABINET_USER;
            CardDAO cardDAO = new CardDAO();

            List<Card> cards = cardDAO.getAllByLogin(login);
            session.setAttribute("cards", cards);
            LOG.info("Get cards and set user Cards -> " + cards);
        }else {
            forward = PATH.PAGE_ADMIN;
        }

        session.setAttribute("errorLogin", "");
        session.setAttribute("customer", customer);
        LOG.trace("Set the session attribute: customer --> " + customer);
        session.setAttribute("login", login);
        LOG.trace("Set the session attribute: login --> " + login);


        LOG.info("Customer " + customer + " logged as " + customer.getRole().toLowerCase());

        LOG.debug("Command finished");
        return forward;
    }
}
