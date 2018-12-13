<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<page:general-template>
    <jsp:attribute name="title"><spring:message code="login.login"/></jsp:attribute>
    <jsp:body>
        <page:simple-template>
            <jsp:attribute name="content">
                <div class="account-content">
                    <form class="form-horizontal"
                          action="<c:url value="/login"/>"
                          method="post">

                        <div class="form-group m-b-20">
                            <div class="col-12">
                                <label for="emailaddress"><spring:message code="user.email"/></label>
                                <input class="form-control" type="email"
                                       id="emailaddress" required="" name="email" value="${param.email}"
                                       placeholder="example@gmail.com">
                            </div>
                        </div>

                        <div class="form-group m-b-20">
                            <div class="col-12">
                                <label for="password">Password</label>
                                <input class="form-control" type="password" required=""
                                       id="password" placeholder="Enter your password" name="password">
                            </div>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <spring:message code="login.error"/>
                        </div>
	                	</c:if>
                        <c:if test="${param.logout != null}">
                        <div class="alert alert-info alert-dismissible fade in" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <spring:message code="logout.successful"/>
                        </div>
	                	</c:if>
                        <div class="form-group account-btn text-center m-t-10">
                            <div class="col-12">
                                <button class="btn btn-lg btn-primary btn-block"
                                        type="submit"><spring:message code="sign.in"/>
                                </button>
                            </div>
                        </div>

                    </form>
                    <div class="row m-t-30">
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}/registration" class="btn btn-lg btn-primary btn-block">
                                <spring:message code="sign.up"/></a>
                        </div>
                    </div>
                    <div class="row m-t-30">
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}" class="btn btn-lg btn-secondary btn-block">
                                <spring:message code="back.to.home"/></a>
                        </div>
                    </div>

                    <div class="clearfix"></div>

                </div>
            </jsp:attribute>
        </page:simple-template>
    </jsp:body>
</page:general-template>
