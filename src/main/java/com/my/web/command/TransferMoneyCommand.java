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

public class TransferMoneyCommand extends Command{

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CardDAO cardDAO;

    public TransferMoneyCommand() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts Transfer");

        HttpSession session = request.getSession();

        String forward = PATH.TRANSFER_PAGE;

        String money = request.getParameter("howmany");

        if (!money.matches("\\d+") || !request.getParameter("numberCard2").matches("\\d+")||
            request.getParameter("type1").equals(request.getParameter("numberCard2"))
            || !request.getParameter("numberCard2").matches("\\d{16}")){
            session.setAttribute("errorTransfer", "Incorrectly input data");
            return forward;
        }

        Card card1 = cardDAO.cardById(request.getParameter("type1"));
        Card card2 = cardDAO.cardById(request.getParameter("numberCard2"));

        if (card1.getStatus().equals("block") || card2.getStatus().equals("block")){
            session.setAttribute("errorTransfer", "Sorry but one of the card is blocked");
            return forward;
        }


       // if (card1.getBalance() - Integer.parseInt(money) > 0)

        boolean b = cardDAO.transferMoney(request.getParameter("type1"), request.getParameter("numberCard2"), money);

        LOG.debug(b);

        if (b){
            LOG.debug("transfer was successful");
            List<Card> cards = cardDAO.getAllByLogin((String) session.getAttribute("login"));
            session.setAttribute("cards", cards);
            LOG.info("Get cards and set user Cards -> " + cards);
        }else {
            LOG.error(Messages.ERR_CANNOT_TRANSFER_MONEY);
            session.setAttribute("error", "Transfer fail, try again");
        }

        LOG.debug("Command end");

        return forward;
    }
}
