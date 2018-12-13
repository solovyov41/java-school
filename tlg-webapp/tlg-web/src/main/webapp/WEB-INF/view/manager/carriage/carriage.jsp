<%@ page import="com.tlg.core.entity.enums.CarriageStatus" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="elements" tagdir="/WEB-INF/tags/elements" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="carriagesItem"><spring:message code="carriage.item"/></c:set>

<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>

<c:set var="carriageStatus" value="<%=CarriageStatus.values()%>"/>
<c:set var="carriageStatusCreated" value="<%=CarriageStatus.CREATED%>"/>
<c:set var="carriageStatusInProgress" value="<%=CarriageStatus.IN_PROGRESS%>"/>


<page:general-template>
    <jsp:attribute name="title">${carriagesItem}</jsp:attribute>

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
                            <%--@elvariable id="carriage" type="com.tlg.core.dto.CarriageDto"--%>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="m-b-20 header-title">${carriagesItem}</h3>
                            </div>
                        </div>
                            <%--Buttons--%>

                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                        <c:if test="${carriage.status == carriageStatusCreated}">
                                        <a href="${pageCtx}/manager/carriage/${carriage.uniqueNumber}/vehicle"
                                           class="btn btn-primary" role="button">
                                            <spring:message code="button.vehicle"/></a>
                                        <a href="${pageCtx}/manager/carriage/edit/${carriage.uniqueNumber}"
                                           class="btn btn-default" role="button">${btnEdit}</a>
                                        </c:if>
                                        <c:if test="${carriage.status != carriageStatusInProgress}">
                                        <a href="${pageCtx}/manager/carriage/delete/${carriage.uniqueNumber}"
                                           class="btn btn-default" role="button">${btnDelete}</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                        <div class="row">
                                <%--Carriage info--%>
                            <div class="col-md-7">
                                <div class="row">
                                    <table class="table">
                                        <tbody>
                                        <tr>
                                            <th scope="row" style="width:30%"><spring:message
                                                    code="carriage.uniqueNumber"/></th>
                                            <td><c:out value="${carriage.uniqueNumber}"/></td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="carriage.isDone"/></th>
                                            <td><spring:message code="${carriage.status.messageCode()}"/></td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="carriage.customerName"/></th>
                                            <td><c:out value="${carriage.customerName}"/></td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="carriage.initiateDate"/></th>
                                            <td><c:out value="${carriage.initiateDate}"/></td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="carriage.finishDate"/></th>
                                            <td><c:out value="${carriage.finishDate}"/></td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="drivers.item"/></th>
                                            <td>
                                                <ul>
                                                    <c:forEach items="${carriage.drivers}" var="driver">
                                                        <li>
                                                            <a href="${pageCtx}/manager/driver/${driver.personalNum}">
                                                                <c:out value="${driver.personalNum}"/></a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><spring:message code="vehicle.item"/></th>
                                            <td>
                                                <a href="${pageCtx}/manager/vehicle/${carriage.vehicle.licPlateNum}">
                                                    <c:out value="${carriage.vehicle.licPlateNum}"/></a>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                                <%--Route info--%>
                            <div class="col-md-5">
                                <div class="card">
                                    <div class="card-header" style="alignment:center">
                                        <strong>
                                            <spring:message code="carriage.route"/>
                                        </strong>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <c:forEach items="${carriage.waypoints}" var="waypoint" varStatus="loop">
                                            <li class="list-group-item">
                                                <c:out value="#${waypoint.position+1}. ${waypoint.city.name}"/>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>

                            </div>
                        </div>
                            <%--Cargoes info--%>
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="m-b-20 header-title"><spring:message code="cargoes.item"/></h4>
                                <table class="table">
                                    <tbody>
                                    <c:forEach items="${carriage.cargoes}" var="cargo" varStatus="loop">
                                        <tr>
                                            <th scope="row" style="width:5%"><c:out value="#${loop.index+1}"/></th>
                                            <td><elements:cargo-table cargo="${cargo}"/></td>
                                        </tr>
                                    </c:forEach>
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





