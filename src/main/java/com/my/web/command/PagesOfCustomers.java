package com.my.web.command;

import com.my.PATH;
import com.my.db.CustomerDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Customer;
import com.my.db.entity.Payment;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PagesOfCustomers extends Command{
    private static final Logger LOG = Logger.getLogger(PagesOfCustomers.class);

    private static CustomerDAO customerDAO;

    public PagesOfCustomers() {
        try {
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts CustomerPagesCommand");

        HttpSession session = request.getSession();

        session.setAttribute("errorAdmin", "");

        String forward = PATH.PAGE_CABINET_ADMIN;

        int currentPage = 0;

        if (request.getParameter("page") != null) {
            currentPage = (Integer.parseInt(request.getParameter("page"))-1) * 5;
        }

        int count = 0;
        int pagesAll = 0;
        List<Customer> customers;

        if (request.getParameter("search") != null && !request.getParameter("search").isEmpty()){
            customers = customerDAO.search(request.getParameter("search"));
            session.setAttribute("infoShow","");
            session.setAttribute("pagesAll", pagesAll);
            session.setAttribute("customers", customers);
            return forward;
        }

        customers = customerDAO.getAll(currentPage);
        count = customerDAO.countAll();


        LOG.debug(count + " count of customers");
        pagesAll = count / 5;
        int pages = count % 5;
        LOG.debug(pagesAll + " pagesAll");
        if (pages > 0) {pagesAll++;}
        LOG.debug(pagesAll + " pagesAll");

        session.setAttribute("pagesAll", pagesAll);
        session.setAttribute("customers", customers);

        LOG.debug("Command end");

        return forward;
    }
}
