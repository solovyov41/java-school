<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="dash" tagdir="/WEB-INF/tags/dashboards" %>

<%@ page import="com.tlg.core.entity.enums.Role" %>

<c:set var="dashboardItem"><spring:message code="dashboard.item"/></c:set>

<page:general-template>
    <jsp:attribute name="title">${dashboardItem}</jsp:attribute>

    <jsp:attribute name="scripts">
      <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div id="page-wrapper">

            <jsp:include page="../parts/top-bar.jsp"/>
            <jsp:include page="../parts/left-menu.jsp"/>

            <div class="page-contentbar">
                <div id="page-right-content">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <h4 class="m-b-20 header-title">${dashboardItem}</h4>
                            </div>
                        </div>
                        <sec:authorize access="hasAuthority('${Role.DRIVER.name()}')">
                            <dash:driver driver="${driver}" allInShift="${allInShift}"
                                         firstUnvisited="${firstUnvisited}"/>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('${Role.DISPATCHER.name()}')">
                            <dash:dispatcher notAssignOrderList="${notAssignOrderList}"
                                             inWorkOrderList="${inWorkOrderList}"/>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('${Role.ADMIN.name()}')">

                        </sec:authorize>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>

</page:general-template>





