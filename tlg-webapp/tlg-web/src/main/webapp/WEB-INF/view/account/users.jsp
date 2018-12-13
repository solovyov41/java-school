<%@ page import="com.tlg.core.entity.enums.Role" %>
<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="usersItem"><spring:message code="users.item"/></c:set>
<c:set var="adminRole" value="<%=Role.ADMIN.name()%>"/>

<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnCreate"><spring:message code="button.create"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>


<page:general-template>

    <jsp:attribute name="title">${usersItem}</jsp:attribute>

    <jsp:attribute name="scripts">
      <script src="${pageCtx}/assets/js/main.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div id="page-wrapper">

            <jsp:include page="../parts/top-bar.jsp"/>
            <jsp:include page="../parts/left-menu.jsp"/>

            <!-- Page content start -->
            <div class="page-contentbar">

                <!-- START PAGE CONTENT -->
                <div id="page-right-content">

                    <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                <h4 class="m-b-20 header-title">${usersItem}</h4>

                                <table class="table">
                                    <thead class="thead-inverse">
                                    <tr>
                                        <th><spring:message code="user.name"/></th>
                                        <th><spring:message code="user.surname"/></th>
                                        <th><spring:message code="user.email"/></th>
                                        <th><spring:message code="user.role"/></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${users}">
                                        <tr>
                                            <th scope="row"><c:out value="${user.name}"/></th>
                                            <td><c:out value="${user.surname}"/></td>
                                            <td><c:out value="${user.email}"/></td>
                                            <td><spring:message code="${user.role.messageCode()}"/></td>
                                            <sec:authorize access="hasAuthority('${adminRole}')">
                                                <td>
                                                    <form action="${pageCtx}/account/users/edit"
                                                          method="get">
                                                        <button class="btn btn-primary" type="submit"
                                                                name="email" value="${user.email}">${btnEdit}</button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form action="${pageCtx}/account/users/delete"
                                                          method="get">
                                                        <button class="btn btn-secondary" type="submit" name="email"
                                                                value="${user.email}">${btnDelete}</button>
                                                    </form>
                                                </td>
                                            </sec:authorize>
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





