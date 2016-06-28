<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="WEB-INF/view/layouts/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <h2>销售统计</h2>
            <div class="panel panel-default">
                <div class="panel-heading">按用户查询</div>
                <div class="panel-body">
                    <form role="form" method="GET" action="admin/orders/user">
                        <div class="input-group">
                            <input class="form-control" type="text" name="username" value="">
                            <span class="input-group-btn">
                                <button class="btn btn-info" type="submit">提交查询</button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
            <script src="/js/d3.min.js"></script>
            <div class="panel panel-default">
                <div class="panel-heading">分类销售量</div>
                <div class="panel-body">
                    <div id="category"></div>
                    <script src="/js/chart/category.js"></script>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">销售量时间关系</div>
                <div class="panel-body">
                    <div class="btn-group" role="group" aria-label="...">
                        <button type="button" class="btn btn-default" onclick="click_day()">日</button>
                        <button type="button" class="btn btn-default" onclick="click_month()">月</button>
                        <button type="button" class="btn btn-default" onclick="click_year()">年</button>
                    </div>
                    <div id="day">
                        <h3>每日销售量</h3>
                    </div>
                    <div id="month" style="display: none">
                        <h3>每月销售量</h3>
                    </div>
                    <div id="year" style="display: none">
                        <h3>每年销售量</h3>
                    </div>
                    <script src="/js/chart/date.js"></script>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/view/layouts/footer.jsp" %>
