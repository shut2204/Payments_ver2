<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 31.07.2022
  Time: 16:20
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

<%@ include file="/jspf/navAdmin.jspf"%>


<section>
    <div class="parent">
        <c:if test="${requestScope.get('cardsUser') != null }">
            <c:forEach var="card" items="${requestScope.get('cardsUser')}">
                    <div class="card">
                            <form action="controller" method="post">
                                <c:if test="${card.getStatus() == 'unlock'}">
                                    <input type="hidden" name="command" value="blockCard">
                                    <input type="hidden" name="login" value="${requestScope.get("login")}">
                                    <input type="hidden" name="idCard" value=${card.getIdcard()}>
                                    <input type="submit" class="blockCard" value="BLOCK">
                                </c:if>
                            </form>

                            <h3 class="bank">Payments</h3>
                            <img src="img/chip.png" class="chip" />
                            <img src="img/contactless-indicator.png" class="indicator" />
                            <h3 class="number">${card.getNumber_card()}</h3>
                            <h5 class="card-holder">
                                <span>card type</span><br />
                                    ${card.getName_card()}
                            </h5>
                            <h5 class="valid"><span class="balance">Balance</span>
                                <br />${card.getBalance()}</h5>
                            <img src="img/visa.png" class="logo1" />
                    </div>
            </c:forEach>
        </c:if>
    </div>
</section>
</body>
</html>
