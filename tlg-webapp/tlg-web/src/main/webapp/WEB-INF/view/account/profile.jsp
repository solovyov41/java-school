<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<page:general-template>

    <jsp:attribute name="styles"> <link href="${pageContext.request.contextPath}/assets/css/custom/profile.css"
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
                            <div class="col-12">
                                <h4 class="m-b-20 header-title"><spring:message code="profile.item"/></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <form:form class="form-horizontal" role="form" method="post"
                                           action="${pageContext.request.contextPath}/account/profile"
                                           modelAttribute="user">
                                    <spring:bind path="name">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label">
                                                <spring:message code="user.name"/></label>
                                            <div class="col-8">
                                                <form:input path="name" class="form-control" type="text"
                                                            id="name" required="" placeholder="Name"
                                                            readonly="${roleChange}"/>
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
                                                            readonly="${roleChange}"/>
                                                <form:errors path="surname" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <spring:bind path="email">
                                        <div class="form-group row">
                                            <label class="col-4 col-form-label"><spring:message
                                                    code="user.email"/></label>
                                            <div class="col-8">
                                                <form:input path="email" class="form-control" type="text"
                                                            id="email" required=""
                                                            placeholder="example@mail.ru"
                                                            readonly="${roleChange}"/>
                                                <form:errors path="email" cssClass="help-block"/>
                                            </div>
                                        </div>
                                    </spring:bind>
                                    <div class="form-group row">
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
    </jsp:body>

</page:general-template>





