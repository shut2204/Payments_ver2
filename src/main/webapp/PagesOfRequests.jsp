<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 31.08.2022
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Payments - Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file="/css/style1.css"%>
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>

<body>

<%@ include file="/jspf/navAdmin.jspf"%>

<section>
    <h1 style="text-align: center; color: lime">${sessionScope.get("infoUnlock")}</h1>

    <h1 style="text-align: center; color: red">${sessionScope.get("errorUnlock")}</h1>

    <div class="wrapTable">
        <table class="table">
            <tr>
                <th>Id Customer</th>
                <th>Id Card</th>
                <th>Action</th>
            </tr>
            <c:forEach var="request" items="${sessionScope.get('requests')}">
                <tr>
                    <td>${request.getIdCustomer()}</td>
                    <td>${request.getIdCard()}</td>
                    <form method="post" action="controller">
                        <input type="hidden" name="idCard" value="${request.getIdCard()}">
                        <input type="hidden" name="command" value="unlockCard">
                        <td><button class="button-29">Unlock</button></td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="tablePages">
        <h3>|||</h3>
        <c:forEach begin="1" end="${sessionScope.get('pagesAll')}" var="number">
            <a href="controller?command=pagesOfRequests&page=${number}">${number}</a>
        </c:forEach>
        <h3>|||</h3>
    </div>
</section>

</body>
</html>
