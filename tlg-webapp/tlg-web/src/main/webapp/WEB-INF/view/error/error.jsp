<!doctype html>

<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="errorItem"><spring:message code="error.item"/></c:set>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<page:general-template>
    <jsp:attribute name="title">${errorItem}</jsp:attribute>
    <jsp:body>
        <page:simple-template>
        <jsp:attribute name="content">


    <!--CONTACT US AREA-->
    <section class="error-area section-padding">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-lg-8 col-md-offset-2 col-lg-offset-2 col-sm-12 col-xs-12">
                    <div class="error-content text-center">
                        <img src="${pageCtx}/assets/img/error.png" alt="">
                        <h3>${messagetitle}</h3>
                        <h2>${message}</h2>
                        <a href="${pageCtx}" class="read-more"
                           type="submit">Back To Home</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

                    </jsp:attribute>
        </page:simple-template>

        <!--CONTACT US AREA END-->

    </jsp:body>
</page:general-template>

</html>
