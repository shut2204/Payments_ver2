package com.my.web.command.InOutSite;

import com.my.PATH;
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

class RegistrationCommandTest extends Mockito {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    CustomerDAO customerDAO = mock(CustomerDAO.class);
    Customer customer =mock(Customer.class);

    @Test
    void execute() throws AppException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("pass")).thenReturn("passwordD!1");
        when(request.getParameter("contact")).thenReturn("0993308467");
        when(request.getParameter("name")).thenReturn("Maks");
        when(request.getParameter("lastname")).thenReturn("Nikolaienko");
        when(customerDAO.findUserByLogin("login")).thenReturn(customer);


        RegistrationCommand registrationCommand = new RegistrationCommand(customerDAO);
        registrationCommand.execute(request,response);

        verify(request, atLeastOnce()).getParameter("login");
        verify(session, atLeast(1)).setAttribute("errorRegister", "This login is already taken");
    }
}