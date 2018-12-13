<!DOCTYPE html>
<%@ tag description="Admin Dashboard" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@attribute name="users" required="true" type="java.util.List" %>

<c:set var="newUsers"><spring:message code="users.new"/></c:set>

<h5 class="m-b-5 header-title">${newUsers}</h5>
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
            <td>
                <form action="${pageContext.request.contextPath}/account/users/delete/${user.email}}"
                      method="get">
                    <button class="btn btn-secondary" type="submit">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

