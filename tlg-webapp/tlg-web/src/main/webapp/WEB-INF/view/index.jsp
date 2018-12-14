<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <!--====== USEFULL META ======-->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description"
          content="Transportation & Agency Template is a simple Smooth transportation and Agency Based Template"/>
    <meta name="author" content="Ilya Solovyov">

    <!--====== TITLE TAG ======-->
    <title>Transport and Logistics Company</title>

    <!--====== FAVICON ICON =======-->
    <link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/assets/img/favicon.png"/>

    <!--====== STYLESHEETS ======-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/stellarnav.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.css">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <%--<link href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css"
          rel="stylesheet">

    <!--====== MAIN STYLESHEETS ======-->
    <link href="${pageContext.request.contextPath}/assets/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/responsive.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/assets/js/modernizr-2.8.3.min.js"></script>
</head>

<body class="home-four">

<!--- PRELOADER -->
<div class="preeloader">
    <div class="preloader-spinner"></div>
</div>

<!--SCROLL TO TOP-->
<a href="#home" class="scrolltotop"><i class="fa fa-long-arrow-up"></i></a>

<!--START TOP AREA-->
<header class="top-area" id="home">
    <div class="top-area-bg" data-stellar-background-ratio="0.6"></div>
    <div class="header-top-area">
        <!--MAINMENU AREA-->
        <div class="mainmenu-area" id="mainmenu-area">
            <div class="mainmenu-area-bg"></div>
            <nav class="navbar">
                <div class="container">
                    <div class="navbar-header">
                        <a href="#home" class="navbar-brand"><img
                                src="${pageContext.request.contextPath}/assets/img/logo.png" alt="logo"></a>
                    </div>
                    <div id="main-nav" class="stellarnav">
                        <ul id="nav" class="nav navbar-nav">
                            <li><a href="${pageContext.request.contextPath}">home</a></li>
                            <li><a href="#about">Contact</a></li>
                            <li><a>My TLG<i class="fa fa-user"></i></a>
                                <sec:authorize access="isAnonymous()">
                                    <ul>
                                        <li><a href="${pageContext.request.contextPath}/login">
                                            <spring:message code="login.login"/></a></li>
                                        <li><a href="${pageContext.request.contextPath}/registration">
                                            <spring:message code="login.registration"/></a></li>
                                    </ul>
                                </sec:authorize>
                                <sec:authorize access="isAuthenticated()">
                                    <ul>
                                        <li><a href="${pageContext.request.contextPath}/account">
                                            <spring:message code="login.account"/></a></li>
                                        <li><a href="${pageContext.request.contextPath}/logout">
                                            <spring:message code="login.logout"/></a></li>
                                    </ul>
                                </sec:authorize>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        </nav>
    </div>
    <!--END MAINMENU AREA END-->
    </div>
    <!--HOME SLIDER AREA-->
    <div class="welcome-slider-area">
        <div class="welcome-single-slide slider-bg-one">
            <div class="container">
                <div class="row flex-v-center">
                    <div class="col-md-10 col-md-offset-1">
                        <div class="welcome-text text-center">
                            <h1>WE MAKE STRONGEST SERVICE ABOVE THE WORLD</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="welcome-single-slide slider-bg-two">
            <div class="container">
                <div class="row flex-v-center">
                    <div class="col-md-10 col-md-offset-1">
                        <div class="welcome-text text-center">
                            <h1>WE MAKE STRONGEST SERVICE ABOVE THE WORLD</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="welcome-single-slide slider-bg-three">
            <div class="container">
                <div class="row flex-v-center">
                    <div class="col-md-10 col-md-offset-1">
                        <div class="welcome-text text-center">
                            <h1>WE MAKE STRONGEST SERVICE ABOVE THE WORLD</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--END HOME SLIDER AREA-->
</header>
<!--END TOP AREA-->

