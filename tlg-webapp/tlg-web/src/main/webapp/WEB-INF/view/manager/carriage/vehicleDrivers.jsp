<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="dashboardItem"><spring:message code="dashboard.item"/></c:set>
<c:set var="carriagesItem"><spring:message code="carriages.item"/></c:set>

<c:set var="carriagesToDo"><spring:message code="carriages.todo"/></c:set>
<c:set var="carriagesInWork"><spring:message code="carriages.inwork"/></c:set>

<c:set var="uniqueNumber"><spring:message code="carriage.uniqueNumber"/></c:set>
<c:set var="isDone"><spring:message code="carriage.isDone"/></c:set>
<c:set var="customerName"><spring:message code="carriage.customerName"/></c:set>
<c:set var="initiateDate"><spring:message code="carriage.initiateDate"/></c:set>
<c:set var="finishDate"><spring:message code="carriage.finishDate"/></c:set>

<c:set var="btnAssign"><spring:message code="button.assign"/></c:set>


<page:general-template>
    <jsp:attribute name="title">${dashboardItem}</jsp:attribute>

    <jsp:attribute name="scripts">
      <script src="${pageCtx}/assets/js/main.js"></script>
      <script src="${pageCtx}/assets/js/custom/drivers-assignment.js"></script>
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
                            <div class="col">
                                <h4 class="m-b-10 header-title">${carriagesItem}</h4>
                                    <%--@elvariable id="carriage" type="com.tlg.core.dto.CarriageDto"--%>
                                <form:form
                                        action="${pageCtx}/manager/carriage/${carriage.uniqueNumber}/vehicle"
                                        method="post" modelAttribute="carriage">
                                    <table class="table" id="v-d-assign-table">
                                        <thead class="thead-inverse">
                                        <tr>
                                            <th></th>
                                            <th><spring:message code="vehicle.licence.plate"/></th>
                                            <th><spring:message code="vehicle.load.capacity"/></th>
                                            <th><spring:message code="vehicle.seats.number"/></th>
                                            <th><spring:message code="city.item"/></th>
                                            <th><spring:message code="vehicle.distance.shipment.city"/></th>
                                            <th><spring:message code="drivers.item"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <%--@elvariable id="vehicleDriversMap" type="java.util.Map"--%>
                                        <c:forEach var="vehicle" items="${vehicleDriversMap.keySet()}"
                                                   varStatus="vLoop">
                                            <tr class="vehicleCheck">
                                                <spring:bind path="vehicle.licPlateNum">
                                                    <td><form:radiobutton path="vehicle.licPlateNum"
                                                                          value="${vehicle.licPlateNum}"/></td>
                                                </spring:bind>
                                                <td><c:out value="${vehicle.licPlateNum}"/></td>
                                                <td><c:out value="${vehicle.loadCapacity}"/></td>
                                                <td class="passSeatsNum"><c:out value="${vehicle.passSeatsNum}"/></td>
                                                <td><c:out value="${vehicle.city.name}"/></td>
                                                <td><c:out value="${vehicle.distanceToCityOrderShipment}"/></td>
                                                <td>
                                                    <c:forEach var="driver"
                                                               items="${vehicleDriversMap.get(vehicle)}"
                                                               varStatus="loop">
                                                        <spring:bind path="drivers"><p>
                                                            <form:checkbox path="drivers[${loop.index}].personalNum"
                                                                           id="vehicle[${vLoop.index}].drivers[${loop.index}].personalNum"
                                                                           class="driverCheck"
                                                                           value="${driver.personalNum}"/>
                                                            <form:label path="drivers[${loop.index}].personalNum"
                                                                        for="vehicle[${vLoop.index}].drivers[${loop.index}].personalNum">
                                                                <c:out value="${driver.user.surname} ${driver.user.name}"/>
                                                            </form:label>
                                                        </p></spring:bind>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="form-group">
                                        <button class="btn btn-primary btn-lg" type="submit">${btnAssign}</button>
                                    </div>
                                </form:form>
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





