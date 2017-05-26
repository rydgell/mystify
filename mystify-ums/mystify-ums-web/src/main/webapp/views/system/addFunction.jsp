<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp" %>
<%@ include file="/common/jsp/jquery-easyui.jsp"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>  
<link href="${ROOT}/css/common_style.css" rel="stylesheet" type="text/css" />
<link href="${ROOT}/css/common.css" rel="stylesheet" type="text/css" />
<script src="${ROOT}/js/common.js" type="text/javascript"></script>
 
<script type="text/javascript">
$(function(){
queryMenus();
});

function insertFunction()
{
   var functionname = $("#functionname").val();
 
   if(functionname==null || functionname=="")
   {
   		alert("请输入权限名称!");
   		return  false;
   		
   }
   else
   {
   		$("#functionForm").submit();
   }
}

function saveItem(){  
    $('#functionForm').form('submit',{  
        url: '${ROOT}/addFunction.do',              
        onSubmit: function(){  
            return $(this).form('validate');  
        },  
        success: function(data){
    		javascript:history.back(-1);
        }  
    });  
}

function queryMenus()
{
	var item = $("#moduleid").val();
	if(item!=null && item!="")
	{
		var urlStr = "${ROOT}/queryModuleMenus.do";
		var moduleId = "moduleId=" + item;
		$.ajax({
			type:"POST",
			url:urlStr,
			data:moduleId,
			dataType:"text",
			success:function(data)
			{
			   $("#superid").empty();
			 
				if(data==null || data=="" || data=="[]") 
				{
					$("#superid").append("<option value=''>--此模块没有菜单--</option>"); 
				}
				else
				{
					var menus = eval('(' + data +')');
						 $("#superid").append("<option value=''></option>"); 
				
						$.each(menus,function(i,menu)
							{
								var menuoption = "<option value='"+menu.id+"'>"+menu.name+"</option>";
								 $("#superid").append(menuoption); 
							}
							
						);
					
				}
			}
			});
	}
}


function validateFunctionName()
{
	var reqUrl = "${ROOT}/validateFunctionName.do" ;
	var functionname = $("#functionname").val();
	
	if(functionname!=null && functionname!="")
	{
		var reqData = "name="+functionname
		$.ajax({
				 url : reqUrl,
				type:"POST",
				data:reqData,
				dataType:"text",
				success:function(msg)
				{
					if(msg=="yes")
					{
						alert("此权限名已存在!")
						$("#functionname").val("");
					}
				}
			});
	}
}


</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    	<td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">功能权限管理&gt; 新增权限</td>
  </tr>
</table>
	
	
	<form id="functionForm" action="${ROOT}/addFunction.do"  method="post">
		<table width="100%" cellpadding="5" cellspacing="1" bgcolor="#d0d7e5" style="margin:10px 0px;" align="center"   class="systemtable">
			<tr>
				<td align="right" bgcolor="#FFFFFF">权限名称：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" class="easyui-validatebox" required="true" id="functionname" maxlength="30" name="name" onblur="validateFunctionName();"><font color="red">*</font></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">权限状态：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="status">
						<option value="1">启用</option>
						<option value="0">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">URL</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" class="easyui-validatebox" style="width:250px" required="true" id="url" maxlength="300" name="url"></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">备注：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="remark" maxlength="300" name="remark"></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">排序：</td>
				<td align="left" bgcolor="#FFFFFF"> <input  id="sort" name="sort" class="easyui-numberbox"   validType="length[0,3]" invalidMessage="不能大于3位数的整数!" required="true" style="width:150px" value="999" >
				<label style="color:#C4C4C4">数字越大越靠前</label>
				</td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">所属模块：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="moduleid" id="moduleid" onchange="queryMenus();">
						<c:forEach items="${moudleList}" var="item">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<!-- <tr>
				<td align="right" bgcolor="#FFFFFF">父权限：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="superid"  id="superid">
					</select>
					<label style="color:#C4C4C4">当要增加权限为菜单时,此项不用选(仅添加按钮等功能需要选择).</label>
				</td>
			</tr> -->
			
			<tr>				
				<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img
					src="${ROOT}/images/button_ok.gif" onclick="saveItem()"/>&nbsp;&nbsp;
					<img
					src="${ROOT}/images/button_back_big.gif"
					onClick="javascript:history.back(-1);" />
				</td>
			</tr>
		</table>
		
		 <div id="win"><ul id="tt"></ul></div> 		
	</form>



<script>


	
</script>

</body>
</html>