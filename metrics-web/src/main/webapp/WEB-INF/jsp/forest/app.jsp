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
    <jsp:include page="../siderbar.jsp"/>
    <div class="content-wrapper">

        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class=" box  box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title">服务列表</h3>
                            <div class="box-tools pull-right">
                            </div><!-- /.box-tools -->
                        </div>
                        <div class="box-body ">
                            <table id="table"  class="table table-bordered">
                                <div id="toolbar">
                                </div>
                            </table>
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

<%--<script src="/dist/js/pages/dashboard.js"></script>--%>
<jsp:include page="../common/script.jsp"/>
<script>

    $('#ok').click(function () {
        $('#table').bootstrapTable('refresh');
    });

    $(function () {
        window.initHandle = {
            // 编辑
            editModel: function (id) {
                $.ajax({
                    type: "post",
                    url: "/discovery/query",
                    data: {vuid: id},
                    dateType: "json",
                    success: function (json) {
                        var data = JSON.parse(json).data;
                        $("#uid").attr("readonly", true)
                        $("#edit").val(1);
                        if (data) {
                            $("#uid").val(data.uid);
                            $("#topSid").val(data.topSid);
                            $("#modal").modal('show');
                        }

                    }
                });
            },
            initTable: function () {
                $('#table').bootstrapTable('destroy');
                $('#table').bootstrapTable({
                    columns: [
                        {
                            field: '_id',
                            title: 'uri',
                            sortable: 'true',
                            visible: true,
                            align: 'center',
                            width: '15%',
                            formatter: function (val, row, index) {
                                return '<a href="/metric/detail.do?uri=' + row._id + '">' + row._id + '</a>';
                            }
                        },
                        {
                            field: 'count',
                            title: '请求数',
                            sortable: 'true',
                            visible: true,
                            align: 'center',
                            width: '15%'
                        },
                        {field: 'maxTime', title: 'maxTime(毫秒)', sortable: 'true', align: 'center', width: '15%'},
                        {field: 'minTime', title: 'minTime(毫秒)', sortable: 'true', align: 'center', width: '15%'},
                        {
                            field: 'time', title: 'avgTime(毫秒)', sortable: 'true', align: 'center', width: '15%',
                            formatter: function (val, row, index) {
                                return (row.time / row.count).toFixed(2);
                            }
                        },
                        {
                            field: 'id',
                            title: '操作',
                            align: 'center',
                            width: '20%',
                        },
                    ],
                    cache: false,
                    striped: true,
                    showRefresh: true,
                    showToggle: true,
                    showColumns: true,
                    pagination: true,
                    pageList: [5, 10, 20, 30, 50],
                    search: true,
                    sidePagination: "client",
                    toolbar: '#toolbar',
                    url: '/metric/groupByUri.do',
                    queryParams: function queryParams(params) {   //设置查询参数
                        var param = {};
                        return param;
                    }
                });

            }
        };

        // 初始化table
        initHandle.initTable();

        // 设置校验ui
        $('#form').validationEngine('attach', {
            promptPosition: 'centerRight',
            scroll: false
        });

    });


</script>

</body>
</html>
