package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class getCardsCommand extends Command{
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CardDAO cardDAO;

    public getCardsCommand() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts getCardsCommand");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        Customer customer = (Customer) session.getAttribute("customer");

        if (customer.getRole().equals("admin")){
            LOG.debug(session.getAttribute("login"));
            List<Card> cards = cardDAO.getAllByLoginSort(String.valueOf(session.getAttribute("login")), Integer.parseInt(request.getParameter("type")));
            forward = PATH.PAGE_CARDS_USER;
            request.setAttribute("cardsUser", cards);
            LOG.debug(cards);
        }else {
            List<Card> cards = cardDAO.getAllByLoginSort((String) session.getAttribute("login"), Integer.parseInt(request.getParameter("type")));
            session.setAttribute("cards", cards);
        }
        LOG.debug("Command end");

        return forward;
    }
}
