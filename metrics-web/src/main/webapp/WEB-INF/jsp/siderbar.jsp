<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-th-large"></i> 监控中心
                </a>
                <ul class="treeview-menu">
                    <li><a href="/app/index.do"><i class="fa fa-table"></i>服务列表</a></li>
                    <li><a href="/metric/index.do"><i class="fa fa-bar-chart-o"></i>服务概况</a></li>
                </ul>

            </li>

            <li>
                <a href="#">
                    <i class="fa fa-envelope"></i> <span>帮助</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>用户指南</a></li>
                </ul>
            </li>


        </ul>
    </section>
</aside>