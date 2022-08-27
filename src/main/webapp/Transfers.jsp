<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 27.08.2022
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Payments</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file="/css/style1.css"%>
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>

<body>
    <%@ include file="/jspf/nav.jspf"%>
    <section>
        <h1 style="color: red; text-align: center" >${errorTransfer}</h1>
        <div class="parent">
            <form class="parent" action="controller">
            <div class="card1">
                    <h3 class="bank">Payments</h3>
                    <img src="img/chip.png" class="chip" />
                    <img src="img/contactless-indicator.png" class="indicator" />

                    <select class="sel1" name="type1" required="required">
                        <option class="option" value="">Choose your card...</option>
                        <c:forEach var="card" items="${sessionScope.get('cards')}">
                            <option value="${card.getIdcard()}">${card.getNumber_card()}</option>
                        </c:forEach>
                    </select>
                    <img src="img/visa.png" class="logo1" />
                </div>

                <div class="card1">
                    <h3 class="bank">Payments</h3>
                    <img src="img/chip.png" class="chip" />
                    <img src="img/contactless-indicator.png" class="indicator" />
                        <input type="hidden" name="command" value="transferMoney">
                        <input class="numberCard2" required name="numberCard2">
                        <h5 class="card-holder">
                            <span style="font-size: large">How many</span><br />
                            <input class="howmuch" required type="text" name="howmany">
                        </h5>
                        <button class="button-29">Prepare</button>

                    <img src="img/visa.png" class="logo1" />
                </div>
            </div>
        </form>
    </section>
</body>
</html>
