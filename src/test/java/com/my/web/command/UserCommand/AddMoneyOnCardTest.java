package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class AddMoneyOnCardTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PaymentDAO paymentDAO =  mock(PaymentDAO.class);
        CardDAO cardDAO = mock(CardDAO.class);

        Card card = new Card();
        card.setStatus("unlock");

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("money")).thenReturn("100");
        when(cardDAO.cardById(request.getParameter("idCard"))).thenReturn(card);
        when(cardDAO.addMoney(request.getParameter("idCard"), request.getParameter("money"))).thenReturn(false);

        AddMoneyOnCard addMoneyOnCard = new AddMoneyOnCard(cardDAO);
        String forward = addMoneyOnCard.execute(request,response);

        assertEquals(PATH.PAGE_CABINET_USER, forward);
    }


}