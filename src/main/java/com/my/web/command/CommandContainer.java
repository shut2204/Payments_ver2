package com.my.web.command;

import com.my.web.command.AdminCommand.*;
import com.my.web.command.InOutSite.LoginCommand;
import com.my.web.command.InOutSite.LogoutCommand;
import com.my.web.command.InOutSite.RegistrationCommand;
import com.my.web.command.UserCommand.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private CommandContainer() {
        // no op
    }


    private static Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login" , new LoginCommand());
        commands.put("noCommand" , new noCommand());
        commands.put("registration" , new RegistrationCommand());
        commands.put("logout" , new LogoutCommand());
        commands.put("addNewCard", new AddCardCommand());
        commands.put("addMoneyOnCard" , new AddMoneyOnCard());
        commands.put("transferMoney", new TransferMoneyCommand());
        commands.put("blockCard", new BlockCardCommand());
        commands.put("prepare", new PreparePaymentCommand());
        commands.put("pagesOfPayments", new PagesOfPayments());
        commands.put("sendRequest", new RequestCardCommand());
        commands.put("pagesOfCustomers", new PagesOfCustomers());
        commands.put("showCardsOfCustomer", new showCardsUser());
        commands.put("pagesOfRequests", new PagesOfRequsts());
        commands.put("unlockCard", new unlockCardCommand());
        commands.put("blockUser", new BlockUserCommand());
        commands.put("pagesOfCards", new getCardsCommand());
        commands.put("updateLocale", new UpadateLocaleCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
