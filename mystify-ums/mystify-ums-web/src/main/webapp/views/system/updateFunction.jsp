<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
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

function updateFunction()
{
   	$("#functionForm").submit();
   
}

function saveItem(){  
    $('#functionForm').form('submit',{  
        url: '${ROOT}/updateFunction.do',              
        onSubmit: function(){  
            return $(this).form('validate');  
        },  
        success: function(data){
    		javascript:history.back(-1);
        }  
    });  
}

</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    	<td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">角色管理&gt; 修改角色</td>
  </tr>
</table>
	
	
	<form id="functionForm" action="${ROOT}/updateFunction.do"  method="post">
		<input type="hidden" name="id" value="${entity.id }">
		<table width="100%" cellpadding="5" cellspacing="1" bgcolor="#d0d7e5" style="margin:10px 0px;" align="center"   class="systemtable">
			<tr>
				<td align="right" bgcolor="#FFFFFF">权限名称：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="rolename" name="name" class="easyui-validatebox" required="true" maxlength="30"   value="${entity.name}" ><font color="red">*</font></td>
			</tr>
		
			<tr>
				<td align="right" bgcolor="#FFFFFF">权限状态：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="status">
						<c:if test="${entity.status==1}">
							<option value="1" selected="selected">启用</option>
							<option value="0">禁用</option>
						</c:if>
						<c:if test="${entity.status==0}">
							<option value="1">启用</option>
							<option value="0" selected="selected">禁用</option>
						</c:if>
						
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">URL</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" style="width:250px" class="easyui-validatebox" required="true" id="url" maxlength="300" name="url" value="${entity.url }"></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">备注：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="remark" maxlength="300" name="remark" value="${entity.remark }"></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">排序：</td>
				<td align="left" bgcolor="#FFFFFF"> <input  id="sort" name="sort" class="easyui-numberbox"   validType="length[0,3]" invalidMessage="不能大于3位数的整数!" required="true" style="width:150px" value="${entity.sort}" >
				<label style="color:#C4C4C4">数字越大越靠前</label>
				</td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">所属模块：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="moduleid">
						<c:forEach items="${moudleList}" var="item">
							<c:choose>
								<c:when test="${entity.moduleid eq item.id}">
									<option value="${item.id}" selected="selected">${item.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${item.id}">${item.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>				
				<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img
					src="${ROOT}/images/button_ok.gif" onclick="saveItem()"/>&nbsp;&nbsp;
					<img
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