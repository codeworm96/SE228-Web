<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<div class="login-flex-container" style="background: url(<%=ctx%>/img/background.jpg) center center / cover no-repeat fixed;">
	<div class="account-container">
		<h2>用户注册</h2>

		<form id="el" role="form" method="POST" action="<%=ctx%>/auth/register">
			<div class="account-input">
				<input type="text" id="username" name="username" placeholder="用户名(注册后不可修改)" required="" autofocus="">
			</div>
			<div class="account-input">
				<input type="password" id="password" name="password" placeholder="密码" required="" v-model="password">
			</div>
			<div class="account-input">
				<input type="password" id="password-repeat" placeholder="确认密码" required="" v-model="password_repeat">
			</div>
			<div class="bottom-link" v-if="password != password_repeat">
				<p>密码不一致！</p>
			</div>
			<button type="submit" v-bind:disabled="password != password_repeat">注册</button>

			<div class="bottom-link">
				<a href="<%=ctx%>/auth/login/">已有账户？马上登录</a>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="<%=ctx%>/js/vue.js"></script>
<script type="text/javascript">
	var vm = new Vue({
		el: '#el',
		data: {
			password: "",
			password_repeat: ""
		}
	});
</script>

<%@ include file="../layouts/footer.jsp" %>
