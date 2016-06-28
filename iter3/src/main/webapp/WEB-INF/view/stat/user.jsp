<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layouts/header.jsp" %>

<%@ page import="bookstore.utility.OrderStatusConst" %>
<%  request.setAttribute("accepted_id", OrderStatusConst.ACCEPTED); %>

<div class="container">
    <h2>用户 "${username}" 的统计信息</h2>
    <p>订单总金额: ${total_price}</p>
    <p>订单总数: ${num}</p>
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
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.date}</td>
                    <td>${order.price}</td>
                    <td>${order.statusInfo}</td>
                    <td>
                        <a class="btn btn-info" href="detail?id=${order.id}">详情</a>
                        <c:if test="${order.status == accepted_id}">
                            <a class="btn btn-danger" href="delete?id=${order.id}">删除</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layouts/footer.jsp" %>