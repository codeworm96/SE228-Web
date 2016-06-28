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
    <table id="dg" title="图书" class="easyui-datagrid" style="width:700px;height:500px"
           url="list" method="get"
           toolbar="#toolbar" pagination="true"
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
        <tr>
            <th field="id" width="50">编号</th>
            <th field="isbn" width="100">ISBN</th>
            <th field="name" width="100">书名</th>
            <th field="category" width="100">类别</th>
            <th field="price" width="100">价格</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createBook()">新图书添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">图书编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBook()">删除图书</a>
    </div>
</div>

<div id="new-dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#new-buttons">
    <div class="ftitle">书籍基本信息</div>
    <form id="new-fm" method="post" novalidate>
        <div class="fitem">
            <label>ISBN:</label>
            <input name="isbn" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>书名:</label>
            <input name="name" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>类别:</label>
            <input name="category" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>价格：</label>
            <input name="price" class="easyui-textbox" required="true">
        </div>
    </form>
</div>
<div id="new-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBook()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#new-dlg').dialog('close')"
       style="width:90px">取消</a>
</div>

<div id="update-dlg" class="easyui-dialog" style="width:640px;height:460px;padding:10px 20px"
     closed="true" buttons="#update-buttons">
    <div class="ftitle">书籍详情</div>
    <form id="update-fm" method="post" novalidate>
        <img id="detail-img">
        <div class="detail-section">
            <div class="fitem">
                <label>封面上传:</label>
                <input id="fileupload" type="file" name="cover">
                <p id="cover-status" class="upload-msg"></p>
            </div>
            <div class="fitem">
                <label>编号:</label>
                <input name="id" class="easyui-textbox" readonly="">
            </div>
            <div class="fitem">
                <label>ISBN:</label>
                <input name="isbn" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>书名:</label>
                <input name="name" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>类别:</label>
                <input name="category" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>价格：</label>
                <input name="price" class="easyui-textbox" required="true">
            </div>
        </div>
    </form>
</div>
<div id="update-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateBook()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#update-dlg').dialog('close')"
       style="width:90px">取消</a>
</div>

<script type="text/javascript">

    function createBook(){
        $('#new-dlg').dialog('open').dialog('center').dialog('setTitle','新增图书');
        $('#new-fm').form('clear');
    }

    function editBook(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#detail-img').attr('src', '<%=ctx%>/cover?id=' + row.id + '&t=' + new Date().getTime());
            $('#cover-status').html('');

            $('#fileupload').fileupload({
                dataType: 'json',
                url: 'cover?id=' + row.id,
                done: function (e, result) {
                    if (result.msg) {
                        $('#cover-status').html(result.msg);
                    } else {
                        $('#cover-status').html('success!');
                        $('#detail-img').attr('src', '<%=ctx%>/cover?id=' + row.id + '&t=' + new Date().getTime());
                    }
                },
                fail: function (e, data) {
                    $('#cover-status').html('fail!');
                }
            });

            $('#update-dlg').dialog('open').dialog('center').dialog('setTitle','图书编辑');
            $('#update-fm').form('load',row);
        }
    }

    function saveBook(){
        var arr = $('#new-fm').serializeArray();
        var data = {};
        arr.forEach(function (item) {
            data[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: 'add',
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

    function updateBook(){
        var arr = $('#update-fm').serializeArray();
        var data = {};
        arr.forEach(function (item) {
            data[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: 'update',
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

    function removeBook(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','真的要删除这本书吗？',function(r){
                if (r){
                    $.post('remove', {id: row.id}, function(result){
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