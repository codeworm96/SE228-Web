<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<div class="container">
    <p>下单用户: ${order.username}</p>
    <p>下单时间: ${order.date}</p>
    <p>状态: ${order.statusInfo}</p>
    <p>总价: ${order.price}</p>
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
            <c:forEach var="item" items="${order.items}">
                <tr>
                    <td>${item.book.name}</td>
                    <td>${item.book.isbn}</td>
                    <td>${item.book.category}</td>
                    <td>${item.price}</td>
                    <td>${item.num}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>