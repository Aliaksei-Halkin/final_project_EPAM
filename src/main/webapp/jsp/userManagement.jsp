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
    <title>FD - <fmt:message key="ui.title.userManagement"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="orders" class="section">
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.listOfUsers"/></h2>
        <br/>
        <em>${sessionScope.statusUserOperation}</em>
        ${sessionScope.statusUserOperation = null}
        <br/>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="register_user_page"/>
            <input type="submit" value='<fmt:message key="ui.header.addNewUser"/>'/>
        </form>
        <c:if test="${numberOfItems > 0}">
            <fmt:message key="ui.totalUsers"/> ${numberOfItems}

            <jsp:include page="paginator.jsp"/>

            <table id="orderHistory">
                <tr>
                    <th><fmt:message key="ui.no"/></th>
                    <th><fmt:message key="ui.username"/></th>
                    <th><fmt:message key="ui.firsName"/></th>
                    <th><fmt:message key="ui.lastName"/></th>
                    <th><fmt:message key="ui.phone"/></th>
                    <th><fmt:message key="ui.email"/></th>
                    <th><fmt:message key="ui.userRole"/></th>
                    <th><fmt:message key="ui.active"/></th>
                    <th><fmt:message key="ui.changeRole"/></th>
                    <th><fmt:message key="ui.manageUser"/></th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <td>${counter.count + index}</td>
                        <td><c:out value="${user.userName}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.phoneNumber}"/></td>
                        <td><c:out value="${user.eMail}"/></td>
                        <td>
                            <c:if test="${user.userRole == 1}"><fmt:message key="ui.role.admin"/></c:if>
                            <c:if test="${user.userRole == 2}"><fmt:message key="ui.role.manager"/></c:if>
                            <c:if test="${user.userRole == 3}"><fmt:message key="ui.role.customer"/></c:if>
                            <c:if test="${user.userRole == 4}"><fmt:message key="ui.role.cook"/></c:if>
                        </td>
                        <td><c:out value="${user.active}"/></td>
                        <td>
                            <c:if test="${user.userRole != 1}">
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="change_user">
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="hidden" name="user" value="${user.userName}">
                                    <label>
                                        <fmt:message key="ui.userRoleChange"/>
                                        <br/>
                                        <c:if test="${user.userRole != 2}">
                                            <label>
                                                <input type="radio" id="choiceRole" name="newRole" value="2">
                                                <fmt:message key="ui.role.manager"/>
                                            </label>
                                            <br/>
                                        </c:if>
                                        <c:if test="${user.userRole != 3}">
                                            <label>
                                                <input type="radio" id="choiceRole" name="newRole" value="3">
                                                <fmt:message key="ui.role.customer"/>
                                            </label>
                                            <br/>
                                        </c:if>
                                        <c:if test="${user.userRole != 4}">
                                            <label>
                                                <input type="radio" id="choiceRole" name="newRole" value="4">
                                                <fmt:message key="ui.role.cook"/>
                                            </label>
                                            <br/>
                                        </c:if>
                                        <input type="submit" value='<fmt:message key="ui.confirm"/>' id="submit">
                                    </label>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${user.userRole != 1}">
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="delete_user">
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="hidden" name="user" value="${user.userName}">
                                    <input type="submit" value='<fmt:message key="ui.deleteUser"/>'>
                                </form>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="update_user">
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="hidden" name="user" value="${user.userName}">
                                    <input type="submit" value='<fmt:message key="ui.updateUser"/>'>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br/>

            <jsp:include page="paginator.jsp"/>

        </c:if>
        <c:if test="${numberOfItems == 0}">
            <p><fmt:message key="ui.emptyUserList"/></p>
        </c:if>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
