<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<style type="text/css">
    .panel-body {
        padding: 0;
    }
</style>

<script src="<%=ctx%>/js/vendor/jquery.ui.widget.js"></script>
<script src="<%=ctx%>/js/jquery.iframe-transport.js"></script>
<script src="<%=ctx%>/js/jquery.fileupload.js"></script>

<div class="container">
    <table id="dg" title="用户" class="easyui-datagrid" style="width:800px;height:500px"
           url="list" method="get"
           toolbar="#toolbar" pagination="true"
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
        <tr>
            <th field="username" width="100">用户名</th>
            <th field="password" width="100">密码</th>
            <th field="isAdmin" width="100">管理员</th>
            <th field="email" width="100">Email</th>
            <th field="phone" width="100">手机</th>
            <th field="address" width="100">收件地址</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createUser()">新用户添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">用户信息编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除用户</a>
    </div>
</div>

<div id="new-dlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px"
     closed="true" buttons="#new-buttons">
    <div class="ftitle">用户信息</div>
    <form id="new-fm" method="post" novalidate>
        <div class="fitem">
            <label>用户名:</label>
            <input name="username" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>密码:</label>
            <input name="password" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>管理员:</label>
            <input name="isAdmin" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Email：</label>
            <input name="email" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>手机：</label>
            <input name="phone" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>收货地址：</label>
            <input name="address" class="easyui-textbox">
        </div>
    </form>
</div>
<div id="new-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#new-dlg').dialog('close')"
       style="width:90px">取消</a>
</div>

<div id="update-dlg" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px"
     closed="true" buttons="#update-buttons">
    <div class="ftitle">用户详情</div>
    <form id="update-fm" method="post" novalidate>
        <div class="fitem">
            <label>头像上传:</label>
            <img id="avatar" class="avatar-big">
            <input id="fileupload" type="file" name="avatar" style="display: inline-block">
            <p id="avatar-status" class="upload-msg"></p>
        </div>
        <div class="fitem">
            <label>用户名:</label>
            <input name="username" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>密码:</label>
            <input name="password" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>管理员:</label>
            <input name="isAdmin" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Email：</label>
            <input name="email" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>手机：</label>
            <input name="phone" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>收货地址：</label>
            <input name="address" class="easyui-textbox">
        </div>
    </form>
</div>
<div id="update-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateUser()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#update-dlg').dialog('close')"
       style="width:90px">取消</a>
</div>

<script type="text/javascript">

    function createUser(){
        $('#new-dlg').dialog('open').dialog('center').dialog('setTitle','添加用户');
        $('#new-fm').form('clear');
    }

    function editUser(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#avatar').attr('src', '<%=ctx%>/avatar?username=' + row.username + '&t=' + new Date().getTime());
            $('#avatar-status').html('');

            $('#fileupload').fileupload({
                dataType: 'json',
                url: 'avatar?username=' + row.username,
                done: function (e, result) {
                    if (result.msg) {
                        $('#avatar-status').html(result.msg);
                    } else {
                        $('#avatar-status').html('success!');
                        $('#avatar').attr('src', '<%=ctx%>/avatar?username=' + row.username + '&t=' + new Date().getTime());
                    }
                },
                fail: function (e, data) {
                    $('#avatar-status').html('fail!');
                }
            });

            $('#update-dlg').dialog('open').dialog('center').dialog('setTitle','用户信息编辑');
            $('#update-fm').form('load',row);
        }
    }

    function saveUser(){
        var arr = $('#new-fm').serializeArray();
        var data = {};
        arr.forEach(function (item) {
            data[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: 'save',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.errorMsg) {
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#new-dlg').dialog('close');      // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function updateUser(){
        var arr = $('#update-fm').serializeArray();
        var data = {};
        arr.forEach(function (item) {
            data[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: 'save',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.errorMsg) {
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#update-dlg').dialog('close');      // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function removeUser(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','真的要删除这位用户吗？',function(r){
                if (r){
                    $.post('remove', {username: row.username}, function(result){
                        if (result.success){
                            $('#dg').datagrid('reload');// reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    },'json');
                }
            });
        }
    }
</script>
<style type="text/css">
    #fm {
        margin:0;
        padding:10px 30px;
    }
    .ftitle {
        font-size:14px;
        font-weight:bold;
        padding:5px 0;
        margin-bottom:10px;
        border-bottom:1px solid #ccc;
    }
    .fitem{
        margin-bottom:5px;
    }
    .fitem label{
        display:inline-block;
        width:80px;
    }
    .fitem input{
        width:160px;
    }
</style>

<%@ include file="../layouts/footer.jsp" %>