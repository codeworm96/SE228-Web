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
<table id="dg" title="图书" class="easyui-datagrid" style="width:700px;height:250px"
           url="books/list" method="get"
           toolbar="#toolbar" pagination="true"
           rownumbers="true" fitColumns="true" singleSelect="true">
      <thead>
        <tr>
          <th field="id" width="50">编号</th>
          <th field="ISBN" width="100">ISBN</th>
          <th field="name" width="100">书名</th>
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

    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
      <div class="ftitle">书籍详情</div>
      <form id="fm" method="post" novalidate>
        <div class="fitem">
          <label>编号:</label>
          <input name="id" class="easyui-textbox" disabled="">
        </div>
        <div class="fitem">
          <label>ISBN:</label>
          <input name="ISBN" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
          <label>书名:</label>
          <input name="name" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
          <label>价格：</label>
          <input name="price" class="easyui-textbox">
      </form>
    </div>
    <div id="dlg-buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBook()" style="width:90px">保存</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
         style="width:90px">取消</a>
    </div>
    <script type="text/javascript">
var url;

function createBook(){
  $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增图书');
  $('#fm').form('clear');
  url = 'books';
}

function editBook(){
  var row = $('#dg').datagrid('getSelected');
  if (row){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','图书编辑');
    $('#fm').form('load',row);
    url = 'books/' + row.id;
  }
}

function saveBook(){
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

function removeBook(){
  var row = $('#dg').datagrid('getSelected');
  if (row){
    $.messager.confirm('确认','真的要删除这本书吗？',function(r){
      if (r){
        $.post('books/'+row.id, {_method:"delete"}, function(result){
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
