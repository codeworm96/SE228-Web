<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<div class="login-flex-container" style="background: url(<%=ctx%>/img/background.jpg) center center / cover no-repeat fixed;">
	<div class="account-container">
		<h2>用户登录</h2>

		<form role="form" method="POST" action="<%=ctx%>/login">
			<div class="account-input">
				<input type="text" id="username" name="username" placeholder="用户名" required="" autofocus="">
			</div>
			<div class="account-input">
				<input type="password" id="password" name="password" placeholder="密码" required="">
			</div>

			<button type="submit">登录</button>

			<div class="bottom-link">
				<a href="<%=ctx%>/auth/register/">还没有账户，马上注册</a>
			</div>
		</form>
	</div>
</div>

<%@ include file="../layouts/footer.jsp" %>
