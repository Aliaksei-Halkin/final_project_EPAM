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
    <title>FD <fmt:message key="ui.title.myProfile"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="profile" class="section">
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.headline.myProfile"/></h2>
        <table id="profileTable">
            <tr>
                <td><fmt:message key="ui.username"/></td>
                <td><c:out value="${user.userName}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="ui.firsName"/></td>
                <td><c:out value="${user.firstName}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="ui.lastName"/></td>
                <td><c:out value="${user.lastName}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="ui.phone"/></td>
                <td><c:out value="${user.phoneNumber}"/></td>
            </tr>
            <tr>
                <td><fmt:message key="ui.email"/></td>
                <td><c:out value="${user.eMail}"/></td>
            </tr>
        </table>
    </div>
    <br/>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
