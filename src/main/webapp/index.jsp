<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="jsp/error.jsp" isErrorPage="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="ui"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FlyingDutchman - <fmt:message key="ui.title.main"/></title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="jsp/headerForMainPage.jsp"/>

<section id="history" class="section">
    <div class="container">
        <div class="text-content">
            <h2 class="headline"><fmt:message key="ui.headline.companyHistory"/></h2>
            <p><fmt:message key="ui.history.firstPart"/></p>
            <p><fmt:message key="ui.history.secondPart"/></p>
            <p><fmt:message key="ui.history.thirdPart"/></p>
        </div>
    </div>
</section>

<section id="products" class="section">
    <header class="imageheader"></header>
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.products"/></h2>
        <p><fmt:message key="ui.productsDescription"/></p>
    </div>
    <ul class="product-list">
        <li class="product-item">
            <img class="product-image" src="images/img1.jpg" alt="Food1-photo Octopus tentacles">
            <h2 class="product-name">Octopus tentacles</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img2.jpg" alt="Food2-photo Tiger prawns grilled">
            <h2 class="product-name">Tiger prawns grilled</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img3.jpg" alt="Food3-photo Tuna grilled">
            <h2 class="product-name">Tuna grilled</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img4.jpg" alt="Food4-photo Squid grilled">
            <h2 class="product-name">Squid grilled</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img5.jpg" alt="Chilean mussels in wine sauce - Product Photo">
            <h2 class="product-name">Chilean mussels</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img6.jpg" alt="Giant mussels baked in dough - Product Photo">
            <h2 class="product-name">Giant mussels baked in dough</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img7.jpg" alt="Cod fillet in cream sauce - Product Photo">
            <h2 class="product-name">Cod fillet in cream sauce</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img8.jpg" alt="Cappuccino coffee - Product Photo">
            <h2 class="product-name">Cappuccino coffee</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img9.jpg" alt="Americano coffee - Product Photo">
            <h2 class="product-name">Americano coffee</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img12.jpg" alt="Caesar salad - Product Photo">
            <h2 class="product-name">Caesar salad</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img13.jpg" alt="Mashed potatoes - Product Photo">
            <h2 class="product-name">Mashed potatoes</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img14.jpg" alt="Basmati rice - Product Photo">
            <h2 class="product-name">Basmati rice</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img10.jpg" alt="Green tea - Product Photo">
            <h2 class="product-name">Green tea</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img11.jpg"
                 alt="Black tea - Product Photo">
            <h2 class="product-name">Black tea</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img15.jpg" alt="Soup Tam-Yam - Product Photo">
            <h2 class="product-name">Soup Tam-Yam</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img16.jpg" alt="Beer Krušovice - Product Photo">
            <h2 class="product-name">OBeer Krušovice</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img7.jpg" alt="Cod fillet in cream sauce - Product Photo">
            <h2 class="product-name">Cod fillet in cream sauce</h2>
        </li>
        <li class="product-item">
            <img class="product-image" src="images/img1.jpg" alt="Food1-photo Octopus tentacles">
            <h2 class="product-name">Octopus tentacles</h2>
        </li>
    </ul>
</section>

<section id="guarantee" class="section">
    <header class="imageheader"></header>
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.headline.guarantee"/></h2>
        <p><fmt:message key="ui.guarantee.firstPart"/></p>
        <p><fmt:message key="ui.guarantee.secondPart"/></p>
    </div>
</section>

<section id="people" class="section">
    <header class="imageheader"></header>
    <div class="container">
        <h2 class="headline"><fmt:message key="ui.people"/></h2>
        <div class="people-cards">
            <div class="person-card">
                <img src="images/employees/HenryTwill.jpg" alt="HenryTwill Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.henryTeill"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.henryTeill.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.henryTeill.about"/></p>
            </div>

            <div class="person-card">
                <img src="images/employees/JessicaNewton.jpg" alt="JessicaNewton Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.jessicaNewton"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.jessicaNewton.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.jessicaNewton.about"/></p>
            </div>

            <div class="person-card">
                <img src="images/employees/PhiTang.jpg" alt="PhiTang Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.phiTang"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.phiTang.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.phiTang.about"/></p>
            </div>

            <div class="person-card">
                <img src="images/employees/MariaSontas.jpg" alt="MariaSontas Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.mariaSontas"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.mariaSontas.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.mariaSontas.about"/></p>
            </div>

            <div class="person-card">
                <img src="images/employees/AngelaHaston.jpg" alt="AngelaHaston Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.angelaHashton"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.angelaHashton.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.angelaHashton.about"/></p>
            </div>

            <div class="person-card">
                <img src="images/employees/MichaelLewiston.jpg" alt="MichaelLewiston Photo">
                <div class="card-info">
                    <h3 class="card-name"><fmt:message key="ui.people.michaelLewiston"/></h3>
                    <h4 class="card-title"><fmt:message key="ui.people.michaelLewiston.position"/></h4>
                </div>
                <p class="card-text"><fmt:message key="ui.people.michaelLewiston.about"/></p>
            </div>
        </div>
    </div>
</section>

<jsp:include page="jsp/searchSection.jsp"/>
<jsp:include page="jsp/footer.jsp"/>

</body>
</html>
