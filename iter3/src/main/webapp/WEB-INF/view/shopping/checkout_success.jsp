<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="panel panel-default">
                <div class="panel-heading">订单创建成功</div>
                <div class="panel-body">
                    <p>订单号: ${order_id}</p>
                    <p>总价: ${price}</p>
                    <a href="<%=ctx%>/order/pay?id=${order_id}" class="btn btn-success">立即支付</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layouts/footer.jsp" %>