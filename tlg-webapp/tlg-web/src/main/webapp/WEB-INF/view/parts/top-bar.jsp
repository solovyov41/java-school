<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Top Bar Start -->
<nav class="navbar navbar-expand-lg navbar-dark" style="z-index: 99; background-color: #353535;">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/account">TLG Web</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/logout"><i class="fa fa-power-off" aria-hidden="true"></i>
                    <spring:message code="login.logout"/> <span class="sr-only">(current)</span></a>
            </li>
        </ul>

    </div>
</nav>
<!-- Top Bar End -->