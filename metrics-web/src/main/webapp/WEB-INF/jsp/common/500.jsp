<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Metrics</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <jsp:include page="../common/style.jsp"></jsp:include>
</head>

<body class="hold-transition skin-blue-light sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <!-- =============================================== -->

    <!-- Left side column. contains the sidebar -->
    <jsp:include page="../siderbar.jsp"/>

    <!-- =============================================== -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 500</h2>
            </div>
            <!-- /.error-page -->
        </section>
    </div>
    <!-- /.content-wrapper -->

    <jsp:include page="../footer.jsp"/>

    <!-- Control Sidebar -->
    <jsp:include page="../control-siderbar.jsp"/>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.0 -->
<script src="/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<!-- AdminLTE App -->
<script src="/dist/js/app.min.js"></script>
<%--<script src="/dist/js/pages/dashboard2.js"></script>--%>


<script src="/bootstrap-table/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/js/commons/live/screen_channel.js"></script>


</body>
</html>
