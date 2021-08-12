<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FD <fmt:message key="ui.title.editProduct"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="orders" class="section">
    <div class="container">
        <c:if test="${sessionScope.registrationStatus != null}">
            <h1 class="custom-colored-h1"><em>${sessionScope.registrationStatus}</em></h1>
            ${sessionScope.registrationStatus=null}
        </c:if>
        <h2 class="headline"><fmt:message key="ui.header.editUser"/></h2>
        <form action="controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="edit_user"/>
            <input type="hidden" name="page" value="${page}"/>
            <input type="hidden" name="username" value="${user.userName}">

            <label for="password"><fmt:message key="ui.label.password"/></label>
            <input type="password" pattern="[a-zA-Z0-9]{4,40}" minlength="4"
                   maxlength="40" name="password" id="password">
            <br/>
            <label for="fname"><fmt:message key="ui.firsName"/></label>
            <input type="text" pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,50}$" minlength="2" maxlength="50"
                   name="fname" id="fname" value="${user.firstName}">
            <br/>
            <label for="lname"><fmt:message key="ui.lastName"/></label>
            <input type="text" pattern="^[A-ZА-Я][a-zA-Zа-яА-Я-]{1,50}$" minlength="2" maxlength="50"
                   name="lname" id="lname" value="${user.lastName}">
            <br/>
            <label for="phone"><fmt:message key="ui.phone"/></label>
            <input type="tel" pattern="^[\+][0-9]{5,21}$" name="phone" id="phone" value="${user.phoneNumber}">
            <br/>
            <label for="email"><fmt:message key="ui.email"/></label>
            <input type="email" pattern="^[-\w._]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$" maxlength="50" name="email"
                   id="email" value="${user.eMail}">
            <br/>
            <label for="active"><fmt:message key="ui.active"/></label> <br/>
            <input type="radio" id="active2" name="active" value="false"><fmt:message key="ui.activeFalse"/> <br/>
            <input type="radio" id="active" name="active" value="true" checked><fmt:message key="ui.activeTrue"/><br/>
            <br/>
            <input type="submit" value='<fmt:message key="ui.confirm"/>' id="submit">
        </form>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
