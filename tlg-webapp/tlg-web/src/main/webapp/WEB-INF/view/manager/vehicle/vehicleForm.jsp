<%@ page import="com.tlg.core.entity.enums.VehicleState" %>

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

<c:set var="vehicleItem"><spring:message code="vehicle.item"/></c:set>
<c:set var="vehicleStates" value="<%=VehicleState.values()%>"/>
<c:set var="yandexKey"><spring:message code="yandex.api.key"/></c:set>


<page:general-template>
    <jsp:attribute name="title">${vehicleItem}</jsp:attribute>
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
                                <h3 class="m-b-20 header-title">${vehicleItem}</h3>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <form:form class="form-horizontal" role="form" method="post"
                                           accept-charset="UTF-8"
                                           action="${pageCtx}/manager/vehicle${edit == true ? '/edit' : ''}"
                                           modelAttribute="vehicle">
                                    <spring:bind path="licPlateNum">
                                        <div class="form-group row">
                                            <label for="licPlateNum" class="col-4 col-form-label">
                                                <spring:message code="vehicle.licence.plate"/>
                                            </label>
                                            <div class="col-8">
                                                <c:choose>
                                                    <c:when test="${edit==false}">
                                                        <form:input path="licPlateNum" class="form-control"
                                                                    type="text" id="licPlateNum"
                                                                    required="true"
                                                                    placeholder="Licence Number (AB12345)"/>
                                                        <form:errors path="licPlateNum" cssClass="help-block"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:input path="licPlateNum"
                                                                    class="form-control-plaintext"
                                                                    type="text" id="licPlateNum"
                                                                    readonly="true"
                                                                    value="${vehicle.licPlateNum}"/>
                                                        <form:errors path="licPlateNum" cssClass="help-block"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="state">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label" for="vehicleStateSelect">
                                                <spring:message code="vehicle.state"/>
                                            </label>
                                            <div class="col-8">
                                                <form:select path="state" class="form-control" id="vehicleStateSelect">
                                                    <form:option value="${vehicle.state}" selected="true">
                                                        <spring:message code="${vehicle.state.messageCode()}"/>
                                                    </form:option>
                                                    <c:forEach items="${vehicleStates}" var="vehicleState">
                                                        <c:if test="${vehicleState != vehicle.state}">
                                                            <form:option value="${vehicleState}">
                                                                <spring:message code="${vehicleState.messageCode()}"/>
                                                            </form:option>
                                                        </c:if>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="state" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="loadCapacity">
                                        <div class="form-group row">
                                            <label for="loadCapacity" class="col-4 col-form-label">
                                                <spring:message code="vehicle.load.capacity"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="loadCapacity" class="form-control" type="text"
                                                            id="loadCapacity" required="true"
                                                            placeholder="in tons"/>
                                                <form:errors path="loadCapacity" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="passSeatsNum">
                                        <div class="form-group row">
                                            <label for="passSeatsNum" class="col-4 col-form-label">
                                                <spring:message code="vehicle.seats.number"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="passSeatsNum" class="form-control" type="text"
                                                            id="passSeatsNum" required="true" placeholder="2"/>
                                                <form:errors path="passSeatsNum" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="city.name">
                                        <div class="form-group row">
                                            <label for="cityName" class="col-4 col-form-label">
                                                <spring:message code="city.item"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="city.name" class="form-control" type="text"
                                                            id="cityName" required="true"
                                                            placeholder="City Name"/>
                                                <form:errors path="city" cssClass="help-block"/>
                                                <p id="notice">City not found </p>
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
                                    <div class="form-group row">
                                        <div class="col-12">
                                            <button type="submit" class="btn btn-primary">
                                                <spring:message code="button.save"/></button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                                <%--Yandex Map--%>
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





