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
<nav>
    <input type="checkbox" id="check">
    <label for="check" class="checkbtn">
        <i class="fas fa-bars"></i>
    </label>
    <label class="logo">Payments</label>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Services</a></li>
        <li><a class="active" href="controller?command=logout">Logout</a></li>
    </ul>
</nav>

<section>
    <span class="large">Your Cards:</span>
    <p>${error}</p>
    <div class="parent">
        <c:if test="${sessionScope.get('cards') != null }">
            <c:set var="num" scope="session" value="${sessionScope.get('cards').size()}"/>
            <c:forEach var="card" items="${sessionScope.get('cards')}">
                    <div class="card">
                        <div class="obertka" id="obertka${num}">
                            <h3 class="bank">Payments</h3>
                            <img src="img/chip.png" class="chip" />
                            <img src="img/contactless-indicator.png" class="indicator" />
                            <h3 class="number">${card.getNumber_card()}</h3>
                            <h5 class="card-holder">
                                <span>card type</span><br />
                                    ${card.getName_card()}
                            </h5>
                            <h5 class="valid"><span class="balance">Balance</span>
                                <button id="myBtn${num}" class="addMoney">+ Add money</button>
                                <br />${card.getBalance()}</h5>
                            <img src="img/visa.png" class="logo1" />
                        </div>

                        <div class="popup1" id="mypopup${num}">
                            <div class="popup-content1">
                                <span class="close" id="close${num}">&times;</span>
                                <form class="form" action="controller" method="post">
                                    <input type="hidden" name="command" value="addNewCard">
                                    <p class="par" >${card.getNumber_card()}</p>
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
                    <p hidden>${num = num-1}<p/>
            </c:forEach>
        </c:if>

        <div class="card">
            <button id="myBtn" class="addNewCardClass">+ Add new Card</button>
            <div class="popup" id="mypopup">
                <div class="popup-content">
                    <span class="close">&times;</span>
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
<script src="script/script.js"></script>
<script>
    let flag = false;
    allvalues = document.getElementsByClassName('obertka');
    allpopup = document.getElementsByClassName('popup1');
    allclose = document.getElementsByClassName('close');
    alladdmaoney = document.getElementsByClassName('addMoney');

    <c:forEach var="card" items="${sessionScope.get('cards')}">

        ${num = num+1}
        fon${num} = document.getElementById('obertka${num}'),
        popup${num} = document.getElementById('mypopup${num}'),
        popupToggle${num} = document.getElementById('myBtn${num}');
        popupClose${num} = document.getElementById('close${num}');

        popupToggle${num}.onclick = function () {
            if (flag === false){
                console.log("if")
                fon${num}.style.display = "none";
                popupToggle${num}.style.display = "none";
                popup${num}.style.display = "block";
                popupClose${num}.style.display = "block";
                flag = true;
            }else {
                console.log("begin else")
                Array.prototype.forEach.call(allvalues, child => {
                    child.style.display = "block";
                });
                Array.prototype.forEach.call(allpopup, child => {
                    child.style.display = "none";
                });
                Array.prototype.forEach.call(allclose, child => {
                    child.style.display = "none";
                });
                Array.prototype.forEach.call(alladdmaoney, child => {
                    child.style.display = "block";
                });

                console.log("begin fun")
                fon${num}.style.display = "none";
                popupToggle${num}.style.display = "none";
                popup${num}.style.display = "block";
                popupClose${num}.style.display = "block";
                flag = true;
            }
        };

        popupClose${num}.onclick = function () {
            flag = false;
            popup${num}.style.display = "none";
            fon${num}.style.display = "block";
            popupToggle${num}.style.display = "block";
        };


    </c:forEach>
</script>

</body>
</html>
