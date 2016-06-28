<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.order.Order" %>
<%@ page import="java.util.List" %>

<div class="container">
    <h2>我的订单</h2>
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
                <a class="btn btn-info" href="detail?id=<%= order.getId() %>">详情</a>
                <% if (order.getStatus().equals("accepted")) { %>
                    <a class="btn btn-success" href="pay?id=<%= order.getId() %>">支付</a>
                    <a class="btn btn-danger" href="delete?id=<%= order.getId() %>">删除</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>