<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<fmt:requestEncoding value="utf-8"/>
<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="driverItem"><spring:message code="driver.item"/></c:set>
<c:set var="yandexKey"><spring:message code="yandex.api.key"/></c:set>

<page:general-template>
    <jsp:attribute name="title">${driverItem}</jsp:attribute>
    <jsp:attribute name="styles">
        <link rel="stylesheet" href="${pageCtx}/assets/css/custom/geomap.css">
    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script src="${pageCtx}/assets/js/main.js"></script>
        <script src="https://api-maps.yandex.ru/2.1/?apikey=${yandexKey}&lang=en_RU"
                type="text/javascript">
        </script>
        <script src="${pageCtx}/assets/js/custom/geocoding.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div id="page-wrapper">

            <jsp:include page="../../parts/top-bar.jsp"/>
            <jsp:include page="../../parts/left-menu.jsp"/>

            <!-- Page content start -->
            <div class="page-contentbar">

                <!-- START PAGE CONTENT -->
                <div id="page-right-content">

                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <h3 class="m-b-20 header-title">${driverItem}</h3>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <form:form class="form-horizontal" role="form" method="post"
                                           accept-charset="UTF-8"
                                           action="${pageCtx}/manager/driver${edit == true ? '/edit' : ''}"
                                           modelAttribute="driver">
                                    <spring:bind path="personalNum">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="driver.personal.number"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="personalNum" class="form-control"
                                                            id="personalNum" readonly="true"
                                                            value="${driver.personalNum}"/>
                                                <form:errors path="personalNum" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="status">
                                        <form:hidden path="status" class="form-control"
                                                     id="status"
                                                     value="${driver.status}"/>
                                        <form:errors path="status" cssClass="help-block"/>
                                    </spring:bind>
                                    <spring:bind path="user">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="user.item"/>
                                            </label>
                                            <div class="col-8">
                                                <form:select class="custom-select" path="user.email">
                                                    <form:option value="${driver.user.email}" selected="true">
                                                        <c:out value="${driver.user.email} - ${driver.user.surname} ${driver.user.name}"/>
                                                    </form:option>
                                                    <c:forEach items="${availableUsers}" var="user">
                                                        <form:option value="${user.email}">
                                                            <c:out value="${user.email} - ${user.surname} ${user.name}"/>
                                                        </form:option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="user" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="city.name">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="city.current"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="city.name" class="form-control" type="text"
                                                            id="cityName" required="true"
                                                            placeholder="City Name"/>
                                                <form:errors path="city" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="city.latitude">
                                        <form:hidden path="city.latitude" class="form-control"
                                                     id="cityLat" required="true"/>
                                        <%--<form:errors path="city" cssClass="help-block"/>--%>
                                    </spring:bind>
                                    <spring:bind path="city.longitude">
                                        <form:hidden path="city.longitude" class="form-control"
                                                     id="cityLng" required="true"/>
                                        <%--<form:errors path="city" cssClass="help-block"/>--%>
                                    </spring:bind>
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="button.save"/></button>
                                    </div>
                                </form:form>
                            </div>
                            <div class="col-6">
                                <div id="YMapsID"></div>
                            </div>
                        </div>
                    </div>
                    <!-- end container -->
                </div>
                <!-- End #page-right-content -->
            </div>
            <!-- end .page-contentbar -->
        </div>
        <!-- End #page-wrapper -->
    </jsp:body>

</page:general-template>





