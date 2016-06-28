@extends('layouts.app')

@section('extra_style')
<style>
.panel {
    margin:auto;
}
</style>
@endsection

@section('content')
<div class="container">
<table id="dg" title="用户" class="easyui-datagrid" style="width:700px;height:250px"
           url="users/list" method="get"
           toolbar="#toolbar" pagination="true"
           rownumbers="true" fitColumns="true" singleSelect="true">
      <thead>
        <tr>
          <th field="username" width="100">用户名</th>
          <th field="password" width="100">密码</th>
          <th field="email" width="100">邮箱</th>
          <th field="is_admin" width="50">管理员</th>
        </tr>
      </thead>
    </table>
    <div id="toolbar">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createUser()">新用户添加</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">用户编辑</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除用户</a>
    </div>
</div>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
      <div class="ftitle">用户信息</div>
      <form id="fm" method="post" novalidate>
        <div class="fitem">
          <label>用户名:</label>
          <input name="username" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
          <label>密码:</label>
          <input name="password" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
          <label>电子邮件:</label>
          <input name="email" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
          <label>管理员(Y/N):</label>
          <input name="is_admin" class="easyui-textbox" required="true">
      </form>
    </div>
    <div id="dlg-buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
         style="width:90px">取消</a>
    </div>
    <script type="text/javascript">
var url;

function createUser(){
  $('#dlg').dialog('open').dialog('center').dialog('setTitle','新建用户');
  $('#fm').form('clear');
  url = 'users';
}

function editUser(){
  var row = $('#dg').datagrid('getSelected');
  console.log(row);
  if (row){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','用户编辑');
    $('#fm').form('load',row);
    url = 'users/' + row.username;
  }
}

function saveUser(){
  $('#fm').form('submit',{
    url: url,
    onSubmit: function(){
      return $(this).form('validate');
    },
    success: function(result){
      var result = JSON.parse(result);
      if (result.errorMsg){
        $.messager.show({
          title: 'Error',
          msg: result.errorMsg
        });
      } else {
        $('#dlg').dialog('close');      // close the dialog
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
        $.post('users/'+row.username, {_method:"delete"}, function(result){
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
@endsection
