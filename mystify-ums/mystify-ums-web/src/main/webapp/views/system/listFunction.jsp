<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<%@ include file="/common/jsp/jquery-easyui.jsp"%> 
<%--  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能权限列表</title>
 
<script type="text/javascript">
$(function(){
	 listFunction();
});

function listFunction(){
	 $('#dg').datagrid({
	        title: '功能权限列表',
	        singleSelect:false,
	        pagination: true,
	        remoteSort:false,
	        showFooter:false, 
	        fit:true,
	        fitColumns:false,
	        showFooter:false,
	        rownumbers:false,
	        toolbar: '#toolbar',
	        url:'${ROOT}/listFunction.do',
	        pageSize:20,
	        queryParams:{
			},
	        columns: [
	            [  	
	                {field: 'id',width:50, title: 'id' },
	                {field: 'name',width:150, title: '名称' },
	             	{field: 'status',width:80, title: '权限状态',formatter:statusFormatter},
	             	{field: 'url',width:300, title: 'URL'},
	             	{field: 'sort',width:80, title: '排序'},
	                {field: 'moduleName',width:150, title: '所属模块'},
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
function reset(){
	$('#name').val('');
	$('#status').combobox('setValue','');
	$('#moduleid').combobox('setValue','');
	searchItem();
}
function searchItem(){
    $('#dg').datagrid('load',{  
					name: $('#name').val(),
	             	status:	$("#status").datebox('getValue'),  			             	
	             	moduleid:	$("#moduleid").datebox('getValue'),  			             	
	         	});  
}
function newItem(){  
		location.href='${ROOT}/beginAddFunctions.do';
}  
function editItem(id){  
	window.location.href="${ROOT}/beginUpdateFunction.do?funcId="+id;
}
function destroyItem(id){  
	$.messager.confirm('确认','是否删除该项?',function(r){  
        if (r){  
            $.post('${ROOT}/deleteFunction.do',{funcId:id},function(result){  
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
    		 权限名称: <input class="easyui-validatebox" type="text" id="name"  style="width:150px">
    		 权限状态: <select class="easyui-combobox" id="status" style="width:150px" data-options="panelHeight:'auto'">
    		  		 <option value="">-请选择-</option>
                 	 <option value="1">启用</option>  
         			 <option value="0">禁用</option>  
                 </select>   
                                     所属模块:
                     <input id="moduleid" class="easyui-combobox" 
        			data-options="valueField:'id',textField:'name',panelHeight:'auto',url:'${ROOT}/getModulesForJSON.do'" />  
                                                                    
             <a href="javascript:void(0)" onclick="searchItem();" class="easyui-linkbutton" iconCls="icon-search">查询</a>  
             <a href="javascript:void(0)" onclick="reset();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>    
             <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">添加</a>
         </div>
         </div>
		 <table id="dg" ></table>
 
    

</body>
</html>
