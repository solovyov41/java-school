<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="content" fragment="true" %>
<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="wrapper-page">
                    <div class="m-t-10 card-box">
                        <div class="text-center">
                            <h3 class="text-uppercase m-t-0 m-b-20">
                                <a href="${pageContext.request.contextPath}">
                                    <span>TLG</span>
                                </a>
                            </h3>
                        </div>
                        <jsp:invoke fragment="content"/>
                    </div>
                    <!-- end card-box-->
                </div>
                <!-- end wrapper -->
            </div>
        </div>
    </div>
</section>
<!-- END HOME -->