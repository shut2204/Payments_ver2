package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.entity.Card;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddMoneyOnCard extends Command{

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CardDAO cardDAO;

    public AddMoneyOnCard() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        boolean b = cardDAO.addMoney(request.getParameter("idCard"), request.getParameter("money"));
        LOG.debug(b);
        if (b){
            LOG.info("balance up successful");
            List<Card> cards = cardDAO.getAllByLogin((String) session.getAttribute("login"));
            session.setAttribute("cards", cards);
            LOG.info("Get cards and set user Cards -> " + cards);
        }else {
            LOG.error(Messages.ERR_CANNOT_UP_BALANCE);
            session.setAttribute("error", "Please try again");
        }

        LOG.debug("Command end");

        return forward;
    }
}