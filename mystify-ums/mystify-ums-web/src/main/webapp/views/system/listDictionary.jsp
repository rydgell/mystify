<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>  
<%@ include file="/common/jsp/jquery-easyui.jsp"%>
<script type="text/javascript">
$(function(){
	
});


var url="";
function redoItem(){
	$('#name').val("");	
	$('#keyName').val("");
	
	searchItem();
}

function searchItem(){
	  $('#dg').datagrid('load',{  
			name: $('#name').val(),
			keyName: $('#keyName').val()
   	}); 
}

function addItem(){				
	 $('#dlg').dialog('open').dialog('setTitle','添加字典信息');  
     $('#fm').form('clear');  
     
     url = '${ROOT}/system/saveDictionary.do'; 
}




function updateItem(){	
	var row = $('#dg').datagrid('getSelected');  
	if(row==null){            	
    	$.messager.alert('系统提示','请选择需要修改的数据!');
		return ;
    }
	if (row){      	      
		$('#fm').form('clear');
    	
        $('#dlg').dialog('open').dialog('setTitle','修改应用信息');  
        $('#fm').form('load',row);         
		
        url = '${ROOT}/system/saveDictionary.do?id='+row.id;
    }  
}

function saveItem(){  
    $('#fm').form('submit',{  
        url: url,                
        onSubmit: function(){  
            return $(this).form('validate');  
        },  
        success: function(data){
        	var result = eval('('+data+')');  
        	if (result.success){  
        		$('#dlg').dialog('close');  
                $('#dg').datagrid('reload');    // reload the user data  
            } else {  
                $.messager.show({   // show error message  
                    title: 'Error',  
                    msg: result.errorMsg  
                });  
            }  
        	                   
        }  
    });  
}  



function delItem(){
	var row = $('#dg').datagrid('getSelected');  
	if(row==null){            	
    	$.messager.alert('系统提示','请选择要删除的数据!');
		return ;
    }
	 if (row){      	
	        $.messager.confirm('确认','是否删除该项?',function(r){  
	            if (r){  
	                $.post('${ROOT}/system/deleteDictionary.do',{id:row.id},function(result){  
	                    if (result.success){  
	                    	$.messager.alert('系统提示','操作成功');
	                        $('#dg').datagrid('reload');    // reload the user data  
	                    } else {  
	                        $.messager.show({   // show error message  
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
</head>
<body>
<table id="dg" title="字典信息列表" class="easyui-datagrid"  
            url="${ROOT}/system/queryDictionary.do"           
            toolbar="#toolbar"  pagination="true" singleSelect="true" fitColumns="true"> 
   <thead>  
		<tr>  
			<th data-options="field:'id',hidden:true"></th>  
			<th data-options="field:'name',width:50">字典名称</th>
			<th data-options="field:'keyName',width:50">字典key</th> 
			<th data-options="field:'value',width:50">字典值</th>
			<th data-options="field:'status',width:50,
				formatter:function(value){if(value==1){return '启用';}else{return '禁用';}}">状态</th> 			
			<th data-options="field:'remark',width:50">备注</th>
			<th data-options="field:'createTime',width:50">创建时间</th>
			<th data-options="field:'createUser',width:50">创建用户</th> 
		</tr>  
	</thead>      
    </table>  
    <div id="toolbar" style="padding:5px;height:auto">
    	<div> 
    		<table style="width: 100%;">
    			<tr style="width: 100%;">
    				<td align="right" style="width: 120;">字典名称:</td>
    				<td><input class="easyui-validatebox" type="text" id="name" style="width:200px"></td>
    				<td align="right" style="width: 120;">字典key:</td>
    				<td><input class="easyui-validatebox" type="text" id="keyName" style="width:200px"></td>    				
    				<td align="right" >
    					<a href="javascript:void(0)" onclick="searchItem();" class="easyui-linkbutton" iconCls="icon-search">查询</a>  
             			<a href="javascript:void(0)" onclick="redoItem();" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
    				</td>    						
				  </tr>
			</table>     		
         </div>
          <div>
         	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addItem()">添加</a>
         	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateItem()">修改</a>          
	       	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delItem()">删除</a>      
        </div>
    </div>  
    
    	<div id="dlg" class="easyui-dialog" data-options="iconCls:'icon-save',modal:true,closed:true" 
   			 style="width:600px;height:400px;padding:10px 20px"  
             buttons="#dlg-buttons" >  
        <div class="ftitle">字典信息</div>  
        <form id="fm" method="post" novalidate>                       
           <table style="width: 100%">
				<tr style="width: 100%">
					<td style="width: 120;"><label>字典名称:</label> </td>
				    <td><input name="name" class="easyui-validatebox" required="true" style="width:200px">  </td>
				</tr>
				<tr style="width: 100%">
					<td style="width: 120;"><label>字典key:</label> </td>
				    <td><input name="keyName" class="easyui-validatebox" required="true" style="width:200px" >  </td>
				</tr>
				<tr style="width: 100%">
					<td style="width: 120;"><label>值:</label> </td>
				    <td><input name="value" class="easyui-validatebox" required="true" style="width:200px" >  </td>
				</tr>
				<tr style="width: 100%">
				    <td style="width: 120;"><label>状态:</label> </td>
				    <td><select class="easyui-combobox" name='status' required="true" editable="false"  >
				    	<option value="">-请选择-</option>
				    	<option value="1">启用</option>
				    	<option value="2">禁用</option>
				    </select>
				</tr>				
				<tr style="width: 100%">
				    <td style="width: 120;"><label>备注:</label> </td>
				    <td><textarea rows="3" cols="50" name="remark" style="font-size: 14px;"></textarea></td>
				</tr>
			</table>                
        </form> 
          <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveItem()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>  
	    </div>
    </div>
    
     



</body>
</html>