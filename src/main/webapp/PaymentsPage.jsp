<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 28.08.2022
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="myTags" prefix="mg"%>

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
<c:if test="${sessionScope.get('customer').getRole().equals('user')}">
    <%@ include file="/jspf/nav.jspf"%>
</c:if>
<c:if test="${sessionScope.get('customer').getRole().equals('admin')}">
    <%@ include file="/jspf/navAdmin.jspf"%>
</c:if>
    <section>
        <h1 style="text-align: center; color: red">${sessionScope.get("errorTransfer")}</h1>
        <h1 style="text-align: center; color: lime">${sessionScope.get("infoTransfer")}</h1>
        <div class="wrapTable">
            <table class="table">
                    <tr>
                        <th>
                            <div><h1><fmt:message key="label.sortby"/>...</h1></div>
                            <form method="get" action="controller">
                                <input type="hidden" name="command" value="pagesOfPayments">
                                <select class="sel" name="type" required="required">
                                    <option value=""><fmt:message key="label.choose"/>...</option>
                                    <option value="1"><fmt:message key="label.number"/></option>
                                    <option value="2"><fmt:message key="label.oldtonew"/></option>
                                    <option value="3"><fmt:message key="label.newtoold"/></option>
                                </select>
                                <button class="button-29"><fmt:message key="label.sort"/></button>
                            </form>
                        </th>
                    </tr>
                    <tr>
                        <th>Id <fmt:message key="label.payment"/></th>
                        <th><fmt:message key="label.date"/></th>
                        <th><fmt:message key="label.amount"/></th>
                        <th><fmt:message key="label.fromcard"/></th>
                        <th><fmt:message key="label.tocard"/></th>
                        <th><fmt:message key="label.status"/></th>
                        <c:if test="${sessionScope.get('customer').getRole().equals('user')}">
                            <th><fmt:message key="label.action"/></th>
                        </c:if>
                    </tr>
                <c:forEach var="payment" items="${sessionScope.get('payments')}">
                    <tr>
                        <td>${payment.getId_payment()}</td>
                        <td>${payment.getDate_of_payment()}</td>
                        <td>${payment.getAmount()}</td>
                        <td>${payment.getId_card()}</td>
                        <td>${payment.getTo_card()}</td>
                        <td>${payment.getStatus()}</td>
                        <c:if test="${sessionScope.get('customer').getRole().equals('user')}">
                            <c:if test="${sessionScope.get('cards').stream().filter(x -> x.getIdcard() == payment.getId_card()).findFirst().orElse(null) != null}">
                                <c:if test="${!payment.getStatus().equals('sent')}">
                                    <form method="post" action="controller">

                                        <input type="hidden"    name="command"     value="transferMoney">
                                        <input type="hidden"    name="idpayment"   value="${payment.getId_payment()}">
                                        <input type="hidden"    name="type1"       value="${payment.getId_card()}">
                                        <input type="hidden"    name="numberCard2" value="${payment.getTo_card()}">
                                        <input type="hidden"    name="howmany"     value="${payment.getAmount()}">

                                        <td><button class="button-29"><fmt:message key="label.trysent"/></button></td>

                                    </form>
                                </c:if>
                            </c:if>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <div class="tablePages">
                <h3>|||</h3>
                <c:forEach begin="1" end="${sessionScope.get('pagesAll')}" var="number">
                    <a href="controller?command=pagesOfPayments&page=${number}&type=${sort}">${number}</a>
                </c:forEach>
                <h3>|||</h3>
            </div>
        </div>
    </section>
</body>
</html>
