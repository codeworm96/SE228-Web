<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="WEB-INF/view/layouts/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="">
                    <form class="login-form" role="form" method="POST" action="/register">
                    <h2 class="login-title">注册</h2>
                    <label for="username" class="sr-only">用户名</label>
                    <input type="text" id="username" name="username" class="form-control" placeholder="用户名" required="" autofocus="">
                    <label for="password" class="sr-only">密码</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="">
                    <label for="email" class="sr-only">电子邮件</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="电子邮件" required="">
                    <button class="btn btn-large btn-primary btn-block" type="submit">注册</button>
                    </form>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/view/layouts/footer.jsp" %>
