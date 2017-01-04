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
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
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
                            <section class="content-header">
                                <h1>
                                    查询条件
                                    <small>监控详情</small>
                                </h1>
                                <ol class="breadcrumb">
                                    <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                                    <li><a href="#">serviceName</a></li>
                                    <li class="active">${param.uri}</li>
                                </ol>
                            </section>

                        </div>
                        <div class="box-body">
                            <div id="toolbar">
                                <div class="form-inline" role="form">
                                    <div class="control-group">
                                        <select id="ipSelect" class="form-control">
                                            <option value="">所有的ip</option>
                                            <c:forEach var="ip" items="${ips}">
                                                <option value="${ip}">${ip}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="roomSelect" class="form-control">
                                            <option value="">所有的机房</option>
                                            <c:forEach var="room" items="${rooms}">
                                                <option value="${room}">${room}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="versionSelect" class="form-control">
                                            <option value="">所有的版本</option>
                                            <c:forEach var="version" items="${versions}">
                                                <option value="${version}">${version}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" class="form-control" id="stime">
                                        <strong class="input-strong-input">至</strong>
                                        <input type="text" class="form-control" id="etime">
                                        <strong class="form-control">&nbsp;</strong>
                                        <select id="fastTime" class="form-control">
                                            <option value="">快速选择时间段...</option>
                                            <option value="30">近30分钟</option>
                                            <option value="60">近1小时</option>
                                            <option value="360">近6小时</option>
                                            <option value="720">近12小时</option>
                                            <option value="1440">近1天</option>
                                            <option value="2880">近2天</option>
                                            <option value="10080">近1周</option>
                                            <option value="43200">近1个月</option>
                                        </select>
                                        <button id="ok" type="submit" class=" form-control btn btn-primary">查询</button>
                                    </div>

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
                        <div class="box-header with-border">
                            <h3 class="box-title">请求数</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                        title="Collapse"><i class="fa fa-minus"></i></button>
                                <button class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                        title="Remove"><i class="fa fa-times"></i></button>
                            </div><!-- /.box-tools -->
                        </div><!-- /.box-header -->
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
                        <div class="box-header with-border">
                            <h3 class="box-title">时延</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                        title="Collapse"><i class="fa fa-minus"></i></button>
                                <button class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                        title="Remove"><i class="fa fa-times"></i></button>
                            </div><!-- /.box-tools -->
                        </div><!-- /.box-header -->
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
                        <div class="box-header with-border">
                            <h3 class="box-title">时延分布</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                        title="Collapse"><i class="fa fa-minus"></i></button>
                                <button class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                        title="Remove"><i class="fa fa-times"></i></button>
                            </div><!-- /.box-tools -->
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <div id="timeDistributionContainer"
                                 style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="box  box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title">返回状态码</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                        title="Collapse"><i class="fa fa-minus"></i></button>
                                <button class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                        title="Remove"><i class="fa fa-times"></i></button>
                            </div><!-- /.box-tools -->
                        </div><!-- /.box-header -->
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


<jsp:include page="../common/script.jsp"/>
<script>

    $('#ok').click(function () {
        refresh();
    });

    function queryChart(ip, roomId, version, startTime, endTime) {
        var url = "/metric/listByUri?uri=" + '${param.uri}' + "&ip=" + ip + '&roomId=' + roomId + '&version=' + version
                + '&startTime=' + startTime + '&endTime=' + endTime;
        var options = {
            chart: {
                renderTo: 'countContainer',
                type: 'spline'
            },
            exporting: {
                enabled: false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            credits: {
                enabled: false
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime',
//                dateTimeLabelFormats: {
//                    minute: '%e. %b %H:%M',
//                }
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
            exporting: {
                enabled: false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
            },
            credits: {
                enabled: false
            },
            tooltip: {
                formatter: function () {
                    return this.series.name + ': <b>' + Highcharts.numberFormat(this.y, 2) + ' </b>';
                },
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
            exporting: {
                enabled: false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
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
            exporting: {
                enabled: false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
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

        $.getJSON(url, function (data) {
            options.series = data.count;
//            options.title.text = "请求数";
            Highcharts.setOptions({ global: { useUTC: false } });
            var chart = new Highcharts.Chart(options);

            timeOptions.series = data.time;
//            timeOptions.title.text = "时延";
            var chart = new Highcharts.Chart(timeOptions);

            timeDistributionOptions.series = data.timeDistribution;
//            timeDistributionOptions.title.text = "时延分布";
            var chart = new Highcharts.Chart(timeDistributionOptions);

            codesOptions.series = data.codes;
//            codesOptions.title.text = "状态码比例分布";
            var chart = new Highcharts.Chart(codesOptions);
        });
    }
    function refresh() {
        var ip = $('#ipSelect ' + " option:selected").val();
        var roomId = $('#roomSelect' + " option:selected").val();
        var version = $('#versionSelect' + " option:selected").val();
        var startTime = $('#startTime' + " option:selected").val();
        var endTime = $('#endTime' + " option:selected").val();
        queryChart(ip, roomId, version, startTime, endTime);
    }

    $(document).ready(function () {
        refresh();
    });

</script>

</body>
</html>
