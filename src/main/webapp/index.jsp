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
<c:if test="${sessionScope.get('customer').getRole().equals('user') || sessionScope.get('customer').getRole().equals('user') == null}">
    <%@ include file="/jspf/nav.jspf"%>
</c:if>
<c:if test="${sessionScope.get('customer').getRole().equals('admin')}">
    <%@include file="/jspf/navAdmin.jspf"%>
</c:if>
    


<section></section>
</body>
</html>
