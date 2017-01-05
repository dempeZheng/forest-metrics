<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-table"></i> 系统监控
                </a>
                <ul class="treeview-menu">
                    <li><a href="/metric/index"><i class="fa fa-pie-chart"></i>服务概况</a></li>
                </ul>

            </li>

            <li>
                <a href="#">
                    <i class="fa fa-envelope"></i> <span>设置</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>用户权限配置</a></li>
                </ul>
            </li>


        </ul>
    </section>
</aside>