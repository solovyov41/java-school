<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>
<%@attribute name="styles" fragment="true" %>
<html lang="en">
<head>
    <!--====== USEFULL META ======-->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description"
          content="Transportation & Agency Template is a simple Smooth transportation and Agency Based Template"/>
    <meta name="author" content="">
    <meta name="_csrf" content="${_csrf.token}"/>

    <!--====== TITLE TAG ======-->
    <title>
        <jsp:invoke fragment="title"/>
    </title>

    <!--====== FAVICON ICON =======-->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.png">

    <!--====== STYLESHEETS ======-->
    <!-- Bootstrap Core CSS -->
    <link href='${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/css/bootstrap.css' rel='stylesheet' type="text/css">
    <%--<link href='${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet' type="text/css">--%>
    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/assets/css/metisMenu.min.css" rel="stylesheet">
    <!-- Icons CSS -->
    <link href="${pageContext.request.contextPath}/assets/css/icons.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/stellarnav.min.css">

    <!--====== MAIN STYLESHEETS ======-->
    <link href="${pageContext.request.contextPath}/assets/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/responsive.css" rel="stylesheet">

    <jsp:invoke fragment="styles"/>
</head>

<body>

<jsp:doBody/>
<!-- js placed at the end of the document so the pages load faster -->
<!--====== SCRIPTS JS ======-->
<script src="${pageContext.request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/popper.js/1.14.3/popper.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/metisMenu.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>

<%--<!-- App Js -->--%>
<%--<script src="${pageContext.request.contextPath}/assets/js/jquery.app.js"></script>--%>


<jsp:invoke fragment="scripts"/>
</body>
</html>
