package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BlockUserCommand extends Command{

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CustomerDAO customerDAO;

    public BlockUserCommand() {
        try {
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts BlockUserCommand");

        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        boolean b = customerDAO.blockUser(request.getParameter("login"));
        LOG.debug(b);

        if (b){
            LOG.debug("block user successful");
            session.setAttribute("errorShow", "");
            session.setAttribute("infoShow", "We block user by login " + request.getParameter("login"));
            forward = "controller?command=pagesOfCustomers";
        }else {
            LOG.error(Messages.ERR_CANNOT_BLOCK_CARD);
            session.setAttribute("infoShow", "");
            session.setAttribute("error", "Please try again");
        }

        LOG.debug("Command end");

        return forward;
    }
}
