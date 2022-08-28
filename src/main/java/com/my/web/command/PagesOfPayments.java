package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Payment;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class PagesOfPayments extends Command{
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static PaymentDAO paymentDAO;
    private static CustomerDAO customerDAO;

    public PagesOfPayments() {
        try {
            paymentDAO = new PaymentDAO();
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts PaymentPagesCommand");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_PAYMENTS;

        int currentPage = 0;

        if (request.getParameter("page") != null) {
            currentPage = (Integer.parseInt(request.getParameter("page"))-1) * 5;
        }

        int idCustomer = customerDAO.findUserIDByLogin((String) session.getAttribute("login"));

        int count = paymentDAO.countOfPaymentsById(String.valueOf(idCustomer));

        int pagesAll = count / 5;

        if (pagesAll > 0) pagesAll++;

        session.setAttribute("pagesAll", pagesAll);
        LOG.debug(pagesAll);
        LOG.debug(String.valueOf(idCustomer));
        LOG.debug(String.valueOf(currentPage));
        List<Payment> payments = paymentDAO.getAllById(String.valueOf(idCustomer), currentPage);

        session.setAttribute("payments", payments);

        LOG.debug("Command end");

        return forward;
    }
}
