<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 28.08.2022
  Time: 19:52
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
        <table>
            <c:forEach var="payment" items="${sessionScope.get('payments')}">
                <tr>
                    <td>${payment.getId_payment()}</td>
                    <td>${payment.getAmount()}</td>
                </tr>
            </c:forEach>
<br>
            <c:forEach begin="1" end="${sessionScope.get('pagesAll')}" var="number">
                <a href="controller?command=pagesOfPayments&page=${number}">${number}</a>
            </c:forEach>
        </table>
    </section>
</body>
</html>
