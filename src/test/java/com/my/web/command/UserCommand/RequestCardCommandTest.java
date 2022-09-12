package com.my.web.command.UserCommand;

import com.my.PATH;
import com.my.db.CustomerDAO;
import com.my.db.RequestDAO;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestCardCommandTest extends Mockito {

    @Test
    void execute() throws AppException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CustomerDAO customerDAO = mock(CustomerDAO.class);

        Customer customer = new Customer();
        customer.setRole("admin");
        customer.setIdcustomer(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(customerDAO.isExistRequest(request.getParameter("idCard"))).thenReturn(false);
        when(customerDAO.sendRequest(String.valueOf(customer.getIdcustomer()), request.getParameter("idCard"))).thenReturn(true);

        RequestCardCommand requestCardCommand = new RequestCardCommand(customerDAO);
        String forward = requestCardCommand.execute(request, response);

        assertEquals(PATH.PAGE_CABINET_USER, forward);
    }
}