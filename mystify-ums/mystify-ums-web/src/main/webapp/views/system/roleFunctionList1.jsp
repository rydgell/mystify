<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>  
<link href="${ROOT}/css/common.css" rel="stylesheet" type="text/css" />
<script src="${ROOT}/js/common.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${ROOT}/js/jquery-easyui/themes/default/eas
	yui.css">
<link rel="stylesheet" type="text/css"
	href="${ROOT}/js/jquery-easyui/themes/icon.css">

<script type="text/javascript" 
	src="${ROOT}/js/jquery-easyui/jquery.min.js"></script>	
<script type="text/javascript"
	src="${ROOT}/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery.form.js"></script>

<script type="text/javascript">

function initAuth(){
	$("#authForm").submit();
}

function addAuth(){
	location.href='${ROOT}/beginAddAuths.do';
}

function update(){
	var boxs = document.getElementsByName("selectid");
	var num=0;
	var selvalue;
	for(var i=0;i<boxs.length;i++){
		  if(boxs[i].checked){
			  num++;
			  selvalue=boxs[i].value;
		  }
	}	
	if(num<=0){
		alert("请选择需要修改的数据");
	}else if(num>1){
		alert("只能选择一条数据修改");
	}else{
		window.location.href="${ROOT}/beginUpdateAuths.do?ids="+selvalue;
	}
}

function updateRole(){
	var boxs = document.getElementsByName("selectid");
	var num=0;
	var selvalue;
	for(var i=0;i<boxs.length;i++){
		  if(boxs[i].checked){
			  num++;
			  selvalue=boxs[i].value;
		  }
	}	
	if(num<=0){
		alert("请选择需要分配角色的用户");
	}else if(num>1){
		alert("一次只能给一个用户分配角色");
	}else{
		window.location.href="${ROOT}/beginAuthRole.do?authId="+selvalue;
	}
}

function checkAll()
{
	var isCheck = $("#checkAll").is(":checked");

	if(isCheck)
	{
		//将checkbox全部选中
		$(":checkbox").attr("checked","checked");
	}
	else
	{
		
		$(":checkbox").attr("checked",false);
	}
	
}

$(function(){
	
	var ret;
	var roleId='${roleId}';
	$.ajax(
		{
			url:'${ROOT}/getRoleLinkRelationList.do',
			type:'POST',
			data:'roleId=' + roleId,
			dataType:'text',
			async:false,
			success:function(msg)
			{
				ret = eval('(' + msg + ')');
			}
		}
	)
	
	$("#tt").tree({
		
			data: ret,
			animate:true,
			checkbox:true,
			cascadeCheck:true,
			
			//url:'${ROOT}/getRoleLinkRelationList.do',
		}
	);
});

