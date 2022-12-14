package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import com.my.web.command.Command;
import com.my.web.command.InOutSite.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BlockCardCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CardDAO cardDAO;

    public BlockCardCommand() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    public BlockCardCommand(CardDAO cardDAO) {
        BlockCardCommand.cardDAO = cardDAO;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts BlockCommand");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        boolean b = cardDAO.blockCard(request.getParameter("idCard"));
        Customer customer = (Customer) session.getAttribute("customer");
        LOG.debug(b);

        if (b){
            LOG.debug("block card successful");
            if (customer.getRole().equals("user")) {
                List<Card> cards = cardDAO.getAllByLogin((String) session.getAttribute("login"));
                session.setAttribute("cards", cards);
                LOG.info("Get cards and set user Cards -> " + cards);
            }else {
                forward = "controller?command=showCardsOfCustomer&login=" + session.getAttribute("login");
            }
        }else {
            LOG.error(Messages.ERR_CANNOT_BLOCK_CARD);
            session.setAttribute("error", "Please try again");
        }

        LOG.debug("Command end");

        return forward;
    }
}
