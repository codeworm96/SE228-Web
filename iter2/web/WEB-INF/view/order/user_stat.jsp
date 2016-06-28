<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.order.Order" %>
<%@ page import="java.util.List" %>

<div class="container">
    <h2>订单信息</h2>
    <p>用户: <%= request.getParameter("username") %></p>
    <p>订单总金额: <%= (int)request.getAttribute("total_price") / 100.0 %></p>
    <p>订单总数: <%= request.getAttribute("num") %></p>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <td>下单日期</td>
            <td>总价</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <% for(Order order : (List<Order>)request.getAttribute("order")) {%>
        <tr>
            <td><%= order.getDate() %></td>
            <td><%= order.getPrice() %></td>
            <td><%= order.getStatusInfo() %></td>
            <td>
                <a class="btn btn-info" href="/order/detail?id=<%= order.getId() %>">详情</a>
                <% if (order.getStatus().equals("accepted")) { %>
                    <a class="btn btn-danger" href="/order/delete?id=<%= order.getId() %>">删除</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>