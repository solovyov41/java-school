<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="elements" tagdir="/WEB-INF/tags/elements" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="vehicleItem"><spring:message code="vehicle.item"/></c:set>

<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>


<page:general-template>
    <jsp:attribute name="title">${vehicleItem}</jsp:attribute>

    <jsp:attribute name="scripts">
      <script src="${pageCtx}/assets/js/main.js"></script>
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
                            <%--@elvariable id="vehicle" type="com.tlg.core.dto.VehicleDto"--%>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="m-b-20 header-title">${vehicleItem}</h3>
                            </div>
                        </div>
                            <%--Buttons--%>
                        <c:if test="${vehicle.carriage == null}">
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                        <a href="${pageCtx}/manager/vehicle/edit/${vehicle.licPlateNum}"
                                           class="btn btn-primary" role="button">${btnEdit}</a>
                                        <a href="${pageCtx}/manager/vehicle/delete/${vehicle.licPlateNum}"
                                           class="btn btn-default" role="button">${btnDelete}</a>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="row">
                            <div class="col-6">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th scope="row"><spring:message code="vehicle.licence.plate"/></th>
                                        <td><c:out value="${vehicle.licPlateNum}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="vehicle.load.capacity"/></th>
                                        <td><c:out value="${vehicle.loadCapacity}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="vehicle.seats.number"/></th>
                                        <td><c:out value="${vehicle.passSeatsNum}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="vehicle.state"/></th>
                                        <td><spring:message code="${vehicle.state.messageCode()}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="city.current"/></th>
                                        <td><c:out value="${vehicle.city.name}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="drivers.item"/></th>
                                        <td><c:forEach var="driver" items="${vehicle.coDrivers}">
                                            <p><a href="${pageCtx}/manager/driver/${driver.personalNum}">
                                                <c:out value="${driver.personalNum}"/></a></p>
                                        </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="carriage.item"/></th>
                                        <td><a href="${pageCtx}/manager/carriage/${vehicle.carriage.uniqueNumber}">
                                            <c:out value="${vehicle.carriage.uniqueNumber}"/></a></td>
                                    </tr>
                                    </tbody>
                                </table>
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





