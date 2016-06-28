<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="WEB-INF/view/layouts/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="panel panel-default">
                <div class="panel-heading">出错了～</div>
                <div class="panel-body">
                    <% if (request.getAttribute("err_msg") != null) { %>
                        <%= request.getAttribute("err_msg") %>
                    <% } else { %>
                        啊呀，出错了～
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/view/layouts/footer.jsp" %>
