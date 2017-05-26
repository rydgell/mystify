<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>网站后台管理系统</TITLE>
<%@ include file="/common/jsp/jquery-easyui.jsp"%> 
<%@ include file="/common/jsp/uploadify.jsp"%>
</head>

<body>  
	<table id="dg" title="登录日志管理" class="easyui-datagrid"   
            url="${ROOT}/system/getLogLoginListJson.do"  
            toolbar="#toolbar" pagination="true"  fit="true"  pageSize="20"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>  
            <tr>  
                <th field="username" width="30">用户名称</th>
                <th field="remoteAddress" width="30">登录IP</th>
                <th field="createTime" width="30">登录时间</th>
            </tr>  
        </thead>  
    </table> 
    <div id="toolbar" style="padding:5px;height:auto">
    	<div>  
    		  用户名: <input class="easyui-validatebox" type="text" id="username" style="width:150px">
    		  登录IP: <input class="easyui-validatebox" type="text" id="remoteAddress" style="width:80px"> 
                                开始时间: <input class="easyui-datebox" id="startTime" style="width:100px">  
                                结束时间: <input class="easyui-datebox" id="endTime" style="width:100px">               
             <a href="javascript:void(0)" onclick="searchLog();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
             <a href="javascript:void(0)" onclick="resetCondition();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>   
         </div>
          
    </div>   
    <script type="text/javascript">    
		function resetCondition(){
			$('#username').val("");
			$('#remoteAddress').val("");
			$("#startTime").datebox('setValue','');
			$("#endTime").datebox('setValue','');
			
			$('#dg').datagrid('load',{  
				username: $('#username').val(),
	            remoteAddress: $('#remoteAddress').val(),  
	            startTime: $("#startTime").datebox('getValue'),
	            endTime: $("#endTime").datebox('getValue')  
	        }); 
		}
       	function searchLog(){
       		$('#dg').datagrid('load',{  
       			username: $('#username').val(),
       			remoteAddress: $('#remoteAddress').val(),  
       			startTime: $("#startTime").datebox('getValue'),
       			endTime: $("#endTime").datebox('getValue')  
       		});  
       	} 
    </script>  
     
</body>  
</html>