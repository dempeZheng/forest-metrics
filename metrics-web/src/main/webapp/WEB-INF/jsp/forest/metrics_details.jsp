<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Forest管理后台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Font Awesome -->
    <%--<link rel="stylesheet" href="/libs/css/font-awesome.min.css">--%>
    <%--<!-- Ionicons -->--%>
    <link rel="stylesheet" href="/libs/css/ionicons.min.css">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.css">
    <%--<!-- Ionicons -->--%>
    <%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">--%>
    <!-- Theme style -->
    <link rel="stylesheet" href="/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/dist/css/skins/_all-skins.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .content {
            min-height: 50px;
            padding: 0px;
            margin-right: auto;
            margin-left: auto;
            padding-left: 15px;
            padding-right: 15px;
        }
    </style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../header.jsp"/>
    <jsp:include page="../siderbar.jsp"/>
    <div class="content-wrapper">

        <section class="content" style="min-height: 20px">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box  box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title">监控详情：serviceName/${param.uri}</h3>
                            <div class="box-tools pull-right">
                            </div><!-- /.box-tools -->
                        </div>
                        <div class="box-body">
                            <div id="toolbar">

                                <div class="form-inline">
                                    <div class="form-group">
                                        <span>时间段:</span>
                                        <input type="datetime">


                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <span>维度:</span>
                                        <select></select>


                                    </div>
                                    <br>
                                    <button id="ok" type="submit" class=" form-control btn btn-primary">查询</button>
                                    <button id="refresh" type="submit" class=" form-control btn btn-info"><i class="fa-refresh"></i>刷新</button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box  box-solid">
                        <div class="box-body">
                            <div id="countContainer"
                                 style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box  box-solid">
                        <div class="box-body">
                            <div id="timeContainer"
                                 style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-8">
                    <div class="box  box-solid">
                        <div class="box-body">
                            <div id="timeDistributionContainer"
                                 style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="box  box-solid">
                        <div class="box-body">
                            <div id="codesContainer"
                                 style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </div>
    <jsp:include page="../footer.jsp"/>
    <jsp:include page="../control-siderbar.jsp"/>

    <div class="control-sidebar-bg"></div>
</div>

<!-- jQuery 2.2.0 -->
<script src="/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/dist/js/app.min.js"></script>

<!-- AdminLTE for demo purposes -->
<script src="/dist/js/demo.js"></script>
<%--<script src="/dist/js/pages/dashboard.js"></script>--%>
<jsp:include page="../common/script.jsp"/>
<script>

    $('#ok').click(function () {
        $('#table').bootstrapTable('refresh');
    });

    $(document).ready(function () {

        var options = {
            chart: {
                renderTo: 'countContainer',
                type: 'spline'
            },
            exporting:{
                enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            credits: {
                enabled: false
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    second:'%H:%M:%S'
                }


            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            series: [{}]
        };

        var timeOptions = {
            chart: {
                renderTo: 'timeContainer',
                type: 'spline'
            },
            exporting:{
                enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            credits: {
                enabled: false
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime',
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            series: [{}]
        };
        var timeDistributionOptions = {
            chart: {
                renderTo: 'timeDistributionContainer',
                type: 'column'
            },
            credits: {
                enabled: false
            },
            exporting:{
                enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            series: [{}]
        };
        var codesOptions = {
            chart: {
                renderTo: 'codesContainer',
                type: 'pie'
            },

            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            credits: {
                enabled: false
            },
            exporting:{
                enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            title: {
                text: ''
            },

            yAxis: {
                title: {
                    text: ''
                }
            },
            series: [{}]
        };

        $.getJSON('/metric/listByUri?uri=hello', function (data) {
            options.series = data.count;
            options.title.text = "请求数";
            var chart = new Highcharts.Chart(options);

            timeOptions.series = data.time;
            timeOptions.title.text = "时延";
            var chart = new Highcharts.Chart(timeOptions);

            timeDistributionOptions.series = data.timeDistribution;
            timeDistributionOptions.title.text = "时延分布";
            var chart = new Highcharts.Chart(timeDistributionOptions);

            codesOptions.series = data.codes;
            codesOptions.title.text = "状态码比例分布";
            var chart = new Highcharts.Chart(codesOptions);
        });

    });

</script>

</body>
</html>
