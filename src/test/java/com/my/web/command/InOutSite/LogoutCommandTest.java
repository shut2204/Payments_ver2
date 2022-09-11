package com.my.web.command.InOutSite;

import com.my.PATH;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class LogoutCommandTest extends Mockito {

    @Test
    void execute() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);

        LogoutCommand logoutCommand = new LogoutCommand();
        String forward = logoutCommand.execute(request, response);
        verify(request, atLeast(1)).getSession(false);

        assertEquals(PATH.PAGE_LOGIN, forward);
    }
}