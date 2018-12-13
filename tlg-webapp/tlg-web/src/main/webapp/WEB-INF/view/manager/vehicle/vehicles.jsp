<%@ page import="com.tlg.core.entity.enums.Role" %>
<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spting" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="vehiclesItem"><spring:message code="vehicles.item"/></c:set>

<c:set var="btnView"><spring:message code="button.view"/></c:set>
<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnCreate"><spring:message code="button.create"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>

<c:set var="adminRole" value="<%=Role.ADMIN.name()%>"/>


<page:general-template>
    <jsp:attribute name="title">${vehiclesItem}</jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="${pageCtx}/assets/js/main.js"></script>
        <script src="${pageCtx}/assets/js/custom/table-search.js"></script>
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
                                <h4 class="m-b-10 header-title">${vehiclesItem}</h4>
                            </div>
                        </div>
                        <div class="row justify-content-between">
                            <div class="col-1">
                                <form action="${pageCtx}/manager/vehicle/new"
                                      method="get">
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="submit">${btnCreate}</button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-5">
                                <input class="form-control" id="search-text" onkeyup="tableSearch()"
                                       placeholder="<spring:message code="search.item"/>" type="text">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <table id="info-table" class="table table-striped">
                                    <thead class="thead-inverse">
                                    <tr>
                                        <th><spring:message code="vehicle.licence.plate"/></th>
                                        <th><spring:message code="vehicle.load.capacity"/></th>
                                        <th><spring:message code="vehicle.seats.number"/></th>
                                        <th><spring:message code="vehicle.state"/></th>
                                        <th><spring:message code="city.item"/></th>
                                        <th><spring:message code="drivers.item"/></th>
                                        <th><spring:message code="carriage.item"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vehicle" items="${vehicles}">
                                        <tr>
                                            <th scope="row">
                                                <a href="${pageCtx}/manager/vehicle/${vehicle.licPlateNum}">
                                                    <c:out value="${vehicle.licPlateNum}"/>
                                                </a>
                                            </th>
                                            <td><c:out value="${vehicle.loadCapacity}"/></td>
                                            <td><c:out value="${vehicle.passSeatsNum}"/></td>
                                            <td><spring:message
                                                    code="${vehicle.state.messageCode()}"/></td>
                                            <td><c:out value="${vehicle.city.name}"/></td>
                                            <td>
                                                <c:forEach var="driver" items="${vehicle.coDrivers}">
                                                    <p><a href="${pageCtx}/manager/driver/${driver.personalNum}">
                                                        <c:out value="${driver.personalNum}"/></a></p>
                                                </c:forEach>
                                            </td>
                                            <td><a href="${pageCtx}/manager/carriage/${vehicle.carriage.uniqueNumber}">
                                                <c:out value="${vehicle.carriage.uniqueNumber}"/></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- end row -->
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





