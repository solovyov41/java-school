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

<c:set var="driversItem"><spring:message code="drivers.item"/></c:set>

<c:set var="adminRole" value="<%=Role.ADMIN.name()%>"/>

<c:set var="btnCreate"><spring:message code="button.create"/></c:set>
<c:set var="btnView"><spring:message code="button.view"/></c:set>
<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>


<page:general-template>
    <jsp:attribute name="title">${driversItem}</jsp:attribute>

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
                                <h4 class="m-b-10 header-title">${driversItem}</h4>
                            </div>
                        </div>
                        <div class="row justify-content-between">
                            <div class="col-1">
                                <form action="${pageCtx}/manager/driver/new"
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
                                        <th><spring:message code="driver.personal.number"/></th>
                                        <th><spring:message code="user.surname"/></th>
                                        <th><spring:message code="driver.status"/></th>
                                        <th><spring:message code="city.current"/></th>
                                        <th><spring:message code="driver.vehicle"/></th>
                                        <th><spring:message code="driver.carriage"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="driver" items="${drivers}">
                                        <tr>
                                            <th scope="row">
                                                <a href="${pageCtx}/manager/driver/${driver.personalNum}">
                                                    <c:out value="${driver.personalNum}"/>
                                                </a>
                                            </th>
                                            <td><c:out value="${driver.user.surname}"/></td>
                                            <td><spring:message code="${driver.status.messageCode()}"/></td>
                                            <td><c:out value="${driver.city.name}"/></td>
                                            <td><a href="${pageCtx}/manager/vehicle/${driver.vehicle.licPlateNum}">
                                                <c:out value="${driver.vehicle.licPlateNum}"/></a></td>
                                            <td><a href="${pageCtx}/manager/carriage/${driver.carriage.uniqueNumber}"
                                                <c:out value="${driver.carriage.uniqueNumber}"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!--end row -->
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





