package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.exception.AppException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class TransferMoneyCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CardDAO cardDAO = mock(CardDAO.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("howmany")).thenReturn("100a");

        TransferMoneyCommand transferMoneyCommand = new TransferMoneyCommand(cardDAO);
        String forward = transferMoneyCommand.execute(request,response);

        assertEquals(PATH.PAGE_PAYMENTS, forward);
    }
}