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
$(function() {
	 $('#type').combobox({
        valueField:'id', //值字段
        textField:'name', //显示的字段
        data:[{
			id: 0,
			name: '内部人员账号'
		},{
			id: 1,
			name: '托管中心账号'
		}],
        panelHeight:'auto',
        required:true,
        editable:false,//不可编辑，只能选择
        onChange:function(id){
       	 if(id==0){
         		$("#tr1").show(); 
         		$("#tr2").hide();  
     		}else{
     			$("#tr2").show(); 
         		$("#tr1").hide();  
     		}  
        }
     });
	 
	 var type =  ${entity.type};
	 $('#type').combobox('select',type);
	 var depositInfoId =  '${entity.depositInfoId}';
	 
 	var hadChosenCareCenterIds;
	 
	 $.ajax(
				{
					url:'${ROOT}/getHadChosenCareCenterIds.do',
					type:'POST',
					dataType:'json',
					async:false,
					success:function(msg)
					{
						hadChosenCareCenterIds = msg.hadChosenCareCenterIds;
					}
				}
			)
			 
	 $('#depositInfoId').combobox({
	        valueField:'id', //值字段
	        textField:'name', //显示的字段
	        url:'${ROOT}/carecenter/careCenterNameList.do?type=1',
	        panelHeight:200,
	        required:true,
	        mode:'local',
	        editable:false,
	        loadFilter: function (data) {
	        	var result =[];
	            for (var i = 0; i < data.length; i++) {
	            	if (hadChosenCareCenterIds.indexOf(','+data[i].id+',', 0) == -1||data[i].id==depositInfoId) {
	            		 result.push(data[i]);
	            	}
	            } 
	            return result;
	        }  
	     });
	 
	 if(type==0){
  		$("#tr1").show(); 
  		$("#tr2").hide();  
		}else{
		$("#tr2").show(); 
  		$("#tr1").hide();  
  		  if(depositInfoId!=''){
  				$('#depositInfoId').combobox('select',depositInfoId);  
  		}
	}  
	 
	 var ret;
		$.ajax(
			{
				url:'${ROOT}/listRole.do?status=1',
				type:'POST',
				dataType:'json',
				async:false,
				success:function(msg)
				{
					ret = msg.rows;
				}
			}
		)
	 var num = 6;
	 var size = ret.length
	 for (var index = 0; index < size; index++) {
		 $("#checkboxspan").append('<input name="userrole"  type="checkbox" value="'+ret[index].id+'" />'+ret[index].name+'&nbsp;&nbsp;&nbsp;');
		 var z = (index+1)%num
		 if(z == 0){
			 $("#checkboxspan").append('<br>');
		 }
    }
	 var userrole = ','+'${entity.userrole}'+',';
	 $("input[name='userrole']").map(function () {
		 if (userrole.indexOf(','+$(this).val()+',', 0) != -1) {  
	    	 $(this).attr("checked", "checked");  
	    } 
 	});
	 
	
	 
		
});

function updateAuth()
{
	var type = $('#type').combobox('getValue');
	if(type==1){
		var depositInfoId = $('#depositInfoId').combobox('getValue');
		if(depositInfoId==''){
			alert("请选择所属托管机构!");
			return;
		}
	}else{
		var userrole =$("input[name='userrole']:checked").map(function () {
	        return $(this).val();
	    }).get().join(',');
		
		if(userrole==''){
			alert("请选择所属角色!");
			return;
		}
	}
	
   $("#authForm").submit();

}

//验证密码强度
function checkPass(pass) {
	if (pass.length < 6) {
		return 0;
	}
	var ls = 0;
	if (pass.match(/([a-z])+/)) {
		ls++;
	}
	if (pass.match(/([0-9])+/)) {
		ls++;
	}
	if (pass.match(/([A-Z])+/)) {
		ls++;
	}
	if (pass.match(/[^a-zA-Z0-9]+/)) {
		ls++;
	}
	return ls;
}
function validatePassword(password){
	if (checkPass(password) < 3) {
		alert("密码复杂度不够，请使用小写字母加数字且长度>6位，例如:abcDEF123");			
		return false;
	}else{
		return true;
	}
}


	function check() {
		var username = $("#username").val();
		var password = $("#password").val();
		if(password != ''){
			if(!validatePassword(password)){
				return false;
			}
		}
		if (username == null || username == "") {
			alert("请输入用户名!");
			return false;
		} else {
			return true;
		}

	}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    	<td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">用户管理&gt; 修改用户</td>
		 
  </tr>
</table>
	
	
	<form id="authForm" action="${ROOT}/updateAuth.do" onsubmit="return check();"  method="post">
		<input type="hidden" name="id" value="${entity.id }">
		<table width="100%" cellpadding="5" cellspacing="1" bgcolor="#d0d7e5" style="margin:10px 0px;" align="center"   class="systemtable">
			<tr>
				<td align="right" bgcolor="#FFFFFF" style="width: 300px">用户名：</td>
				<input type="hidden"  name="name" value="${entity.name}" >
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="username" maxlength="30" value="${entity.name}" disabled="disabled"><font color="red">*</font></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">密码：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="password" maxlength="30" name="password"><label style="color:#C4C4C4">若此处不填写,不修改密码</label></td>
			</tr>
			<tr>
				<td align="right" bgcolor="#FFFFFF">是否启用：</td>
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
			
			<%-- <tr>
				<td align="right" bgcolor="#FFFFFF">账号类型：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select name="type" style="width:100px">
						<c:if test="${entity.type==1}">
							<option value="1" selected="selected">托管中心账号</option>
							<option value="0">内部人员账号</option>
						</c:if>
						<c:if test="${entity.type==0}">
							<option value="1">托管中心账号</option>
							<option value="0" selected="selected">内部人员账号</option>
						</c:if>
						
					</select>
				</td>
			</tr> --%>
			
			<tr>
				<td align="right" bgcolor="#FFFFFF">备注：</td>
				<td align="left" bgcolor="#FFFFFF"><input type="text" id="title" maxlength="300" name="remark" value="${entity.remark }"></td>
			</tr>
			
			<tr>
				<td align="right" bgcolor="#FFFFFF">账号类型：</td>
				<td align="left" bgcolor="#FFFFFF">
					<!-- <select class="easyui-combobox" name="type" id ="type" style="width:150px" data-options="panelHeight:'auto'">
						<option value="0">内部人员账号</option>
						<option value="1">托管中心账号</option>
					</select> -->
					<input type="text" id="type" name="type" style="width: 150px" class="easyui-validatebox">
				</td>
			</tr>
			
			<tr id="tr1" >
				<td align="right" bgcolor="#FFFFFF">所属角色：</td>
				<td align="left" bgcolor="#FFFFFF">
					<div id="checkboxspan">
				      
				   </div>
				</td>
			</tr>
			
			<tr id="tr2" >
				<td align="right" bgcolor="#FFFFFF">所属托管机构：</td>
				<td align="left" bgcolor="#FFFFFF">
					<select class="easyui-combobox" name='depositInfoId' id="depositInfoId"
				      			url="${ROOT}/carecenter/careCenterNameList.do?type=1"
								data-options="valueField:'id',textField:'name',panelHeight:200" style="width: 180px;" >
				    </select> 
				</td>
			</tr>
			
			<tr>				
				<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img
					src="${ROOT}/images/button_ok.gif" onclick="updateAuth();"/>&nbsp;&nbsp;
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