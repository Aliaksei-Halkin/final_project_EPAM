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
    <title>FD <fmt:message key="ui.title.productManagement"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section id="orders" class="section">
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.listOfProducts"/></h2>
        <br/>
        <c:if test="${sessionScope.registrationStatusProduct != null}">
            <em>${sessionScope.registrationStatusProduct}</em>
            ${sessionScope.registrationStatusProduct = null}
            <br/>
        </c:if>
        <form method="get" action="controller">
            <input type="hidden" name="product" value="${product.id}">
            <input type="hidden" name="command" value="add_new_product_page">
            <input type="hidden" name="page" value="${page}"/>
            <button><fmt:message key="ui.addNewProduct"/></button>
        </form>
        <c:if test="${numberOfItems > 0}">

            <fmt:message key="ui.totalProducts"/> ${numberOfItems}
            <jsp:include page="paginator.jsp"/>

            <table id="orderHistory">
                <tr>
                    <th><fmt:message key="ui.no"/></th>
                    <th><fmt:message key="ui.productName"/></th>
                    <th><fmt:message key="ui.image"/></th>
                    <th><fmt:message key="ui.imageAddress"/></th>
                    <th id="columnToLimit"><fmt:message key="ui.description"/></th>
                    <th><fmt:message key="ui.cost"/></th>
                    <th></th>
                </tr>
                <c:forEach items="${products}" var="product" varStatus="counter">
                    <tr>
                        <td>${counter.count + index}</td>
                        <td><c:out value="${product.name}"/></td>
                        <td><img id="pic1" width="80px" height="60px" src="<c:out value="${product.productImgPath}"/>">
                        </td>
                        <td><c:out value="${product.productImgPath}"/></td>
                        <td><c:out value="${product.description}"/></td>
                        <td>$<c:out value="${product.cost}"/></td>
                        <td>
                            <form method="get" action="controller">
                                <input type="hidden" name="product" value="${product.id}">
                                <input type="hidden" name="page" value="${page}"/>
                                <input type="hidden" name="command" value="delete_product">
                                <button><fmt:message key="ui.deleteProduct"/></button>
                            </form>
                            <form method="get" action="controller">
                                <input type="hidden" name="product" value="${product.id}">
                                <input type="hidden" name="page" value="${page}"/>
                                <input type="hidden" name="command" value="edit_product_page">
                                <button><fmt:message key="ui.editProduct"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="paginator.jsp"/>

            <br/>
        </c:if>
        <c:if test="${numberOfItems == 0}">
            <p><fmt:message key="ui.emptyProductList"/></p>
        </c:if>
    </div>
</section>

<jsp:include page="searchSection.jsp"/>
<jsp:include page="footer.jsp"/>

</body>
</html>
