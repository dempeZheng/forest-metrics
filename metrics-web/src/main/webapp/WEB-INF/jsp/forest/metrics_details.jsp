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
    <jsp:include page="../common/style.jsp"></jsp:include>
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

<body class="hold-transition skin-blue-light sidebar-mini">
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
                                    <li><a href="/app/index.do"><i class="fa fa-dashboard"></i> Home</a></li>
                                    <li>
                                        <a href="/metric/index.do?serviceName=${param.serviceName}">${param.serviceName}</a>
                                    </li>
                                    <li class="active">${param.uri}</li>
                                </ol>
                            </section>

                        </div>
                        <div class="box-body">
                            <div id="toolbar">
                                <div class="form-inline" role="form">
                                    <div class="control-group">
                                        <select id="ipSelect" class="form-control">
                                            <option value="">所有IP</option>
                                            <c:forEach var="ip" items="${ips}">
                                                <option value="${ip}">${ip}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="roomSelect" class="form-control">
                                            <option value="">所有机房</option>
                                            <c:forEach var="room" items="${rooms}">
                                                <option value="${room}">${room}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="versionSelect" class="form-control">
                                            <option value="">所有版本</option>
                                            <c:forEach var="version" items="${versions}">
                                                <option value="${version}">${version}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<div class="input-group input-group-sm">--%>
                                        <%--<input type="text" class="form-control datepicker" id="startTime">--%>
                                        <%--<span class="input-group-addon"><span--%>
                                        <%--class="glyphicon glyphicon-calendar"></span></span>--%>
                                        <%--</div>--%>
                                        <%--<strong class="input-strong-input">至</strong>--%>
                                        <%--<div class="input-group input-group-sm">--%>
                                        <%--<input type="text" class="form-control datepicker" id="endTime">--%>
                                        <%--<span class="input-group-addon"><span--%>
                                        <%--class="glyphicon glyphicon-calendar"></span></span>--%>
                                        <%--</div>--%>
                                        <div class="input-group">
                                            <button type="button" class="btn btn-default pull-right" id="daterange-btn">
                                                <i class="fa fa-calendar"></i>
                                                <span>选择时段</span>
                                                <i class="fa fa-caret-down"></i>
                                            </button>
                                        </div>
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

    function queryChart(ip, roomId, version, time) {

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

        <%--var url = "/metric/listByUri?uri=" + '${param.uri}' + "&ip=" + ip + '&roomId=' + roomId + '&version=' + version--%>
        <%--+ '&time=' + time;--%>

        var uri = '${param.uri}';
        var serviceName = '${param.serviceName}';
        $.ajax({
            type: "post",
            url: "/metric/listByUri.do",
            data: {uri: uri, ip: ip, roomId: roomId, version: version, time: time, serviceName: serviceName},
            dataType: "json",
            success: function (data) {
                options.series = data.count;
//            options.title.text = "请求数";
                Highcharts.setOptions({global: {useUTC: false}});
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
            }
        });

    }
    function refresh() {
        var ip = $('#ipSelect ' + " option:selected").val();
        var roomId = $('#roomSelect' + " option:selected").val();
        var version = $('#versionSelect' + " option:selected").val();
        var time = $('#daterange-btn span').html();
        if (time.indexOf('选择时段') != -1) {
            time = '';
        }
        queryChart(ip, roomId, version, time);
    }

    $(document).ready(function () {
        refresh();
    });


    $(function () {

        //Date range picker
        $('#reservation').daterangepicker();
        //Date range picker with time picker
        $('#reservationtime').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        //Date range as a button
        $('#daterange-btn').daterangepicker(
                {
                    ranges: {
                        '近半时': [moment().subtract('minutes', 30), moment()],
                        '近1小时': [moment().subtract('hours', 1), moment()],
                        '今日': [moment().startOf('day'), moment()],
                        '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                        '最近7日': [moment().subtract('days', 6), moment()],
                        '最近30日': [moment().subtract('days', 29), moment()]
                    },
                    startDate: moment().startOf('day'),
                    endDate: moment(),
                    minDate: '01/12/2016',    //最小时间
                    maxDate: moment(),    //最小时间
                    format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
                    showDropdowns: true,
                    showWeekNumbers: false, //是否显示第几周
                    timePicker: true, //是否显示小时和分钟
                    timePickerIncrement: 60, //时间的增量，单位为分钟
                    timePicker12Hour: false, //是否使用12小时制来显示时间
                    opens: 'right', //日期选择框的弹出位置
//                    buttonClasses : [ 'btn btn-default' ],
//                    applyClass : 'btn-small btn-primary blue',
//                    cancelClass : 'btn-small',
                    separator: ' to ',
                    locale: {
//                        applyLabel: '确定',
//                        cancelLabel: '取消',
                        customRangeLabel: '自定义',
                        daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                            '七月', '八月', '九月', '十月', '十一月', '十二月'],
                        firstDay: 1
                    }
                },

                function (start, end) {
                    $('#daterange-btn span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' to ' + end.format('YYYY-MM-DD HH:mm:ss'));
                }
        );

        //选择时间后触发重新加载的方法
        $("#daterange-btn").on('apply.daterangepicker', function () {
            //当选择时间后，出发dt的重新加载数据的方法
            var data = $('#daterange-btn span').html();
            console.log("date range:" + data);
        });


    });

</script>

</body>
</html>
