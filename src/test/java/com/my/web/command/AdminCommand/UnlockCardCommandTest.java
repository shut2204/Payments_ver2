package com.my.web.command.AdminCommand;

import com.my.db.CardDAO;
import com.my.exception.AppException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnlockCardCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CardDAO cardDAO = mock(CardDAO.class);

        when(request.getSession()).thenReturn(session);
        when(cardDAO.unlockCard(request.getParameter("idCard"))).thenReturn(true);

        unlockCardCommand unlockCardCommand = new unlockCardCommand(cardDAO);
        String forward = unlockCardCommand.execute(request,response);

        assertEquals("controller?command=pagesOfRequests", forward);
    }
}