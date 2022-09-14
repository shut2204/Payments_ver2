package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.PaymentDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class AddCardCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CustomerDAO customerDAO = mock(CustomerDAO.class);
        CardDAO cardDAO = mock(CardDAO.class);

        Card card = new Card();
        card.setStatus("unlock");

        Customer customer = mock(Customer.class);
        customer.setIdcustomer(1);
        customer.setLogin("login");
        customer.setStatus("unlock");
        customer.setPassword_customer("password");
        customer.setRole("user");
        customer.setFirst_name("Some name");
        customer.setLast_name("SomeSname");
        customer.setPhone_number("0993308467");


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn("login");
        when(customerDAO.findUserIDByLogin("login")).thenReturn(2);
        when(cardDAO.add(card)).thenReturn(true);
        when(request.getParameter("type")).thenReturn("1");

        AddCardCommand addCardCommand = new AddCardCommand(cardDAO, customerDAO);
        String forward = addCardCommand.execute(request,response);

        verify(session, atLeast(1)).getAttribute("login");

        assertEquals(PATH.PAGE_CABINET_USER, forward);
    }
}