<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.order.OrderItem" %>
<%@ page import="java.util.List" %>

<div class="container">
    <h2>购物车</h2>
    <p>不要的商品数量可以填0</p>
    <form role="form" method="POST" action="/order/add">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <td>ISBN</td>
                <td>书名</td>
                <td>类别</td>
                <td>单价</td>
                <td>数量</td>
            </tr>
            </thead>
            <tbody>
            <% for (OrderItem item : (List<OrderItem>)request.getAttribute("cart")) { %>
                <tr>
                    <td><%= item.getIsbn() %></td>
                    <td><%= item.getName() %></td>
                    <td><%= item.getCategory() %></td>
                    <td><%= item.getPrice() %></td>
                    <td><input type="text" name="<%= item.getId() %>" value="<%= item.getNum() %>"></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <button class="btn btn-large btn-success" type="submit">提交订单</button>
    </form>
</div>

<%@ include file="../layouts/footer.jsp" %>