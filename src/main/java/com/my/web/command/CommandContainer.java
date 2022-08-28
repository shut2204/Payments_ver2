package com.my.web.command;

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
        commands.put("blockCard", new BlockCommand());
        commands.put("prepare", new PreparePaymentCommand());
        commands.put("pagesOfPayments", new PagesOfPayments());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
