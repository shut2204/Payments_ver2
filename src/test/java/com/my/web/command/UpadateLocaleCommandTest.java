package com.my.web.command;

import com.my.exception.AppException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class UpadateLocaleCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("locale")).thenReturn("us");

        UpadateLocaleCommand upadateLocaleCommand = new UpadateLocaleCommand();
        upadateLocaleCommand.execute(request,response);

        verify(request, atLeastOnce()).getParameter("locale");
        verify(session, atLeastOnce()).setAttribute("locale", request.getParameter("locale"));
    }
}