<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<%@ include file="/common/jsp/jquery-easyui.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块列表</title>
<link href="${ROOT}/cluetip/jquery.cluetip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ROOT}/cluetip/jquery.cluetip.js"></script>
<script type="text/javascript">
$(function(){
	listModule();
});
function listModule(){
	 $('#dg').datagrid({
	        title: '模块列表',
	        singleSelect:false,
	        pagination: true,
	        remoteSort:false,
	        showFooter:false, 
	        fit:true,
	        fitColumns:false,
	        showFooter:false,
	        rownumbers:false,
	        toolbar: '#toolbar',
	        url:'${ROOT}/listModule.do',
	        pageSize:20,
	        queryParams:{
			},
	        columns: [
	            [  	
					{field: 'id',width:50, title: '模块ID' },
	                {field: 'name',width:150, title: '名称' },
	             	{field: 'status',width:80, title: '模块状态',formatter:statusFormatter},
	                {field: 'sort',width:80, title: '排序'},
	                {field: 'remark',width:200, title: '备注'},
	                {field: 'operating',width:150, title: '操作',formatter:getOperating} 
	            ]
	        ]

	    }); 
}

function statusFormatter(value,row,rowIndex){
       	if(value==1){
       		return '启用';
       	}
       	return "<font color='red'>禁用</font>";
}

 function getOperating(value,row,rowIndex){
		var html ="";
		html += "<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='editItem(\""+row.id+"\")'>编辑</a>"+
				"<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='destroyItem(\""+row.id+"\")'>删除</a>";
		return html;
}
 
 
 function reset()
 {
	 $('#id').numberbox('setValue','');
		$('#name').val('');
		$('#status').combobox('setValue','');
		searchItem();
 }
 
	function searchItem(){
		  $('#dg').datagrid('load',{  
							moduleId: $('#id').numberbox('getValue'),
							name: $('#name').val(),  
			             	status:	$("#status").datebox('getValue')  			             	
			         	});  
	}
 function newItem(){  
  		location.href='${ROOT}/beginAddModule.do';
 }  
 
 function editItem(id){  
     /*  var row = $('#dg').datagrid('getChecked');  
     if(row.length==0){            	
     	$.messager.alert('系统提示','请选择需要修改的数据!');
 		return ;
     }
     if(row.length>1){
     	$.messager.alert('系统提示','只能选择一条数据修改!');
 		return ;
     }  */
     
     
    /* window.location.href="${ROOT}/beginUpdateModules.do?moduleId="+row[0].id; */
 	window.location.href="${ROOT}/beginUpdateModules.do?moduleId="+id;
   
 }
	function updateFunc()
	{
		 var row = $('#dg').datagrid('getChecked');  
     if(row.length==0){            	
     	$.messager.alert('系统提示','请选择需要修改的数据!');
 		return ;
     }
     if(row.length>1){
     	$.messager.alert('系统提示','只能选择一条数据修改!');
 		return ;
     }
     
     window.location.href="${ROOT}/beginRoleFunc.do?roleId="+row[0].id;
	}

 
 function destroyItem(id){  
     /* var row = $('#dg').datagrid('getChecked'); 
     if(row.length==0){            	
     	$.messager.alert('系统提示','请选择需要修改的数据!');
 		return ;
     } 
     if (row){  
     	var item=[];
     	for(var i=0;i<row.length;i++){
     		item.push(row[i].id);
     	}
     	var selectid=item.join("@_@");
         $.messager.confirm('确认','是否删除该项?',function(r){  
             if (r){  
                 $.post('${ROOT}/deleteModules.do',{moduleId:selectid},function(result){  
                     if (result=='success'){  
                         $('#dg').datagrid('reload');    // reload the user data  
                     } else {  
                        $.messager.alert('错误','删除失败!');
                     }  
                 },'text');  
             }  
         });  
     }  */
 	 $.messager.confirm('确认','是否删除该项?',function(r){  
          if (r){  
              $.post('${ROOT}/deleteModules.do',{moduleId:id},function(result){  
                  if (result=='success'){  
                      $('#dg').datagrid('reload');    // reload the user data  
                  } else {  
                     $.messager.alert('错误','删除失败!');
                  }  
              },'text');  
          }  
      });  
 }  
 
</script>
</head>

<body>	
	<div id="toolbar" style="padding:5px;height:auto">
    	<div>  
    		 模块ID: <input class="easyui-numberbox" type="text" id="id"  style="width:150px">
    		 模块名称: <input class="easyui-validatebox" type="text" id="name"  style="width:150px">
    		 模块状态: <select class="easyui-combobox" id="status" style="width:150px" data-options="panelHeight:'auto'">
    		  		 <option value="">-请选择-</option>
                 	 <option value="1">启用</option>  
         			 <option value="0">禁用</option>  
                 </select>    
                                                                       
               <a href="javascript:void(0)" onclick="searchItem();" class="easyui-linkbutton" iconCls="icon-search">查询</a>  
               <a href="javascript:void(0)" onclick="reset();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">添加</a> 
         </div>
         <!--  <div> -->
         	 <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">添加</a> -->
         	 <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>          -->
			 <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateFunc()">修改权限</a>     -->
	       	 <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a> 	   -->
       <!--  </div> -->
    </div>  
	<table id="dg">  </table>  
   <%--  <table id="dg" title="模块列表"  class="easyui-datagrid" 
            url="${ROOT}/listModule.do"  
            toolbar="#toolbar"  pagination="true"   fit="true“
            pageSize="20"
            rownumbers="true" fitColumns="false"> 
        <thead>  
            <tr>  
            	<th data-options="field:'id',checkbox:true"></th>  
          
                <th field="name" width="155">名称</th>
                <!-- 一期只有公共消息，不需要消息类型之分 -->
<!--                 <th field="typename" width="150">消息类型</th> -->
                <th data-options="field:'status',formatter:statusFormatter" width="80">是否可用</th>
                <th field="sort" width="80">排序</th>
                <th field="remark" width="200">备注</th>     
                <th data-options="field:'opt',formatter:getOperating" width="150">操作</th>            
            </tr>  
        </thead>  
    </table>   --%>
    

</body>
</html>
