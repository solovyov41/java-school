<%@ page import="com.tlg.core.entity.enums.Role" %>
<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="pageCtx" value="${pageContext.request.contextPath}"/>

<c:set var="adminRole" value="<%=Role.ADMIN%>"/>
<c:set var="userRoles" value="<%=Role.values()%>"/>

<page:general-template>

    <jsp:attribute name="styles"> <link href="${pageCtx}/assets/css/custom/profile.css"
                                        media="all" rel="stylesheet"
                                        type="text/css"/>
    </jsp:attribute>

    <jsp:attribute name="title"><spring:message code="profile.item"/></jsp:attribute>

    <jsp:body>
        <div id="page-wrapper">

            <jsp:include page="../parts/top-bar.jsp"/>
            <jsp:include page="../parts/left-menu.jsp"/>

            <div class="page-contentbar">
                <div id="page-right-content">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                <h4 class="m-b-20 header-title"><spring:message code="profile.item"/></h4>
                                <div class="row">

                                    <form:form class="form-horizontal" role="form" method="post"
                                               action="${pageCtx}/account/users/edit/"
                                               modelAttribute="user">
                                        <div class="col-md-6">
                                            <spring:bind path="name">
                                                <div class="form-group row">
                                                    <label class="col-4 col-form-label">
                                                        <spring:message code="user.name"/></label>
                                                    <div class="col-8">
                                                        <form:input path="name" class="form-control" type="text"
                                                                    id="name" required="" placeholder="Name"
                                                                    readonly="true"/>
                                                        <form:errors path="name" cssClass="help-block"/>
                                                    </div>
                                                </div>
                                            </spring:bind>
                                            <spring:bind path="surname">
                                                <div class="form-group row">
                                                    <label class="col-4 col-form-label">
                                                        <spring:message code="user.surname"/></label>
                                                    <div class="col-8">
                                                        <form:input path="surname" class="form-control" type="text"
                                                                    id="surname" required="" placeholder="Surname"
                                                                    readonly="true"/>
                                                        <form:errors path="surname" cssClass="help-block"/>
                                                    </div>
                                                </div>
                                            </spring:bind>
                                            <spring:bind path="email">
                                                <div class="form-group row">
                                                    <label class="col-4 col-form-label">
                                                        <spring:message code="user.email"/></label>
                                                    <div class="col-8">
                                                        <form:input path="email" class="form-control" type="text"
                                                                    id="email" required=""
                                                                    placeholder="example@mail.ru"
                                                                    readonly="true"/>
                                                        <form:errors path="email" cssClass="help-block"/>
                                                    </div>
                                                </div>
                                            </spring:bind>

                                            <spring:bind path="role">
                                                <div class="form-group row">
                                                    <label class="col-4 col-form-label">
                                                        <spring:message code="user.role"/>
                                                    </label>
                                                    <div class="col-8">
                                                        <form:select class="custom-select" path="role">
                                                            <form:option value="${user.role}" selected="true">
                                                                <spring:message code="${user.role.messageCode()}"/>
                                                            </form:option>
                                                            <c:forEach items="${userRoles}" var="role">
                                                                <c:if test="${role != user.role && role != adminRole}">
                                                                    <form:option value="${role}">
                                                                        <spring:message code="${role.messageCode()}"/>
                                                                    </form:option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </form:select>
                                                        <form:errors path="role" cssClass="help-block"/>
                                                    </div>
                                                </div>
                                            </spring:bind>

                                            <button type="submit" class="btn btn-primary">
                                                <spring:message code="button.save"/></button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>

</page:general-template>





