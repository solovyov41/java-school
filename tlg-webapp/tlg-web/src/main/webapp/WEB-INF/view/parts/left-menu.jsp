<%@ page import="com.tlg.core.entity.enums.Role" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="dashboardItem"><spring:message code="dashboard.item"/></c:set>
<c:set var="usersItem"><spring:message code="users.item"/></c:set>
<c:set var="carriagesItem"><spring:message code="carriages.item"/></c:set>
<c:set var="driversItem"><spring:message code="drivers.item"/></c:set>
<c:set var="vehiclesItem"><spring:message code="vehicles.item"/></c:set>


<c:set var="driverRole" value="<%=Role.DRIVER.name()%>"/>
<c:set var="adminRole" value="<%=Role.ADMIN.name()%>"/>
<c:set var="managerRole" value="<%=Role.DISPATCHER.name()%>"/>

<!--left navigation start-->
<aside class="sidebar-navigation">
    <div class="scrollbar-wrapper">
        <div>
            <!-- User Detail box -->
            <div class="user-details">
                <div class="pull-left">
                    <img src="${pageContext.request.contextPath}/assets/img/users/avatar-1.jpg" alt=""
                         class="thumb-md img-circle">
                </div>
                <div class="user-info">
                    <a href="${pageContext.request.contextPath}/account/profile">
                        <sec:authentication property="name"/>
                    </a>
                    <p class="text-muted m-0">
                        <sec:authentication property="principal.authorities" var="authorities"/>
                        <c:forEach items="${authorities}" var="authority">
                            ${authority.authority}
                        </c:forEach>
                    </p>
                </div>
            </div>
            <!--- End User Detail box -->

            <!-- Left Menu Start -->
            <ul class="metisMenu nav" id="side-menu">
                <li><a href="${pageContext.request.contextPath}/account/dashboard">
                    <i class="fa fa-home" aria-hidden="true"></i> ${dashboardItem}</a></li>
                <sec:authorize access="hasAuthority('${adminRole}')">
                    <li><a href="${pageContext.request.contextPath}/account/users">
                        <i class="fa fa-users" aria-hidden="true"></i> ${usersItem}</a></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('${managerRole}')">
                    <li><a href="${pageContext.request.contextPath}/manager/carriage">
                        <i class="fa fa-tasks" aria-hidden="true"></i> ${carriagesItem}</a></li>
                    <li><a href="${pageContext.request.contextPath}/manager/driver">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i> ${driversItem}</a></li>
                    <li><a href="${pageContext.request.contextPath}/manager/vehicle">
                        <i class="fa fa-truck" aria-hidden="true"></i> ${vehiclesItem}</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div><!--Scrollbar wrapper-->
</aside>
<!--left navigation end-->

