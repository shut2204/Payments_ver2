<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 30.08.2022
  Time: 15:35
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
    <h1 style="color: lime; text-align: center">${infoShow}</h1>

    <h1 style="color: red; text-align: center">${errorShow}</h1>
    <div class="wrapTable">
        <table class="table">
            <tr>
                <th>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="pagesOfCustomers">
                        <input class="search" type="text" name="search" placeholder="Search by login">
                        <button class="button-29">Go</button>
                    </form>
                </th>
            </tr>
            <tr>
                <th>Id Customer</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Login</th>
                <th>Action</th>
                <th>Cards</th>
            </tr>
            <c:forEach var="customer" items="${sessionScope.get('customers')}">
                <tr>
                    <td>${customer.getIdcustomer()}</td>
                    <td>${customer.getFirst_name()}</td>
                    <td>${customer.getLast_name()}</td>
                    <td>${customer.getLogin()}</td>
                    <form method="post" action="controller">
                        <input type="hidden" name="command" value="blockUser">
                        <input type="hidden" name="login" value="${customer.getLogin()}">
                        <td>
                            <c:if test="${customer.getStatus().equals('unlock')}">
                                <button class="button-29">Block</button>
                            </c:if>
                        </td>
                    </form>
                    <form method="get" action="controller">
                        <input type="hidden" name="command" value="showCardsOfCustomer">
                        <input type="hidden" name="login" value="${customer.getLogin()}">
                        <td><button class="button-29">To cards</button></td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="tablePages">
        <h3>|||</h3>
        <c:forEach begin="1" end="${sessionScope.get('pagesAll')}" var="number">
            <a href="controller?command=pagesOfCustomers&page=${number}">${number}</a>
        </c:forEach>
        <h3>|||</h3>
    </div>
</section>


</body>
</html>
