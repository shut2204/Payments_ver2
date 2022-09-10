<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${locale}" dir="ltr">

<head>
    <meta charset="utf-8">
    <title>Login on site</title>
    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>

<body>

<div class="form-container">
    <img src="img/bgImg.jpg" alt="" class="bg__img">
    <form class="form-box" action="controller" method="post">

        <img src="img/user.png" alt="" class="user">
        <h1><fmt:message key="label.Welcome"/></h1>
        <p style="color: red">${errorLogin}<p/>
                       ${infoRegister}
        <input type="hidden" value="login" name="command">

        <input type="text" name="login" placeholder="<fmt:message key="label.your-login"/>">

        <input type="password" name="password" placeholder="<fmt:message key="label.pass"/>">

        <input type="submit" name="" value="<fmt:message key="label.login"/>">

        <a href="registration.jsp" class="forgot__link"><fmt:message key="label.register"/></a>

    </form>
</div>

</body>
</html>
