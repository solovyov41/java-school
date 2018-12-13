<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Top Bar Start -->
<div class="topbar">

    <!-- LOGO -->
    <div class="topbar-left">
        <div class="">
            <a href="${pageContext.request.contextPath}/account" class="logo logo-lg">
                TLG Web
            </a>
        </div>
    </div>

    <!-- Top navbar -->
    <div class="navbar navbar-default" role="navigation">
        <div class="container justify-content-end">
            <div class="">
                <!-- Top nav Right menu -->
                <ul class="nav navbar-nav navbar-right top-navbar-items-right pull-right">

                    <li class="dropdown top-menu-item-xs">
                        <a href="" class="dropdown-toggle menu-right-item profile" data-toggle="dropdown"
                           aria-expanded="true">
                            <img src="${pageContext.request.contextPath}/assets/img/users/avatar-1.jpg" alt="user-img"
                                 class="img-circle">
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/account/profile"><i
                                    class="ti-user m-r-10"></i> <spring:message code="profile.item"/></a></li>
                            <li class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/logout"><i class="ti-power-off m-r-10"></i>
                                <spring:message code="login.logout"/></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div> <!-- end container -->
    </div> <!-- end navbar -->
</div>
<!-- Top Bar End -->