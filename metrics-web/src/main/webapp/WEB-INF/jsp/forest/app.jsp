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
                            <h3 class="box-title">服务总览</h3>
                            <div class="box-tools pull-right">
                            </div><!-- /.box-tools -->
                        </div>
                        <div class="box-body ">
                            <table id="table" class="table table-bordered">
                                <div id="toolbar">
                                    <button id="add" class="btn btn-default">
                                        <i class="glyphicon glyphicon-plus"></i>增加
                                    </button>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modalLabel">服务信息</h4>
            </div>
            <div class="modal-body">
                <form id="add-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="serviceName" class="col-sm-3 control-label">服务名:</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control validate[required]" name="serviceName"
                                   id="serviceName">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="tipModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body" id="tipMsg"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
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

            initTable: function () {
                $('#table').bootstrapTable('destroy');
                $('#table').bootstrapTable({
                    columns: [
                        {
                            field: 'serviceName',
                            title: 'serviceName',
                            sortable: 'true',
                            visible: true,
                            align: 'center',
                            width: '15%',
                            formatter: function (val, row, index) {
                                return '<a href="/metric/index.do?serviceName=' + row.serviceName + '">' + row.serviceName + '</a>';
                            }
                        },
                        {
                            field: 'appKey',
                            title: 'appKey',
                            sortable: 'true',
                            visible: true,
                            align: 'center',
                            width: '15%'
                        },
                        {field: 'createAt', title: 'createAt', sortable: 'true', align: 'center', width: '15%'},
                        {field: 'createBy', title: 'createBy', sortable: 'true', align: 'center', width: '15%'},
                        {
                            field: 'serviceName',
                            title: '操作',
                            align: 'center',
                            width: '15%',
                            formatter: function (val, row, index) {
                                return '<button data-id=' + val + ' class="opt_remove btn btn-danger"><i class="glyphicon glyphicon-remove"></i>删除</button>';
                            }
                        }
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
                    url: '/app/list.do',
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

        $("body").undelegate(".opt_remove", "click").delegate(".opt_remove", "click", function () {
            var serviceName = $(this).attr("data-id");
            if (confirm("你确认删除【" + serviceName + "】吗?\r\n删除后再也不能找回，请谨慎操作！")) {
                $.ajax({
                    type: 'post',
                    url: "/app/deleteByServiceName.do",
                    data: {'serviceName': serviceName},
                    dataType: "json",
                    success: function (json) {
                        if (json.code == 0) {
                            $("#tipMsg").text("删除成功");
                            $("#tipModal").modal('show');
                            TableHelper.doRefresh("#table");
                        } else {
                            $("#tipMsg").text("删除失败，错误码：" + json.msg);
                            $("#tipModal").modal('show');
                        }
                    }
                });
            }
        });

    });

    $("#add").click(function () {
        // 打开编辑弹窗
        $("#addModal").modal('show');
    });


    $("#save").click(function () {
        console.log($('#add-form').serialize());
        if ($("#add-form").validationEngine('validate')) {
            $.ajax({
                type: "post",
                url: "/app/save.do",
                data: $('#add-form').serialize(),
                dataType: "json",
                success: function (json) {
                    if (json.code == 0) {
                        $("#addModal").modal('hide');
                        $("#tipMsg").text("保存成功!");
                        $("#tipModal").modal('show');
                        TableHelper.doRefresh("#table");
                    } else {
                        $("#tipMsg").text("保存失败! msg：" + json.msg);
                        $("#tipModal").modal('show');
                    }
                }
            });
        }
    });


</script>

</body>
</html>
