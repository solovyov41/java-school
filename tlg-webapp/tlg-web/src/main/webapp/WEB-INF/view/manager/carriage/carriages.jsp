<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="dashboardItem"><spring:message code="dashboard.item"/></c:set>
<c:set var="carriagesItem"><spring:message code="carriages.item"/></c:set>

<c:set var="carriagesToDo"><spring:message code="carriages.todo"/></c:set>
<c:set var="carriagesInWork"><spring:message code="carriages.inwork"/></c:set>

<c:set var="isDone"><spring:message code="carriage.isDone"/></c:set>

<c:set var="btnView"><spring:message code="button.view"/></c:set>
<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnCreate"><spring:message code="button.create"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>


<page:general-template>
    <jsp:attribute name="title">${dashboardItem}</jsp:attribute>

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
                        <div class="row ">
                            <div class="col-12">
                                <h4 class="m-b-10 header-title">${carriagesItem}</h4>
                            </div>
                        </div>
                        <div class="row justify-content-between">
                                <div class="col-1">
                                    <form action="${pageCtx}/manager/carriage/new"
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
                            <div class="col-lg-12">
                                <table id="info-table" class="table table-striped">
                                    <thead class="thead-inverse">
                                    <tr>
                                        <th><spring:message code="carriage.uniqueNumber"/></th>
                                        <th><spring:message code="carriage.customerName"/></th>
                                        <th><spring:message code="carriage.initiateDate"/></th>
                                        <th><spring:message code="carriage.finishDate"/></th>
                                        <th><spring:message code="carriage.isDone"/></th>
                                        <th><spring:message code="vehicle.item"/></th>
                                        <th><spring:message code="drivers.item"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="carriage" items="${OrderList}">
                                        <tr>
                                            <th scope="row"><a
                                                    href="${pageCtx}/manager/carriage/${carriage.uniqueNumber}">
                                                <c:out value="${carriage.uniqueNumber}"/></a></th>
                                            <td><c:out value="${carriage.customerName}"/></td>
                                            <td><fmt:formatDate value="${carriage.initiateDate}"
                                                                pattern="dd.MM.yyyy HH:mm"/></td>
                                            <td><fmt:formatDate value="${carriage.finishDate}"
                                                                pattern="dd.MM.yyyy HH:mm"/></td>
                                            <td><spring:message code="${carriage.status.messageCode()}"/></td>
                                            <td>
                                                <a href="${pageCtx}/manager/vehicle/${carriage.vehicle.licPlateNum}">
                                                    <c:out value="${carriage.vehicle.licPlateNum}"/></a></td>
                                            <td><c:forEach var="driver" items="${carriage.vehicle.coDrivers}">
                                                <p><a href="${pageCtx}/manager/driver/${driver.personalNum}">
                                                    <c:out value="${driver.personalNum}"/></a></p>
                                            </c:forEach></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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