function assginFunction()
{
	var chks = $("[name='selectid']:checked}"); // 先找到所有选择的
	if(chks.length==0)
	{
		alert("请选择要赋的角色");
		return ;
	}
	var vals='';
	for(var i=0;i<chks.length;i++)
	{
		vals+=chks[i].value+"@_@";
	}
	var urlStr ="${ROOT}/assginFunction.do";
	var roleIdVal = $("#roleId").val();
	var reqdata = "roleId=" + roleIdVal + "&funcId=" + vals
	$.ajax(
			{
				type:"POST",
				url:urlStr,
				data:reqdata,
				dataType:"text",
				success:function(msg)
				{
					
					if(msg=="success")
					{
						alert("赋权限成功!");
						window.location.href="${ROOT}/beginRoleFunc.do?roleId="+roleIdVal;
					}
					else
					{
						alert("赋权限失败!");
						window.location.href="${ROOT}/beginRoleFunc.do?roleId="+roleIdVal;
					}
				}
			}
			);
}
//取消角色
function cancelFunction()
{
	if(confirm("确定要取消此角色的这些权限?"))
	{
		var chks = $("[name='selectid']:checked}"); // 先找到所有选择的
		if(chks.length==0)
		{
			alert("请选择要取消的权限");
			return ;
		}
		var vals='';
		for(var i=0;i<chks.length;i++)
		{
			vals+=chks[i].value+"@_@";
		}
		
		var urlStr ="${ROOT}/cancelRoleFunction.do";
		var roleIdVal = $("#roleId").val();
		var reqdata = "roleId=" + roleIdVal + "&funcId=" + vals
		$.ajax(
			{
				type:"POST",
				url:urlStr,
				data:reqdata,
				dataType:"text",
				success:function(msg)
				{	
					
					if(msg=="success")
					{
						alert("取消角色权限成功!");
						window.location.href="${ROOT}/beginRoleFunc.do?roleId="+roleIdVal;
					}
					else
					{
						alert("取消角色权限失败!");
						window.location.href="${ROOT}/beginRoleFunc.do?roleId="+roleId;
					}
				}
			}
			);
	}
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
					
						var selectSuperid = $("#selectSuperid").val();
				
						if(selectSuperid==null || selectSuperid=="")
						{
							$("#superid").append("<option value=''>--全部--</option>"); 
							$.each(menus,function(i,menu)
								{
									var menuoption = "<option value='"+menu.id+"'>"+menu.name+"</option>"
									 $("#superid").append(menuoption); 
								});
						}
						else
						{
							$("#superid").append("<option value=''>--全部--</option>"); 
							$.each(menus,function(i,menu)
							{
								if(selectSuperid==menu.id)
								{
									var menuoption = "<option value='"+menu.id+"' selected='selected'>"+menu.name+"</option>"
									 $("#superid").append(menuoption); 
								 }
								 else
								 {
								 	var menuoption = "<option value='"+menu.id+"'>"+menu.name+"</option>"
									 $("#superid").append(menuoption); 
								 }
							});
						}	
					
				}
			}
			});
	}
}


function doRelation()
{
		var nodes = $('#tt').tree('getChecked');
		var funcIds = "";
	 	if(nodes!=null && nodes.length>0)
	 	{
			 	for(var i=0;i<nodes.length;i++)
		 	{
		 		
		 		funcIds +=  nodes[i].id + ",";	//得到所有选择的
		 	}
	 	}
	 	var roleId = $("#roleId").val();
	 	var data = "funcId=" + funcIds + "&roleId=" + roleId;
	 	$.ajax(
		{
			url:'${ROOT}/doRoleFunctions.do',
			data:data,
			dataType:'text',
			type:'POST',
			success:function(msg)
			{
				if(msg=='success')
				{
					alert('分配角色链接成功!');
				}
				else
				{
					alert('分配角色链接失败!');
				}
			}
		});
	 	
}
 function collapseAll(){
	 $('#tt').tree('collapseAll');
 }

 function expandAll(){
	 $('#tt').tree('expandAll');
 }

</script>
</head>


<body style='overflow-y:hidden;overflow-x:hidden'>



<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    <td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">角色权限管理(当前配置角色:${roleName })</td>
    <td width="90" align="center" class="subnavcss" style="background:url(${ROOT}/images/subnav.jpg) repeat-x"> 
    <img src="${ROOT}/images/button_back.gif" width="51" height="18" onclick="javascript:history.back();"/></td> 
	<td align="right" width="3"><img src="${ROOT}/images/subnav2.jpg" width="3" height="30" /></td>
  </tr>
</table>

<input type="hidden" id="selectSuperid" value="${superid}"/>
<form id="myform" action="queryRoleAssignFunction.do" method="post">
<input type="hidden" name="roleId" id="roleId" value="${roleId }"/>
<input type="hidden" name="role.id" id="roleid" value="${role.id}"/>
	角色名: ${roleName }
	<br />
	<br />
	<a href="#" onclick="expandAll();return false;">展开</a>&nbsp;/&nbsp;<a href="#" onclick="collapseAll();return false;">收起</a>
	    <ul id="tt" ></ul>  
	    
	    
<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0"   class="tabBox">
	  	<tr>				
			<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img
				src="${ROOT}/images/button_ok.gif" onclick="javascript:doRelation();"  style="cursor: pointer"/>&nbsp;&nbsp;<img
				src="${ROOT}/images/button_back_big.gif"
				onClick="window.location='${ROOT}/beginRoleList.do'"  style="cursor: pointer"/>
			</td>
		</tr>
 </table>
	    
</form>
<script>
	
	
	function search()
	{
	
		$("#myform").submit();
			
	}
	
</script>

</body>
</html>