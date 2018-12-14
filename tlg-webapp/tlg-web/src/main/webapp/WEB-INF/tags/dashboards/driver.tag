<%@ tag description="Driver Dashboard" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@ taglib prefix="elements" tagdir="/WEB-INF/tags/elements" %>

<%@ tag import="com.tlg.core.entity.enums.DriverStatus" %>

<%@attribute name="driver" required="true" type="com.tlg.core.dto.DriverDto" %>
<%@attribute name="allInShift" required="true" type="java.lang.Boolean" %>
<%@attribute name="firstUnvisited" required="false" type="java.lang.Integer" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="uniqueNumber"><spring:message code="carriage.uniqueNumber"/></c:set>
<c:set var="isDone"><spring:message code="carriage.isDone"/></c:set>
<c:set var="customerName"><spring:message code="carriage.customerName"/></c:set>
<c:set var="initiateDate"><spring:message code="carriage.initiateDate"/></c:set>
<c:set var="finishDate"><spring:message code="carriage.finishDate"/></c:set>

<c:set var="btnEdit"><spring:message code="button.edit"/></c:set>

<c:set var="REST" value="<%=DriverStatus.REST.name()%>"/>
<c:set var="IN_SHIFT" value="<%=DriverStatus.IN_SHIFT.name()%>"/>
<c:set var="DRIVE" value="<%=DriverStatus.DRIVE.name()%>"/>

<div class="row">
    <%--Driver info--%>
    <div class="col-6">
        <table class="table">
            <tbody>
            <tr>
                <th scope="row"><spring:message code="driver.personal.number"/></th>
                <td><c:out value="${driver.personalNum}"/></td>
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
                <td>
                    <c:out value="${driver.carriage.vehicle.licPlateNum}"/></td>
            </tr>
            <tr>
                <th scope="row"><spring:message code="driver.carriage"/></th>
                <td><c:out value="${driver.carriage.uniqueNumber}"/></td>
            </tr>
            <tr>
                <th scope="row"><spring:message code="carriage.codrivers"/></th>
                <td><c:forEach var="codriver" items="${driver.carriage.drivers}">
                    <c:if test="${driver.personalNum != codriver.personalNum}">
                        <p><c:out value="${codriver.user.surname} ${codriver.user.name} - "/>
                            <spring:message code="${codriver.status.messageCode()}"/></p>

                    </c:if>
                </c:forEach></td>
            </tr>
            </tbody>
        </table>
        <c:if test="${driver.carriage != null}">
            <%--@elvariable id="driver" type="com.tlg.core.dto.DriverDto"--%>
            <form:form role="form" method="post" action="${pageCtx}/driver/changeStatus"
                       modelAttribute="driver">
                <c:choose>
                    <c:when test="${driver.status == REST}">
                        <form:input path="status" id="status.start" value="${IN_SHIFT}" type="hidden"/>
                        <form:button type="submit" class="btn btn-primary">
                            <spring:message code="button.start"/>
                        </form:button>
                    </c:when>
                    <c:when test="${driver.status == IN_SHIFT && allInShift}">
                        <form:input path="status" id="status.drive" value="${DRIVE}" type="hidden"/>
                        <form:button type="submit" class="btn btn-primary">
                            <spring:message code="button.drive"/>
                        </form:button>
                    </c:when>
                    <c:when test="${driver.status == DRIVE && allInShift}">
                        <form:input path="status" id="status.switch" value="${IN_SHIFT}" type="hidden"/>
                        <form:button type="submit" class="btn btn-primary">
                            <spring:message code="button.transfer.control"/>
                        </form:button>
                    </c:when>
                    <c:otherwise>
                        <form:label path="status"><spring:message code="driver.wait.codrivers"/></form:label>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </c:if>
    </div>

    <c:if test="${driver.carriage != null}">
        <%--Route info--%>
        <div class="col-6">
            <div class="card">
                <div class="card-header" style="alignment:center">
                    <strong>
                        <spring:message code="carriage.route"/>
                    </strong>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach items="${driver.carriage.waypoints}" var="waypoint" varStatus="loop">
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-10">
                                    <c:out value="#${waypoint.position+1}. ${waypoint.city.name}"/>
                                </div>

                                    <%--@elvariable id="firstUnvisited" type="int"--%>
                                    <%--@elvariable id="allInShift" type="boolean"--%>
                                <c:if test="${allInShift && waypoint.position == firstUnvisited}">
                                    <div class="col-2">
                                        <a class="btn btn-success"
                                           href="${pageCtx}/driver/delivery/${driver.carriage.uniqueNumber}/arrival/${waypoint.position}"
                                           role="button"><i class="fa fa-check" aria-hidden="true"></i></a>
                                    </div>
                                </c:if>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>
</div>

<c:if test="${driver.carriage != null}">
    <%--Cargoes info--%>
    <div class="row">
        <div class="col-12">
            <h4 class="m-b-20 header-title"><spring:message code="cargoes.item"/></h4>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <table class="table">
                <tbody>
                <c:forEach items="${driver.carriage.cargoes}" var="cargo" varStatus="loop">
                    <tr>
                        <th scope="row" style="width:5%"><c:out value="#${loop.index+1}"/></th>
                        <td><elements:cargo-table cargo="${cargo}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>