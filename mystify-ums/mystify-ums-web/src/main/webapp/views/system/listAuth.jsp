<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<%@ include file="/common/jsp/jquery-easyui.jsp"%> 
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员列表</title>
 
<link href="${ROOT}/cluetip/jquery.cluetip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ROOT}/cluetip/jquery.cluetip.js"></script>
<script type="text/javascript">

	$(function() {
		
		$('#dg').datagrid({
	        title: '人员列表',
	        singleSelect:false,
	        rownumbers:false,
	        pagination: true,
	        remoteSort:false,
	        showFooter:false, 
	        nowrap:false,
	        fit:true,
	        fitColumns:false,
	        toolbar: '#toolbar',
	        url:'${ROOT}/listAuth.do',
	        pageSize:20,
	        queryParams:{
			},
	        columns: [
	            [  	
	             	{field: 'id',width:50, title: 'id' },
	                {field: 'name',width:200, title: '姓名' },
	                {field: 'status',width:200, title: '人员状态',formatter:statusFormatter},
	                {field: 'type',width:200, title: '账号类型',formatter:typeFormatter},
	                {field: 'roleName',width:200, title: '所属角色'},
	                {field: 'remark',width:200, title: '备注'},
	                {field: 'operating',width:150, title: '操作',formatter:getOperating} 
	            ]
	        ]

	    }); 
		
	});
	
function typeFormatter(value) {
		if (value == 0) {
			return '内部人员账号';
		}
		return '托管中心账号';
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
 
 function searchItem(){
		  $('#dg').datagrid('load',{  
							id:  $('#id').numberbox('getValue'),
							name: $('#name').val(),  
			             	status:	$("#status").datebox('getValue')  			             	
			         	});  
	}
function reset()
	{
		$('#id').numberbox('setValue','');
		$('#name').val('');
		$('#status').combobox('setValue','');
		searchItem();
	}
	
function newItem(){  
 location.href='${ROOT}/beginAddAuths.do';
}  
function editItem(id){  
    window.location.href="${ROOT}/beginUpdateAuths.do?ids="+id;
}

function destroyItem(id){  
	 $.messager.confirm('确认','是否删除该项?',function(r){  
        if (r){  
            $.post('${ROOT}/deleteAuths.do',{id:id},function(result){  
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
	 <table id="dg"></table>
 
    <div id="toolbar" style="padding:5px;height:auto">
    	<div>  
    		 人员ID: <input class="easyui-numberbox" type="text" id="id"  style="width:150px">
    		 人员名称: <input class="easyui-validatebox" type="text" id="name"  style="width:150px">
    		 人员状态: <select class="easyui-combobox" id="status" style="width:150px" data-options="panelHeight:'auto'">
    		  		 <option value="">-请选择-</option>
                 	 <option value="1">启用</option>  
         			 <option value="0">禁用</option>  
                 </select>    
                                                                       
             <a href="javascript:void(0)" onclick="searchItem();" class="easyui-linkbutton" iconCls="icon-search">查询</a>  
             <a href="javascript:void(0)" onclick="reset();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>    
             <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">添加</a>
         </div>
          <!-- <div>
         	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">添加</a>
         	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>          
         	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateRole()">修改角色</a>          
	       	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a>
         </div> -->
    </div>  
   	
    <script type="text/javascript">  
		function updateRole()
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
            
            window.location.href="${ROOT}/beginAuthRole.do?authId="+row[0].id;
		}
    </script>  
     

</body>
</html>
