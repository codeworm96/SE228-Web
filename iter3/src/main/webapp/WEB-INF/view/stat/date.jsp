<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored ="false" %>

<%@ include file="../layouts/header.jsp" %>

<script src="<%=ctx%>/js/d3.min.js"></script>
<script src="<%=ctx%>/js/chart/date.js"></script>

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <h2>按时间统计</h2>
            <p>${startDate} ~ ${endDate}</p>
            <ul class="nav nav-tabs">
                <li id="day-tab" class="active" onclick="click_day()">
                    <a href="javascript:void(0);">日</a>
                </li>
                <li id="month-tab" onclick="click_month()">
                    <a href="javascript:void(0);">月</a>
                </li>
                <li id="year-tab" onclick="click_year()">
                    <a href="javascript:void(0);">年</a>
                </li>
            </ul>
            <div id="day">
                <h3>每日销售量</h3>
            </div>
            <div id="month" style="display: none">
                <h3>每月销售量</h3>
            </div>
            <div id="year" style="display: none">
                <h3>每年销售量</h3>
            </div>
            <script type="text/javascript">
                function click_day() {
                    d3.select('#day-tab').attr('class', 'active');
                    d3.select('#month-tab').attr('class', '');
                    d3.select('#year-tab').attr('class', '');
                    d3.select("#day").style("display", "block");
                    d3.select("#month").style("display", "none");
                    d3.select("#year").style("display", "none");
                }

                function click_month() {
                    d3.select('#day-tab').attr('class', '');
                    d3.select('#month-tab').attr('class', 'active');
                    d3.select('#year-tab').attr('class', '');
                    d3.select("#day").style("display", "none");
                    d3.select("#month").style("display", "block");
                    d3.select("#year").style("display", "none");
                }

                function click_year() {
                    d3.select('#day-tab').attr('class', '');
                    d3.select('#month-tab').attr('class', '');
                    d3.select('#year-tab').attr('class', 'active');
                    d3.select("#day").style("display", "none");
                    d3.select("#month").style("display", "none");
                    d3.select("#year").style("display", "block");
                }
                day('#day', '<%=ctx%>/stat/date/day?startDate=${startDate}&endDate=${endDate}');
                bar('#month', '<%=ctx%>/stat/date/month?startDate=${startDate}&endDate=${endDate}');
                bar('#year', '<%=ctx%>/stat/date/year?startDate=${startDate}&endDate=${endDate}');
            </script>
        </div>
    </div>
</div>

<%@ include file="../layouts/footer.jsp" %>