<!--ABOUT AREA-->
<section class="about-area gray-bg section-padding">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-lg-6 col-md-offset-3 col-lg-offset-3 col-sm-12 col-xs-12">
                <div class="area-title text-center wow fadeIn">
                    <h2>welcome to carries !</h2>
                    <p>Excellence. Simply delivered. </p>
                        <p>International express deliveries; global freight forwarding by air, sea, road and rail;
                            warehousing solutions from packaging, to repairs, to storage; mail deliveries worldwide;
                            and other customized logistic services – with everything TLG does,
                            we help connect people and improve their lives. </p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
                <div class="about-history-content">
                    <img src="${pageContext.request.contextPath}/assets/img/about/about-details-2.jpg" alt="">
                </div>
            </div>
            <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
                <div class="about-history-content">
                    <h3>Our services</h3>
                    <ul>
                        <li><i class="fa fa-check"></i> Express deliveries worldwide;</li>
                        <li><i class="fa fa-check"></i> Freight forwarding with planes, trucks, ships and trains;</li>
                        <li><i class="fa fa-check"></i> Warehousing services that go beyond just storage,
                            but include everything from packaging to repairs; </li>
                        <li><i class="fa fa-check"></i> International mail deliveries;</li>
                        <li><i class="fa fa-check"></i> Customized and specialized shipping</li>
                    </ul>
                    <p>If it is about logistics, it is about TLG.</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!--ABOUT AREA END-->

<!--FOOER AREA-->
<div class="footer-area dark-bg">
    <div class="footer-area-bg"></div>
    <div class="footer-top-area wow fadeIn">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="footer-border"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-bottom-area wow fadeIn">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
                    <div class="single-footer-widget footer-about" >
                        <h3>About Us</h3>
                        <a name="about"></a>
                        <p>The best transport and logistics company - TLG</p>
                        <ul>
                            <li><i class="fa fa-phone"></i> <a href="callto:+88009876543">8-800-987-6543</a></li>
                            <li><i class="fa fa-map-marker"></i> <a href="mailto:solo-tlg@tlg.com">solo-tlg@tlg.com</a>
                            </li>
                            <li><i class="fa fa-phone"></i> SPb</li>
                        </ul>
                    </div>
                </div>
                <%--<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">--%>
                    <%--<div class="single-footer-widget list-widget">--%>
                        <%--<h3>Customer Service</h3>--%>
                        <%--<ul>--%>
                            <%--<li><a href="#">Support Forums</a></li>--%>
                            <%--<li><a href="#">Communication</a></li>--%>
                            <%--<li><a href="#">FAQS</a></li>--%>
                            <%--<li><a href="#">Privacy Policy</a></li>--%>
                            <%--<li><a href="#">Rules & Condition</a></li>--%>
                            <%--<li><a href="#">Contact Us</a></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="footer-border"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-copyright-area">
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
                    <div class="footer-copyright wow fadeIn">
                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script>
                            All rights reserved Colorlib
                        </p>
                        <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--FOOER AREA END-->


<!--====== SCRIPTS JS ======-->
<script src="${pageContext.request.contextPath}/assets/js/jquery-1.12.4.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>--%>

<!--====== PLUGINS JS ======-->
<script src="${pageContext.request.contextPath}/assets/js/jquery.easing.1.3.js"></script>
<%--<script src="${pageContext.request.contextPath}webjars/jquery/3.3.1/jquery.easing.1.3.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-migrate-1.2.1.min.js"></script>
<%--<script src="${pageContext.request.contextPath}webjars/jquery/3.3.1/jquery-migrate-1.2.1.min.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/jquery.appear.js"></script>
<%--<script src="${pageContext.request.contextPath}webjars/jquery/3.3.1/jquery.appear.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/jquery.sticky.js"></script>
<%--<script src="${pageContext.request.contextPath}webjars/jquery/3.3.1/jquery.sticky.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/stellar.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/wow.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/stellarnav.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/contact-form.js"></script>

<!--===== ACTIVE JS=====-->
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>

</html>
