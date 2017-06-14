<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<%@ include file="/common/jsp/jquery-easyui.jsp"%>  
  
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>组织管理</TITLE>
<script src="${ROOT}/js/comm.js" type="text/javascript"></script>	
<script type="text/javascript" src="${ROOT}/js/organization/organization.js"></script>
 
</head>

<body style="width:100%;height: 100%;" > 
    <div id="toolbar" style="padding:5px;height:auto">
    	<div>  
    		  组织名称:<input class="easyui-validatebox" type="text" id="name" name="name" style="width:200px">
             <a href="javascript:void(0)" onclick="searchState();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
             <a href="javascript:void(0)" onclick="resetCondition();" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addItem()">添加</a>
         </div>
          
    </div>   
 
     <table id="dg">  </table>   
     
     <div id="dlg" class="easyui-dialog" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:410px;height:200px;padding:10px 20px"  buttons="#dlg-buttons">
    	<div class="ftitle">&nbsp;</div>   
        <form id="fm" method="post" novalidate action="#">   
           <input type="hidden" id="id" name="id" value=""/>   
           <table style="width: 100%">
				<tr style="width: 100%">
					<td style="text-align: right;width:80px"><label>组织名称:</label></td>
				    <td>
				  	 <input id="name" name="name" required="true" validType="length[0,30]" invalidMessage="不能超过30个字符!"  class="easyui-textbox" style="width:200px" value="" >
				   </td>
				</tr>
				<tr style="width: 100%;height:8px;"></tr>
				 <tr style="width: 100%">
					<td style="text-align: right;width:80px"><label>组织描述:</label></td>
				    <td>
				  	 <input id="description" name="description" required="true" validType="length[0,30]" invalidMessage="不能超过30个字符!"  class="easyui-textbox" style="width:200px" value="" >
				   </td>
				</tr>
				<tr style="width: 100%;height:8px;"></tr>
			</table>                
        </form> 
          <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveItem()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>  
	    </div>
    </div>
</body>  
</html>