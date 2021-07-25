<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FD - <fmt:message key="ui.title.register"/></title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="registration" class="section">
    <div class="container tagline">
        <em><fmt:message key="ui.header.registerUser"/></em><br/>
        <em>${sessionScope.registrationStatus}</em>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="register_user"/>

            <label for="username"><fmt:message key="ui.username"/></label>
            <input type="text" pattern="^[a-zA-Z0-9-_\.]{4,30}$" minlength="4" maxlength="30"
                   name="username" id="username" required>
            <br/>
            <label for="password"><fmt:message key="ui.label.password"/></label>
            <input type="password" pattern="[a-zA-Z0-9]{4,40}" minlength="4"
                   maxlength="40" name="password" id="password" required>
            <br/>
            <label for="fname"><fmt:message key="ui.firsName"/></label>
            <input type="text" pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,50}$" minlength="2" maxlength="50"
                   name="fname" id="fname" required>
            <br/>
            <label for="lname"><fmt:message key="ui.lastName"/></label>
            <input type="text"pattern="^[A-ZА-Я][a-zA-Zа-яА-Я-]{1,50}$"  minlength="2" maxlength="50"
                   name="lname" id="lname" required>
            <br/>
            <label for="phone"><fmt:message key="ui.phone"/></label>
            <input type="tel" pattern="^[\+][0-9]{5,21}$" name="phone"  id="phone" required>
            <br/>
            <label for="email"><fmt:message key="ui.email"/></label>
            <input type="email" pattern= "^[-\w._]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$" maxlength="50" name="email" id="email" required>
            <br/>
            <input type="submit" value='<fmt:message key="ui.login.button.submit"/>' id="submit">
        </form>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>



