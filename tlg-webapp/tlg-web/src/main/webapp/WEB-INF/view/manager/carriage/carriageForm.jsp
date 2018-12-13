<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="carriageItem"><spring:message code="carriage.item"/></c:set>
<c:set var="yandexKey"><spring:message code="yandex.api.key"/></c:set>

<page:general-template>
    <jsp:attribute name="title">${carriageItem}</jsp:attribute>


    <jsp:attribute name="styles">
        <link href="${pageCtx}/assets/css/custom/geomap.css" rel="stylesheet">
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="${pageCtx}/assets/js/main.js"></script>
        <script src="https://api-maps.yandex.ru/2.1/?apikey=${yandexKey}&lang=en_RU"
                type="text/javascript">
        </script>
        <script src="${pageCtx}/assets/js/custom/waypoints_geocoding.js"></script>
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
                        <div class="row">
                            <div class="col-sm-12">
                                <h3 class="m-b-20 header-title">${carriageItem}</h3>
                            </div>
                        </div>
                            <%--End of page title--%>

                            <%--@elvariable id="carriage" type="com.tlg.core.dto.CarriageDto"--%>
                        <form:form id="mainForm" class="form-horizontal" role="form" method="post"
                                   action="${pageCtx}/manager/carriage${edit ? '/edit' : ''}"
                                   modelAttribute="carriage">
                            <div class="row">
                                <div class="col-6">
                                    <spring:bind path="uniqueNumber">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="carriage.uniqueNumber"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="uniqueNumber" class="form-control" type="text"
                                                            id="uniqueNumber" readonly="true"/>
                                                <form:errors path="uniqueNumber" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="customerName">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="carriage.customerName"/>
                                            </label>
                                            <div class="col-8">
                                                <form:input path="customerName" class="form-control" type="text"
                                                            id="customerName" required="true"
                                                            placeholder="Customer Name"/>
                                                <form:errors path="customerName" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="status">
                                        <form:input path="status" class="form-control" type="hidden"
                                                    id="status"/>
                                        <form:errors path="status" cssClass="help-block"/>
                                    </spring:bind>
                                    <div class="form-group row">
                                        <div id="YMapsID"></div>
                                    </div>
                                </div>
                                    <%--Route inputs--%>
                                <div class="col-6">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="m-b-10 header-title"><spring:message code="carriage.route"/></h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12">
                                            <div id="route" style="display:${edit || hasError ? 'none' : 'block'}">
                                                <div id="waypoints">
                                                    <spring:bind path="waypoints">
                                                        <c:forEach items="${carriage.waypoints}" var="point"
                                                                   varStatus="loop">
                                                            <div class="form-group input-group">
                                                                <spring:bind path="waypoints[${loop.index}].city">
                                                                    <form:input items="${point.city.name}"
                                                                                path="waypoints[${loop.index}].city.name"
                                                                                class="form-control waypoint city-name"
                                                                                type="text"
                                                                                id="waypoints[${loop.index}].city.name"
                                                                                required="true"/>
                                                                </spring:bind>
                                                                <spring:bind path="waypoints[${loop.index}].city">
                                                                    <form:hidden items="${point.city.latitude}"
                                                                                 path="waypoints[${loop.index}].city.latitude"
                                                                                 class="form-control waypoint latitude"
                                                                                 id="waypoints[${loop.index}].city.latitude"/>
                                                                </spring:bind>
                                                                <spring:bind path="waypoints[${loop.index}].city">
                                                                    <form:hidden items="${point.city.longitude}"
                                                                                 path="waypoints[${loop.index}].city.longitude"
                                                                                 class="form-control waypoint longitude"
                                                                                 id="waypoints[${loop.index}].city.longitude"/>
                                                                </spring:bind>
                                                                <spring:bind path="waypoints[${loop.index}].position">
                                                                    <form:hidden items="${point.position}"
                                                                                 path="waypoints[${loop.index}].position"
                                                                                 class="form-control waypoint position"
                                                                                 id="waypoints[${loop.index}].position"/>
                                                                </spring:bind>
                                                                <div class="input-group-append">
                                                                    <!-- Buttons -->
                                                                    <button class="btn btn-outline-secondary deleteBtn"
                                                                            style="display:none">
                                                                        <i class="fa fa-times" aria-hidden="true"></i>
                                                                    </button>
                                                                    <button class="btn btn-outline-primary addBtn">
                                                                        <i class="fa fa-plus" aria-hidden="true"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </spring:bind>
                                                </div>
                                                <div class="form-group row">
                                                    <button id="save_route" type="button" class="btn btn-primary">
                                                        <spring:message code="button.save"/>
                                                    </button>
                                                </div>
                                            </div>
                                            <div id="route_fixed"
                                                 style="display:${!(edit  || hasError)? 'none' : 'block'}">
                                                <table class="table">
                                                    <tbody id="route_table">
                                                    <c:forEach items="${carriage.waypoints}" var="point"
                                                               varStatus="loop">
                                                        <tr>
                                                            <td><c:out value="${loop.index+1}"/></td>
                                                            <td><c:out value="${point.city.name}"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                                <button type="button" class="btn btn-primary"
                                                        data-toggle="modal" data-target="#exampleModalCenter">
                                                    <spring:message code="button.edit"/>
                                                </button>
                                                    <%--Modal window for route edit--%>
                                                <div class="modal fade" id="exampleModalCenter" tabindex="-1"
                                                     role="dialog" aria-labelledby="exampleModalCenterTitle"
                                                     aria-hidden="true">
                                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLongTitle">
                                                                    <spring:message code="route.edit"/></h5>
                                                                <button type="button" class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <spring:message code="route.edit.alert"/>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary"
                                                                        data-dismiss="modal"><spring:message
                                                                        code="button.cancel"/></button>
                                                                <button id="edit_route" type="button"
                                                                        class="btn btn-primary"><spring:message
                                                                        code="button.edit"/></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%--End of top row with info map and route--%>
                            <div class="row">
                                <div class="col-12">
                                    <h5 class="m-b-20 header-title"><spring:message code="cargoes.item"/></h5>
                                </div>
                                <div class="col-12">
                                    <div id="cargoes" style="display:${!(edit || hasError) ? 'none' : 'block'}">
                                        <spring:bind path="cargoes">
                                            <table class="table">
                                                <tbody>
                                                <c:forEach items="${carriage.cargoes}" var="cargo" varStatus="loop">
                                                    <tr class="cargo-row">
                                                            <%--<th scope="row" class="number-cell"><c:out--%>
                                                            <%--value="#${loop.index+1}"/></th>--%>
                                                        <td class="cargoCell">
                                                            <spring:bind path="cargoes[${loop.index}].number">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message code="cargo.number"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:input items="${cargo.number}"
                                                                                    path="cargoes[${loop.index}].number"
                                                                                    class="form-control cargo-number"
                                                                                    type="text"
                                                                                    id="cargoes[${loop.index}].number"
                                                                                    readonly="true"/>
                                                                        <form:errors
                                                                                path="cargoes[${loop.index}].number"
                                                                                cssClass="help-block"/>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind path="cargoes[${loop.index}].name">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message code="cargo.name"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:input items="${cargo.name}"
                                                                                    path="cargoes[${loop.index}].name"
                                                                                    class="form-control cargo-name"
                                                                                    type="text"
                                                                                    id="cargoes[${loop.index}].name"
                                                                                    required="required"/>
                                                                        <form:errors
                                                                                path="cargoes[${loop.index}].name"
                                                                                cssClass="help-block"/>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind path="cargoes[${loop.index}].description">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message code="cargo.description"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:input items="${cargo.description}"
                                                                                    path="cargoes[${loop.index}].description"
                                                                                    class="form-control cargo-description"
                                                                                    type="text"
                                                                                    id="cargoes[${loop.index}].description"
                                                                                    required=""/>
                                                                        <form:errors
                                                                                path="cargoes[${loop.index}].description"
                                                                                cssClass="help-block"/>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind path="cargoes[${loop.index}].weight">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message code="cargo.weight"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:input items="${cargo.weight}"
                                                                                    path="cargoes[${loop.index}].weight"
                                                                                    class="form-control cargo-weight"
                                                                                    type="text"
                                                                                    id="cargoes[${loop.index}].weight"
                                                                                    required="required"/>
                                                                        <form:errors
                                                                                path="cargoes[${loop.index}].weight"
                                                                                cssClass="help-block"/>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind
                                                                    path="cargoes[${loop.index}].departureWaypoint">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message
                                                                                code="cargo.city.departure"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:select
                                                                                path="cargoes[${loop.index}].departureWaypoint.position"
                                                                                class="custom-select departure city-name cargo-departure"
                                                                                id="cargoes[${loop.index}].departureWaypoint.position">
                                                                            <form:option value="-1">
                                                                                <spring:message
                                                                                        code="cargo.city.not.selected"/>
                                                                            </form:option>
                                                                            <c:forEach items="${carriage.waypoints}"
                                                                                       var="waypoint">
                                                                                <c:if test="${waypoint.position != carriage.waypoints.size()-1}">
                                                                                    <form:option
                                                                                            value="${waypoint.position}"
                                                                                            selected="${waypoint.position == cargo.departureWaypoint.position ? true : false}">
                                                                                        <c:out value="${waypoint.position+1}. ${waypoint.city.name}"/>
                                                                                    </form:option>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </form:select>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                            <spring:bind
                                                                    path="cargoes[${loop.index}].destinationWaypoint">
                                                                <div class="form-group row">
                                                                    <label class="col-4 col-form-label">
                                                                        <spring:message
                                                                                code="cargo.city.destination"/>
                                                                    </label>
                                                                    <div class="col-8">
                                                                        <form:select
                                                                                path="cargoes[${loop.index}].destinationWaypoint.position"
                                                                                class="custom-select destination city-name cargo-destination"
                                                                                id="cargoes[${loop.index}].destinationWaypoint.position">
                                                                            <form:option value="-1">
                                                                                <spring:message
                                                                                        code="cargo.city.not.selected"/>
                                                                            </form:option>
                                                                            <c:forEach items="${carriage.waypoints}"
                                                                                       var="waypoint">
                                                                                <c:if test="${waypoint.position != 0}">
                                                                                    <form:option
                                                                                            value="${waypoint.position}"
                                                                                            selected="${waypoint.position == cargo.destinationWaypoint.position ? true : false}">
                                                                                        <c:out value="${waypoint.position+1}. ${waypoint.city.name}"/>
                                                                                    </form:option>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </form:select>
                                                                    </div>
                                                                </div>
                                                            </spring:bind>
                                                        </td>
                                                        <td class="btn-cell">
                                                            <div class="btn-group" role="group">
                                                                <!-- Buttons -->
                                                                <button type="button"
                                                                        class="btn btn-secondary deleteCrgBtn"
                                                                        style="display:${carriage.cargoes.size() > 1 ? 'block': 'none'}">
                                                                    <i class="fa fa-times" aria-hidden="true"></i>
                                                                </button>
                                                                <button type="button"
                                                                        class="btn btn-primary addCrgBtn">
                                                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </spring:bind>

                                        <div class="form-group row">
                                            <form:button type="submit" class="btn btn-primary">
                                                <spring:message code="button.save"/>
                                            </form:button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%--End of cargoes row--%>
                        </form:form>
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





