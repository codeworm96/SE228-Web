<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<script src="<%=ctx%>/js/d3.min.js"></script>
<script src="<%=ctx%>/js/chart/pie.js"></script>
<script src="<%=ctx%>/js/bootstrap-datepicker.js"></script>
<link href="<%=ctx%>/css/bootstrap-datepicker3.standalone.css" rel="stylesheet" type="text/css">

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <h2>销售统计</h2>
            <div class="panel panel-default panel-with-margin">
                <div class="panel-heading">按用户统计</div>
                <div class="panel-body">
                    <form class="form-inline" role="form" method="GET" action="<%=ctx%>/stat/user">
                        <div class="input-group">
                            <input class="form-control" type="text" name="username" placeholder="用户名">
                            <span class="input-group-btn">
                                <button class="btn btn-info" type="submit">按用户统计</button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
            <div class="panel panel-default panel-with-margin">
                <div class="panel-heading">按时间段统计</div>
                <div class="panel-body">
                    <form class="form-inline" role="form" method="get" action="<%=ctx%>/stat/date">
                        <div class="form-group">
                            <label for="startDate">开始日期</label>
                            <div class='input-group date'>
                                <input type="text" class="form-control" id="startDate" name="startDate">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="endDate">结束日期</label>
                            <div class='input-group date'>
                                <input type="text" class="form-control" id="endDate" name="endDate">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-info">按时间段统计</button>
                        <script type="text/javascript">
                            $('.date').datepicker({
                                format: "yyyy-mm-dd"
                            });
                        </script>
                    </form>
                </div>
            </div>
            <ul class="nav nav-tabs">
                <li id="category-tab" class="active" onclick="click_category()">
                    <a href="javascript:void(0);">各分类销量</a>
                </li>
                <li id="book-tab" onclick="click_book()">
                    <a href="javascript:void(0);">各书籍销量</a>
                </li>
            </ul>
            <div id="category" class="pie-chart"></div>
            <div id="book" class="pie-chart" style="display: none"></div>
            <script type="text/javascript">
                function click_category() {
                    d3.select('#category').style('display', 'block');
                    d3.select('#book').style('display', 'none');
                    d3.select('#category-tab').attr('class', 'active');
                    d3.select('#book-tab').attr('class', '');
                }
                function click_book() {
                    d3.select('#category').style('display', 'none');
                    d3.select('#book').style('display', 'block');
                    d3.select('#category-tab').attr('class', '');
                    d3.select('#book-tab').attr('class', 'active');
                }
                pie('#category', 'category');
                pie('#book', 'book');
            </script>
        </div>
    </div>
</div>

<%@ include file="../layouts/footer.jsp" %>
