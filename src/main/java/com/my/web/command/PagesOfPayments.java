package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
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

    public PagesOfPayments() {
        try {
            paymentDAO = new PaymentDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts PaymentPagesCommand");

        HttpSession session = request.getSession();

        session.setAttribute("errorTransfer", "");

        String forward = PATH.PAGE_PAYMENTS;

        int currentPage = 0;

        if (request.getParameter("page") != null) {
            currentPage = (Integer.parseInt(request.getParameter("page"))-1) * 5;
        }

        Customer customer = ((Customer) session.getAttribute("customer"));

        int idCustomer = customer.getIdcustomer();


        int count;

        List<Payment> payments;
        if (customer.getRole().equals("user")) {
            payments = paymentDAO.getAllById(String.valueOf(idCustomer), currentPage);
            count = paymentDAO.countOfPaymentsById(String.valueOf(idCustomer));
        }else {
            payments = paymentDAO.getAll(currentPage);
            count = paymentDAO.countOfPayments();
        }

        LOG.debug(count + " count of payments");
        int pagesAll = count / 5;
        int pages = count % 5;
        LOG.debug(pagesAll + " pagesAll");
        if (pages > 0) {pagesAll++;}
        LOG.debug(pagesAll + " pagesAll");

        session.setAttribute("pagesAll", pagesAll);
        session.setAttribute("payments", payments);

        LOG.debug("Command end");

        return forward;
    }
}
