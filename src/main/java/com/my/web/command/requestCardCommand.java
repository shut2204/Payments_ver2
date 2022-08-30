package com.my.web.command;

import com.my.PATH;
import com.my.db.CustomerDAO;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class requestCardCommand extends Command{

    private static final Logger LOG = Logger.getLogger(requestCardCommand.class);


    private static CustomerDAO customerDAO;

    public requestCardCommand() {
        try {
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts requestCardCommand");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        Customer customer = (Customer) session.getAttribute("customer");

        if (customerDAO.isExistRequest(request.getParameter("idCard"))){
            session.setAttribute("infoRequest", "");
            session.setAttribute("errorRequest", "Request already exists");
            return forward;
        }

        boolean b = customerDAO.sendRequest(String.valueOf(customer.getIdcustomer()), request.getParameter("idCard"));

        if (b){
            session.setAttribute("errorRequest","");
            session.setAttribute("infoRequest", "Request successful create");
            LOG.debug("Request add successful");
        }else {
            LOG.error(Messages.ERR_CANNOT_ADD_CUSTOMER);
            session.setAttribute("errorRequest", "Request not created, Please try again later");
            return forward;
        }

        LOG.debug("Command request end");
        return forward;
    }
}
