package com.my.web.command;

import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class BlockCardCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CardDAO cardDAO = mock(CardDAO.class);

        Customer customer = new Customer();
        customer.setRole("admin");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn("login");
        when(session.getAttribute("customer")).thenReturn(customer);
        when(cardDAO.blockCard(request.getParameter("idcard"))).thenReturn(false);

        BlockCardCommand blockCardCommand = new BlockCardCommand(cardDAO);
        String forward = blockCardCommand.execute(request, response);


        assertEquals("Cabinet.jsp", forward);
    }
}