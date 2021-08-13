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
    <title>FD - <fmt:message key="ui.title.orderManagement"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="orders" class="section">
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.listOfOrders"/></h2>
        <c:if test="${numberOfItems > 0}">
            <fmt:message key="ui.totalOrders"/> ${numberOfItems}

            <jsp:include page="paginator.jsp"/>

            <table id="orderHistory">
                <tr>
                    <th><fmt:message key="ui.orderNo"/></th>
                    <th><fmt:message key="ui.username"/></th>
                    <th><fmt:message key="ui.orderId"/></th>
                    <th><fmt:message key="ui.orderDate"/></th>
                    <th><fmt:message key="ui.products"/></th>
                    <th><fmt:message key="ui.cost"/></th>
                    <th><fmt:message key="ui.orderStatus"/></th>
                </tr>
                <c:forEach items="${orders}" var="order" varStatus="counter">
                    <tr>
                        <td>${counter.count + index}</td>
                        <td><c:out value="${order.userName}"/></td>
                        <td>ID${order.id}</td>
                        <td>
                            <fmt:parseDate value="${order.orderDateTime}" pattern="yyyy-MM-dd'T'HH:mm:ss"
                                           var="parsedDateTime" type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${ parsedDateTime }"/>
                        </td>
                        <td>
                            <table>
                                <c:forEach items="${order.listOfProducts}" var="product">
                                    <tr>
                                        <td><c:out value="${product.key.name}"/></td>
                                        <td> x ${product.value}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td>$<c:out value="${order.orderCost}"/></td>
                        <td>

                            <c:if test="${order.status=='NEW'}">
                                <fmt:message key="ui.orderStatus.pending"/><br/>
                            </c:if>
                            <c:if test="${order.status=='APPROVED'}">
                                <fmt:message key="ui.orderStatus.approved"/><br/>
                            </c:if>
                            <c:if test="${order.status=='COOKED'}">
                                <fmt:message key="ui.orderStatus.cooked"/><br/>
                            </c:if>
                            <c:if test="${order.status=='NEW' && sessionScope.userRole == 2}">
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="change_order_status">
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="hidden" name="order" value="${order.id}">
                                    <input type="hidden" name="status" value="CLOSED">
                                    <input type="submit" value='<fmt:message key="ui.close"/>'>
                                </form>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="change_order_status">
                                    <input type="hidden" name="page" value="${page}">
                                    <input type="hidden" name="order" value="${order.id}">
                                    <input type="hidden" name="status" value="APPROVED">
                                    <input type="submit" value='<fmt:message key="ui.approve"/>'>
                                </form>
                                <br/>
                            </c:if>

                            <c:if test="${order.status=='COOKED' && sessionScope.userRole == 2 }">
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="change_order_status">
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="hidden" name="order" value="${order.id}">
                                    <input type="hidden" name="status" value="CLOSED">
                                    <input type="submit" value='<fmt:message key="ui.close"/>'>
                                </form>
                            </c:if>

                            <c:if test="${order.status=='APPROVED' && sessionScope.userRole == 4}">
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="change_order_status">
                                    <input type="hidden" name="page" value="${page}">
                                    <input type="hidden" name="order" value="${order.id}">
                                    <input type="hidden" name="status" value="COOKED">
                                    <input type="submit" value='<fmt:message key="ui.orderStatusCooked"/>'>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="paginator.jsp"/>

            <br/>
        </c:if>
        <c:if test="${numberOfItems == 0}">
            <p><fmt:message key="ui.emptyOrderList"/></p>
        </c:if>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
