package com.my.web.command.AdminCommand;

import com.my.PATH;
import com.my.db.RequestDAO;
import com.my.db.entity.Request;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PagesOfRequsts extends Command {
    private static final Logger LOG = Logger.getLogger(PagesOfRequsts.class);

    private static RequestDAO requestDAO;

    public PagesOfRequsts() {
        try {
            requestDAO = new RequestDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    public PagesOfRequsts(RequestDAO requestDAO) {
        PagesOfRequsts.requestDAO = requestDAO;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts RequestPagesCommand");
        HttpSession session = request.getSession();

        session.setAttribute("errorRequests", "");

        String forward = PATH.PAGE_REQUESTS;

        int currentPage = 0;

        if (request.getParameter("page") != null) {
            currentPage = (Integer.parseInt(request.getParameter("page"))-1) * 5;
        }


        int count;

        List<Request> requests;

        requests = requestDAO.getAll(currentPage);
        count = requestDAO.countAll();


        LOG.debug(count + " count of requests");
        int pagesAll = count / 5;
        int pages = count % 5;
        LOG.debug(pagesAll + " pagesAll");
        if (pages > 0) {pagesAll++;}
        LOG.debug(pagesAll + " pagesAll");

        session.setAttribute("pagesAll", pagesAll);
        session.setAttribute("requests", requests);

        LOG.debug("Command end");

        return forward;
    }
}
