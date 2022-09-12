package com.my.web.command.AdminCommand;

import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class BlockUserCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CustomerDAO customerDAO = mock(CustomerDAO.class);

        when(request.getSession()).thenReturn(session);
        when(customerDAO.blockUser(request.getParameter("login"))).thenReturn(true);

        BlockUserCommand blockUserCommand = new BlockUserCommand(customerDAO);
        String forward = blockUserCommand.execute(request, response);

        verify(session, atLeast(1)).setAttribute("errorShow", "");

        assertEquals("controller?command=pagesOfCustomers",forward);
    }
}