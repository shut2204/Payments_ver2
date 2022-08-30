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

<%@ include file="/jspf/nav.jspf"%>


<section>
    <h1 style="color: red; text-align: center">${error}<p/>
    <h1 style="color: lime; text-align: center">${infoRequest}<p/>
    <h1 style="color: red; text-align: center">${errorRequest}<p/>
    <div class="parent">
        <c:if test="${sessionScope.get('cards') != null }">
            <c:set var="num" scope="session" value="${sessionScope.get('cards').size()}"/>
            <c:forEach var="card" items="${sessionScope.get('cards')}">
                    <div class="card">
                        <div class="obertka" id="obertka${num}">
                            <form action="controller" method="post">
                                <c:if test="${card.getStatus() == 'unlock'}">
                                    <input type="hidden" name="command" value="blockCard">
                                    <input type="hidden" name="idCard" value=${card.getIdcard()}>
                                    <input type="submit" class="blockCard" value="BLOCK">
                                </c:if>

                                <c:if test="${card.getStatus() == 'block'}">
                                    <h1 class="blockedCard">BLOCKED</h1>
                                    <input type="hidden" name="command" value="sendRequest">
                                    <input type="hidden" name="idCard" value=${card.getIdcard()}>
                                    <input type="submit" class="requestCard" value="REQUEST">
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
                                <c:if test="${card.getStatus() == 'unlock'}">
                                    <button id="myBtn${num}" class="addMoney">+ Add money</button>
                                </c:if>
                                <br />${card.getBalance()}</h5>
                            <img src="img/visa.png" class="logo1" />
                        </div>

                        <div class="popup1" id="mypopup${num}">
                            <div class="popup-content1">
                                <span class="close" id="close${num}">&times;</span>
                                <form class="form" action="controller" method="post">
                                    <p class="par" >How many</p>
                                    <input name="idCard" value="${card.getIdcard()}" type="hidden">
                                    <input type="text" class="sel" name="money">
                                    <input type="hidden" name="command" value="addMoneyOnCard">
                                    <div class="Button">
                                        <input class="button-29" role="button" type="submit" value="Submit">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <p hidden>${num = num-1}<p/>
            </c:forEach>
        </c:if>

        <div class="card">
            <button id="myBtn" class="addNewCardClass">+ Add new Card</button>
            <div class="popup" id="mypopup">
                <div class="popup-content">
                    <span class="close" id="closeOne">&times;</span>
                    <form class="form" action="controller" method="post">
                        <input type="hidden" name="command" value="addNewCard">
                        <p class="par" >Choose type</p>
                        <select class="sel" name="type" required="required">
                            <option value="">Choose...</option>
                            <option value="1">Personal</option>
                            <option value="2">Special</option>
                        </select>
                        <div class="Button">
                            <input class="button-29" role="button" type="submit" value="Submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>


    </div>
</section>

<%@ include file="/jspf/script.jspf"%>
</body>
</html>
