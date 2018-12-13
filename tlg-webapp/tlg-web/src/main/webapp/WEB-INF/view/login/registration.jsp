<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<page:general-template>
    <jsp:attribute name="title"><spring:message code="login.registration"/></jsp:attribute>
    <jsp:body>
        <page:simple-template>
            <jsp:attribute name="content">
                <div class="account-content">
                    <form:form class="form-horizontal" method="post"
                               action="${pageContext.request.contextPath}/registration" modelAttribute="user">
                        <spring:bind path="name">
                        <div class="form-group m-b-20 ${status.error ? 'has-error' : ''}">
                            <div class="col-xs-12">
                                <label for="username"><spring:message code="user.name"/></label>
                                <form:input path="name" class="form-control" type="text" id="username" required=""
                                            placeholder="Name"/>
                                <form:errors path="name" cssClass="help-block"/>
                            </div>
                        </div>
                        </spring:bind>
                        <spring:bind path="surname">
                        <div class="form-group m-b-20 ${status.error ? 'has-error' : ''}">
                            <div class="col-xs-12">
                                <label for="username"><spring:message code="user.surname"/></label>
                                <form:input path="surname" class="form-control" type="text" id="surname" required=""
                                            placeholder="Surname"/>
                                <form:errors path="surname" cssClass="help-block"/>
                            </div>
                        </div>
                        </spring:bind>
                        <spring:bind path="email">
                        <div class="form-group m-b-20 ${status.error ? 'has-error' : ''}">
                            <div class="col-xs-12">
                                <label for="emailaddress"><spring:message code="user.email"/></label>
                                <form:input path="email" class="form-control" type="email" id="emailaddress" required=""
                                            placeholder="your-email@mail.com"/>
                                <form:errors path="email" cssClass="help-block"/>
                            </div>
                        </div>
                        </spring:bind>
                        <spring:bind path="password">
                        <div class="form-group m-b-20 ${status.error ? 'has-error' : ''}">
                            <div class="col-xs-12">
                                <label for="password"><spring:message code="user.password"/></label>
                                <form:input path="password" class="form-control" type="password" required=""
                                            id="password" placeholder="Enter your password"/>
                                <form:errors path="password" cssClass="help-block"/>
                            </div>
                        </div>
                        </spring:bind>
                        <spring:bind path="confirmPassword">
                        <div class="form-group m-b-20 ${status.error ? 'has-error' : ''}">
                            <div class="col-xs-12">
                                <label for="password"><spring:message code="user.confirm.password"/></label>
                                <form:input path="confirmPassword" class="form-control" type="password" required=""
                                            id="confirmPassword" placeholder="Confirm your password"/>
                                <form:errors path="confirmPassword" cssClass="help-block"/>
                            </div>
                        </div>
                        </spring:bind>
                        <div class="form-group account-btn text-center m-t-10">
                            <div class="col-12">
                                <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message
                                        code="sign.up"/></button>
                            </div>
                        </div>

                    </form:form>

                    <div class="row m-t-30">
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}" class="btn btn-lg btn-primary btn-block"
                               type="submit"><spring:message code="back.to.home"/></a>
                        </div>
                    </div>

                    <div class="clearfix"></div>

                </div>
            </jsp:attribute>
        </page:simple-template>
    </jsp:body>
</page:general-template>
