<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="${locale}" dir="ltr">

<head>
	<meta charset="utf-8">
	<title>Registration on site</title>
	<style>
		<%@include file="/css/style.css"%>
	</style>
	<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="form-container">
	<img src="img/bgImg.jpg" alt="" class="bg__img">
	<form class="form-box" action="controller" method="post">
		<img src="img/user.png" alt="" class="user">
		<h1><fmt:message key="label.hello"/></h1>
		<p style="color: red">${errorRegister}<p/>
		<c:if test="${!sessionScope.get('errors').isEmpty()}">
			<c:forEach var="error" items="${sessionScope.get('errors')}" >
				<p style="color: red">${error}<p/>
			</c:forEach>
		</c:if>
		<input type="hidden" name="command" value="registration">
		<input required type="text" name="name" placeholder="<fmt:message key="label.name"/>" />
		<input required type="text" name="lastname" placeholder="<fmt:message key="label.lastname"/>" />
		<input required type="text" name="login" placeholder="<fmt:message key="label.your-login"/>" />
		<input required type="password" name="pass"  placeholder="<fmt:message key="label.pass"/>" />
		<input required type="text" name="contact" placeholder="<fmt:message key="label.contact"/> **********" />

		<input type="submit" name="signup" value="<fmt:message key="label.register"/>" />
		<a href="login.jsp" class="forgot__link"><fmt:message key="label.login"/></a>
	</form>
</div>

</body>
</html>
