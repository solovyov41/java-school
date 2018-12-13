<!DOCTYPE html>
<%@ tag description="Dispatcher Dashboard" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@attribute name="notAssignOrderList" required="true" type="java.util.List" %>
<%@attribute name="inWorkOrderList" required="true" type="java.util.List" %>


<c:set var="carriagesToDo"><spring:message code="carriages.todo"/></c:set>
<c:set var="carriagesInWork"><spring:message code="carriages.inwork"/></c:set>

<c:set var="uniqueNumber"><spring:message code="carriage.uniqueNumber"/></c:set>
<c:set var="isDone"><spring:message code="carriage.isDone"/></c:set>
<c:set var="customerName"><spring:message code="carriage.customerName"/></c:set>
<c:set var="initiateDate"><spring:message code="carriage.initiateDate"/></c:set>
<c:set var="finishDate"><spring:message code="carriage.finishDate"/></c:set>

<c:set var="btnView"><spring:message code="button.view"/></c:set>
<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>
<c:set var="btnCreate"><spring:message code="button.create"/></c:set>
<c:set var="btnDelete"><spring:message code="button.delete"/></c:set>

<div class="row">
    <div class="col-6">
        <h5 class="m-b-5 header-title">${carriagesToDo}</h5>
        <table class="table table-striped">
            <thead class="thead-inverse">
            <tr>
                <th scope="col">${uniqueNumber}</th>
                <th scope="col">${customerName}</th>
                <th scope="col">${initiateDate}</th>
                <th scope="col">${finishDate}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="carriage" items="${notAssignOrderList}">
                <tr>
                    <th scope="row"><a
                            href="${pageContext.request.contextPath}/manager/carriage/${carriage.uniqueNumber}">
                        <c:out value="${carriage.uniqueNumber}"/></a></th>
                    <td><c:out value="${carriage.customerName}"/></td>
                    <td><c:out value="${carriage.initiateDate}"/></td>
                    <td><c:out value="${carriage.finishDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-6">
        <h5 class="m-b-5 header-title">${carriagesInWork}</h5>
        <table class="table table-striped">
            <thead class="thead-inverse">
            <tr>
                <th scope="col">${uniqueNumber}</th>
                <th scope="col">${customerName}</th>
                <th scope="col">${initiateDate}</th>
                <th scope="col">${finishDate}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="carriage" items="${inWorkOrderList}">
                <tr>
                    <th scope="row"><a
                            href="${pageContext.request.contextPath}/manager/carriage/${carriage.uniqueNumber}">
                        <c:out value="${carriage.uniqueNumber}"/> </a></th>
                    <td><c:out value="${carriage.customerName}"/></td>
                    <td><c:out value="${carriage.initiateDate}"/></td>
                    <td><c:out value="${carriage.finishDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

