package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Payment;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class PreparePaymentCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PaymentDAO paymentDAO =  mock(PaymentDAO.class);
        CardDAO cardDAO = mock(CardDAO.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("howmany")).thenReturn("100");
        when(request.getParameter("numberCard2")).thenReturn("1234567890123456");
        when(request.getParameter("type1")).thenReturn("1234567890123457");

        Card card1 = new Card();
        card1.setStatus("unlock");
        Card card2 = new Card();
        card2.setStatus("unlock");

        when(cardDAO.cardById(request.getParameter("type1"))).thenReturn(card1);
        when(cardDAO.cardById(request.getParameter("numberCard2"))).thenReturn(card2);

        Payment payment = new Payment();
        payment.setIdcustomer2(2);
        payment.setId_payment(1);
        payment.setIdcustomer(1);
        payment.setAmount(100);
        payment.setId_card("1234567890123456");
        payment.setTo_card("1234567890123457");
        payment.setDate_of_payment("");
        payment.setStatus("prepare");

        when(paymentDAO.prepare(payment)).thenReturn(true);

        PreparePaymentCommand preparePaymentCommand = new PreparePaymentCommand(paymentDAO, cardDAO);
        String forward = preparePaymentCommand.execute(request, response);

        assertEquals(PATH.TRANSFER_PAGE, forward);

    }
}