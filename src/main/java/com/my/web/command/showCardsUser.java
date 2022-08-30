package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.entity.Card;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class showCardsUser extends Command{

    private static final Logger LOG = Logger.getLogger(showCardsUser.class);

    private static CardDAO cardDAO;

    public showCardsUser() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts showCardUser");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_ADMIN;

        List<Card> cardsUser = cardDAO.getAllByLogin(request.getParameter("login"));
        cardsUser.forEach(x -> System.out.println(x.getIdcard()));

        request.setAttribute("login" , request.getParameter("login"));
        LOG.debug("cards size -> " + cardsUser.size());
        if (!cardsUser.isEmpty()){
            LOG.debug("show cards successful");
            request.setAttribute("cardsUser", cardsUser);
            forward = PATH.PAGE_CARDS_USER;
        }else {
            LOG.error(Messages.ERR_CANNOT_BLOCK_CARD);
            session.setAttribute("errorShow", "Please try again");
        }

        LOG.debug("Command end");

        return forward;
    }
}
