package com.my.service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public final class Util {

    public static ArrayList<String> validateDataRegister(HttpServletRequest request){
        String contact = request.getParameter("contact");
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String pass = request.getParameter("pass");

        ArrayList<String> errors = new ArrayList<>();

        if(!login.matches("^[a-zA-Z\\d._-]{3,}$")           || login.isEmpty()) errors.add("Incorrect login");
        if(!name.matches("[A-Z][a-z]*|[А-ЯёЁ][а-я]*")       || name.isEmpty()) errors.add("Incorrect First name");
        if(!lastname.matches("[A-Z][a-z]*|[А-Я][а-я]*")     || lastname.isEmpty()) errors.add("Incorrect Last name");
        if(!pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$") || pass.isEmpty()) errors.add("Incorrect password");
        if(!contact.matches("\\d{10}")                      || contact.isEmpty()) errors.add("Incorrect number");

        return errors;
    }

}
