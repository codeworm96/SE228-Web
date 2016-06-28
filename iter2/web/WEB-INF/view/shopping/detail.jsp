<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.book.Book" %>

<div class="container">
    <div class="col-md-offset-3 col-md-6">
        <h2>书籍详情</h2>
        <% Book book = (Book) request.getAttribute("book"); %>
        <p><b>ISBN: </b><%= book.getIsbn() %></p>
        <p><b>名称: </b><%= book.getName() %></p>
        <p><b>类别: </b><%= book.getCategory() %></p>
        <p><b>单价: </b><%= book.getPrice() %></p>

        <form role="form" method="POST" action="/cart/add?id=<%= book.getId() %>">
            <div class="form-group">
                <label for="num">数量</label>
                <input type="text" id="num" name="num" class="form-control" value=1 required="">
            </div>
            <button class="btn btn-large btn-primary" type="submit">加入购物车</button>
        </form>

    </div>
</div>

<%@ include file="../layouts/footer.jsp" %>
