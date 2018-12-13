<%@ page import="com.tlg.core.entity.enums.DriverStatus" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="elements" tagdir="/WEB-INF/tags/elements" %>
<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="driverItem"><spring:message code="driver.item"/></c:set>

<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>

<c:set var="REST" value="<%=DriverStatus.REST%>"/>

<page:general-template>
    <jsp:attribute name="title">${driverItem}</jsp:attribute>

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
                            <%--@elvariable id="driver" type="com.tlg.core.dto.DriverDto"--%>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="m-b-20 header-title">${driverItem}</h3>
                            </div>
                        </div>
                            <%--Buttons--%>
                        <c:if test="${driver.status == REST}">
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                        <a href="${pageCtx}/manager/driver/edit/${driver.personalNum}"
                                           class="btn btn-primary" role="button">${btnEdit}</a>
                                        <a href="${pageCtx}/manager/driver/delete/${driver.personalNum}"
                                           class="btn btn-default" role="button">${btnDelete}</a>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="row">
                                <%--Carriage info--%>
                            <div class="col-6">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th scope="row"><spring:message code="driver.personal.number"/></th>
                                        <td><c:out value="${driver.personalNum}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="user.surname"/></th>
                                        <td><c:out value="${driver.user.surname}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="user.name"/></th>
                                        <td><c:out value="${driver.user.name}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="driver.status"/></th>
                                        <td><spring:message code="${driver.status.messageCode()}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="city.current"/></th>
                                        <td><c:out value="${driver.city.name}"/></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="driver.vehicle"/></th>
                                        <td><a href="${pageCtx}/manager/vehicle/${driver.vehicle.licPlateNum}">
                                            <c:out value="${driver.vehicle.licPlateNum}"/></a></td>
                                    </tr>
                                    <tr>
                                        <th scope="row"><spring:message code="driver.carriage"/></th>
                                        <td><a href="${pageCtx}/manager/carriage/${driver.carriage.uniqueNumber}">
                                            <c:out value="${driver.carriage.uniqueNumber}"/></a></td>
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





