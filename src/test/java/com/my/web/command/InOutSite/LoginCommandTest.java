package com.my.web.command.InOutSite;

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

class LoginCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CustomerDAO customerDAO = mock(CustomerDAO.class);

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
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");
        when(customerDAO.findUserByLogin("login")).thenReturn(customer);
        when(customer.getPassword_customer()).thenReturn("passwsord");

        LoginCommand loginCommand = new LoginCommand(customerDAO);
        loginCommand.execute(request, response);

        verify(request, atLeastOnce()).getParameter("password");
        verify(request, atLeastOnce()).getParameter("login");
        verify(session, atLeast(1)).setAttribute("errorLogin","Password is wrong");
    }
}