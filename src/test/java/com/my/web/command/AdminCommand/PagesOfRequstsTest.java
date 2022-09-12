package com.my.web.command.AdminCommand;

import com.my.PATH;
import com.my.db.RequestDAO;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PagesOfRequstsTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDAO requestDAO = mock(RequestDAO.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("page")).thenReturn("1");
        when(requestDAO.getAll(1)).thenReturn(new ArrayList<>());
        when(requestDAO.countAll()).thenReturn(5);

        PagesOfRequsts pagesOfRequsts = new PagesOfRequsts(requestDAO);
        String forward = pagesOfRequsts.execute(request, response);

        assertEquals(PATH.PAGE_REQUESTS,forward);
    }
}