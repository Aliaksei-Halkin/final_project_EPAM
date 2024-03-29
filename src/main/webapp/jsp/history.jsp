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
    <title>FD -<fmt:message key="ui.title.myOrders"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="orders" class="section">
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.orderHistory"/></h2>
        <c:if test="${numberOfItems > 0}">
            <fmt:message key="ui.totalOrders"/> ${numberOfItems}

            <jsp:include page="paginator.jsp"/>

            <table id="orderHistory">
                <tr>
                    <th><fmt:message key="ui.orderNo"/></th>
                    <th><fmt:message key="ui.orderId"/></th>
                    <th><fmt:message key="ui.orderDate"/></th>
                    <th><fmt:message key="ui.products"/></th>
                    <th><fmt:message key="ui.cost"/></th>
                    <th><fmt:message key="ui.orderStatus"/></th>
                </tr>
                <c:forEach items="${orders}" var="order" varStatus="counter">
                    <tr>
                        <td>${counter.count + index}</td>
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
                                        <td><img width="100px" height="75px"
                                                 src="<c:out value="${product.key.productImgPath}"/>"></td>
                                        <td> x ${product.value}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td>$<c:out value="${order.orderCost}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status=='APPROVED'}">
                                    <fmt:message key="ui.orderStatusConfirmed"/>
                                </c:when>
                                <c:when test="${order.status=='COOKED'}">
                                    <fmt:message key="ui.orderStatusCooked"/>
                                </c:when>
                                <c:when test="${order.status=='CLOSED'}">
                                    <fmt:message key="ui.orderStatusClosed"/>
                                </c:when>
                                <c:when test="${order.status=='NEW'}">
                                    <fmt:message key="ui.orderStatus.pending"/>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="remove_order">
                                        <input type="hidden" name="page" value="${page}"/>
                                        <input type="hidden" name="order" value="${order.id}">
                                        <input type="submit" value='<fmt:message key="ui.removeFromOrders"/>'>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <p>Undefined</p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="paginator.jsp"/>

            <br/>
        </c:if>
        <c:if test="${numberOfItems == 0}">
            <p><fmt:message key="ui.emptyHistory"/></p>
        </c:if>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
