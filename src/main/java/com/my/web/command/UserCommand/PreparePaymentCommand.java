package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Payment;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import com.my.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PreparePaymentCommand extends Command {
    private static final Logger LOG = Logger.getLogger(PreparePaymentCommand.class);

    private static PaymentDAO paymentDAO;
    private static CardDAO cardDAO;

    public PreparePaymentCommand() {
        try {
            paymentDAO = new PaymentDAO();
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts Prepare");

        HttpSession session = request.getSession();

        String forward = PATH.TRANSFER_PAGE;

        String money = request.getParameter("howmany");

        if (!money.matches("\\d+") || !request.getParameter("numberCard2").matches("\\d+")||
           request.getParameter("type1").equals(request.getParameter("numberCard2"))
           || !request.getParameter("numberCard2").matches("\\d{16}")){

            session.setAttribute("infoPrepare", "");
            session.setAttribute("errorPrepare", "Incorrectly input data");
            return forward;
        }

        Card card1 = cardDAO.cardById(request.getParameter("type1"));
        Card card2 = cardDAO.cardById(request.getParameter("numberCard2"));
        if(card2 == null){
            session.setAttribute("errorPrepare", "this card does not exist");
            return forward;
        }

        if (card1.getStatus().equals("block") || card2.getStatus().equals("block")){
            session.setAttribute("infoPrepare", "");
            session.setAttribute("errorPrepare", "Sorry but one of the card is blocked");
            return forward;
        }


        Payment payment = new Payment();
        payment.setId_card(String.valueOf(card1.getIdcard()));
        payment.setAmount(Integer.parseInt(money));
        payment.setTo_card(String.valueOf(card2.getIdcard()));
        payment.setIdcustomer(card1.getIdcustomer());
        payment.setIdcustomer2(card2.getIdcustomer());

        boolean b = paymentDAO.prepare(payment);

        LOG.debug(b);

        if (b){
            session.setAttribute("errorPrepare", "");
            session.setAttribute("infoPrepare", "Your payment was successful prepare");
            LOG.debug("payment was prepare successful");
        }else {
            session.setAttribute("infoPrepare", "");
            session.setAttribute("errorPrepare", "Error prepare your payment, try again");
            LOG.error(Messages.ERR_CANNOT_TRANSFER_MONEY);
        }

        LOG.debug("Command end");

        return forward;
    }
}
