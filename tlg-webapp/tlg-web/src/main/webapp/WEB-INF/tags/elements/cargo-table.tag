<!DOCTYPE html>
<%@ tag description="Cargo presented in table" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="cargo" required="true" type="com.tlg.core.dto.CargoDto" %>

<table class="table">
    <tbody>
    <tr>
        <th scope="row" style="width:25%"><spring:message code="cargo.number"/></th>
        <td><c:out value="${cargo.number}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.name"/></th>
        <td><c:out value="${cargo.name}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.description"/></th>
        <td><c:out value="${cargo.description}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.state"/></th>
        <td><spring:message code="${cargo.state.messageCode()}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.weight"/></th>
        <td><c:out value="${cargo.weight}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.city.departure"/></th>
        <td><c:out value="${cargo.departureWaypoint.city.name}"/></td>
    </tr>
    <tr>
        <th scope="row"><spring:message code="cargo.city.destination"/></th>
        <td><c:out value="${cargo.destinationWaypoint.city.name}"/></td>
    </tr>
    </tbody>
</table>