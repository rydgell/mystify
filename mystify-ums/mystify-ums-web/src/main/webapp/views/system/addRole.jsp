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

});

function insertRole()
{
/*
   var rolename = $("#rolename").val();
 
   if(rolename==null || rolename=="")
   {
   		alert("请输入角色名称!");
   		return ;
   }
   else
   {*/
   		$("#roleForm").submit();
  // }
}

function check()
{
   var rolename = $("#rolename").val();
   if(rolename==null || rolename=="")
   {
   		alert("请输入角色名称!");
   		return false;
   }
   else
   {
   		return true;
   }
}

function validateRoleName()
{
	var reqUrl = "${ROOT}/validateRoleName.do" ;
	var rolename = $("#rolename").val();
	
	if(rolename!=null && rolename!="")
	{
		var reqData = "name="+rolename
		$.ajax({
				 url : reqUrl,
				type:"POST",
				data:reqData,
				dataType:"text",
				success:function(msg)
				{
					if(msg=="yes")
					{
						alert("此角色名称已存在!")
						$("#rolename").val("");
					}
				}
			});
	}
}

function resetInput()
{
	$('#rolename').val('');
	$('#enabled').attr('selected',true);
	$('#remark').val('');
}

</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    	<td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">角色管理&gt; 新增角色</td>
  </tr>
</table>
	
	
	<form id="roleForm" action="${ROOT}/addRole.do" onsubmit="return check();" method="post">
		<table width="100%" cellpadding="5" cellspacing="1" bgcolor="#d0d7e5" style="margin:10px 0px;" align="center"   class="systemtable">
			<tr>
				<td align="right" bgcolor="#FFFFFF">角色名称：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="rolename" class="easyui-validatebox" required="true" name="name" maxlength="30" onblur="validateRoleName()"><font color="red">*</font></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">角色状态：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="status">
						<option id="enabled" value="1">启用</option>
						<option value="0">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">备注：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="remark" maxlength="300" name="remark"></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">操作权限：</td>
				<td align="left" bgcolor="#FFFFFF">
				<input name="optAuths"   type="checkbox" value="ROLE_CONTRACT" />合同审核
				<input name="optAuths"   type="checkbox" value="ROLE_AUDIT" />审核
				<input name="optAuths"   type="checkbox" value="ROLE_DELIVERY" />发货
				<input name="optAuths" 	 type="checkbox" value="ROLE_PAYAUDIT" />支付审核
				<br>
				<input name="optAuths" 	 type="checkbox" value="ROLE_CANCEL" />取消
				<input name="optAuths"   type="checkbox" value="ROLE_PAY" />收款
				<input name="optAuths"   type="checkbox" value="ROLE_THEMEEDIT" />主题编辑
				<input name="optAuths"   type="checkbox" value="ROLE_THEMEDEL" />主题删除
				<label style="color:#C4C4C4">(只对特定页面有效)</label>
				</td>
			</tr>
			<tr>				
				<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img style="cursor: pointer;"
					src="${ROOT}/images/button_ok.gif" onclick="insertRole()"/>&nbsp;&nbsp;
					<img style="cursor: pointer;"
					src="${ROOT}/images/button_back_big.gif"
					onClick="javascript:history.back(-1);" />
				</td>
			</tr>
		</table>
	</form>



<script>


	
</script>

</body>
</html>