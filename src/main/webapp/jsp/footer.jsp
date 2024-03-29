<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="customtag" prefix="mytag" %>
<c:if test="${sessionScope.language != null}">
  <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<footer class="footer">
  <div class="container">
    <nav class="nav" role="navigation">
      <div class="container nav-elements">
        <div class="branding">
          <a href="#home"><img src="images/logo.jpg"
                               alt="Logo"></a>
          <p class="address">
            <fmt:message key="ui.streetAddress"/>
            <br>
            <fmt:message key="ui.cityAddress"/>
          </p>
        </div>
      </div>
      <mytag:role-tag/>
    </nav>
    <p class="legal"><fmt:message key="ui.footer.legalNote"/></p>
  </div>
</footer>