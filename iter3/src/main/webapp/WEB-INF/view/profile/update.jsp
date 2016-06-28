<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bookstore.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored ="false" %>

<%@ include file="../layouts/header.jsp" %>

<script src="<%=ctx%>/js/vendor/jquery.ui.widget.js"></script>
<script src="<%=ctx%>/js/jquery.iframe-transport.js"></script>
<script src="<%=ctx%>/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=ctx%>/js/vue.js"></script>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h2>个人信息</h2>
            <form id="form" action="<%=ctx%>/profile/" method="post">
                <% User user = (User) request.getAttribute("user"); %>
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" disabled value="<%=user.getUsername()%>">
                </div>
                <div class="form-group">
                    <label for="role">角色</label>
                    <input type="text" class="form-control" id="role" disabled value="<%=user.isAdmin() ? "管理员" : "普通用户"%>">
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="新密码" v-model="password">
                    <input type="password" class="form-control" id="password-repeat" placeholder="重复新密码" v-model="password_repeat">
                    <p class="help-block" v-if="password != password_repeat">密码不一致</p>
                </div>
                <div class="form-group">
                    <label for="avatar">头像</label><img id="avatar" class="avatar-big">
                    <input id="fileupload" type="file" name="avatar">
                    <p id="avatar-status" class="help-block"></p>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" id="email" name="email" value="<%=user.getEmail()%>">
                </div>
                <div class="form-group">
                    <label for="phone">手机</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="<%=user.getPhone()%>">
                </div>
                <div class="form-group">
                    <label for="address">收件地址</label>
                    <input type="text" class="form-control" id="address" name="address" value="<%=user.getAddress()%>">
                </div>
                <button type="submit" class="btn btn-primary" v-bind:disabled="password != password_repeat">提交修改</button>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('#avatar').attr('src', '<%=ctx%>/avatar?username=<%=user.getUsername()%>&t=' + new Date().getTime());
    $('#avatar-status').html('');

    $('#fileupload').fileupload({
        dataType: 'json',
        url: 'avatar',
        done: function (e, result) {
            if (result.msg) {
                $('#avatar-status').html(result.msg);
            } else {
                $('#avatar-status').html('success!');
                $('#avatar').attr('src', '<%=ctx%>/avatar?username=<%=user.getUsername()%>&t=' + new Date().getTime());
            }
        },
        fail: function (e, data) {
            $('#avatar-status').html('fail!');
        }
    });

    var vm = new Vue({
        el: '#form',
        data: {
            password: "",
            password_repeat: ""
        }
    });
</script>

<%@ include file="../layouts/footer.jsp" %>
