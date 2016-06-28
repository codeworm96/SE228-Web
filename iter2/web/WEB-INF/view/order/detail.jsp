<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.order.Order" %>
<%@ page import="bookstore.order.OrderItem" %>
<%@ page import="java.util.List" %>

<div class="container">
    <% Order order = (Order)request.getAttribute("order"); %>
    <p>下单用户: <%= order.getUsername() %></p>
    <p>下单时间: <%= order.getDate() %></p>
    <p>状态: <%= order.getStatusInfo() %></p>
    <p>总价: <%= order.getPrice() %></p>
    <p>内容:</p>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <td>书名</td>
            <td>ISBN</td>
            <td>类别</td>
            <td>单价(下单时)</td>
            <td>数量</td>
        </tr>
        </thead>
        <tbody>
        <% for(OrderItem item : (List<OrderItem>)request.getAttribute("item")) {%>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getIsbn() %></td>
            <td><%= item.getCategory() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getNum() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>