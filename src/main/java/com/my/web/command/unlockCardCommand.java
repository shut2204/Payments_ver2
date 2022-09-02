package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.RequestDAO;
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

public class unlockCardCommand extends Command{

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CardDAO cardDAO;

    public unlockCardCommand() {
        try {
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        LOG.debug("Command starts unlockCardCommand");

        HttpSession session = request.getSession();

        String forward = "controller?command=pagesOfRequests";

        boolean b = cardDAO.unlockCard(request.getParameter("idCard"));
        LOG.debug(b);
        if (b){
            LOG.debug("unlock card successful");
            session.setAttribute("errorUnlock", "");

            session.setAttribute("infoUnlock", "Card unlock");
        }else {
            LOG.error(Messages.ERR_CANNOT_UNLOCK_CARD);

            session.setAttribute("infoUnlock", "");

            session.setAttribute("errorUnlock", "Please try again later");
        }

        LOG.debug("Command end");

        return forward;
    }
}
