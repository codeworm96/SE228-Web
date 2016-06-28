<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.book.Book" %>
<%@ page import="java.util.List" %>

<div class="container">
    <h2>书籍列表</h2>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <td>ISBN</td>
            <td>书名</td>
            <td>类别</td>
            <td>价格</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <% for(Book book : (List<Book>)request.getAttribute("book")) {%>
        <tr>
            <td><%= book.getIsbn() %></td>
            <td><%= book.getName() %></td>
            <td><%= book.getCategory() %></td>
            <td><%= book.getPrice() %></td>
            <td>
                <a class="btn btn-info" href="detail?id=<%= book.getId() %>">详情</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>