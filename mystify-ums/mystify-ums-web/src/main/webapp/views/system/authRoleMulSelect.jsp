<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp" %>
<HTML>
<HEAD>
<TITLE>人员角色选择</TITLE>

<link href="${ROOT}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ROOT}/css/common.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="${ROOT}/cluetip/jquery.cluetip.css" type="text/css" />


<script src="${ROOT}/js/common.js" type="text/javascript"></script>
<script src="${ROOT}/js/twoselect.js" type="text/javascript"></script>
<script type="text/javascript" src="${ROOT}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery.form.js"></script>

<script src="${ROOT}/cluetip/jquery.hoverIntent.js" type="text/javascript"></script> <!-- optional -->
<script src="${ROOT}/cluetip/jquery.cluetip.js" type="text/javascript"></script>


<SCRIPT language=JavaScript>
$(function(){  
/*
        var all=[{value:'1',parent:'',display:'display1',index:1},  
                    {value:'2',parent:'',display:'display2',index:2},  
                    {value:'3',parent:'',display:'display3',index:3},  
                    {value:'4',parent:'',display:'display4',index:4},  
                    {value:'5',parent:'',display:'display5',index:5}];  
        var selected=[  
                    {value:'4',parent:'',display:'display4',index:4},  
                    {value:'5',parent:'',display:'display5',index:5}];  
             */
             
             var all;
             var selected;  
             var authIds = $("#authId").val(); 
              $.ajax({
              		
              		url:'queryAllRoleForJson.do',
              		type:'POST',
              		async:false,	//同步的
              		dataType:'text',
              		success:function(msg)
              		{
              			all = eval('(' + msg + ')'); 
              		}//请求所有的
              });   
                 $.ajax({
              		url:'assginedRoleListForJson.do',
              		data:'authId=' + authIds,
              		type:'POST',
              		async:false,	//同步的
              		dataType:'text',
              		success:function(msg)
              		{
              			selected = eval('(' + msg + ')'); 
              		}//请求所有的
              }); 
        generate2Select("test1",all,selected,false);  
});  

function doAuthRoles()
{
	var selected = getSelectVal();
	var authId = $("#authId").val();
	
	var requrl = null;
	var reqdata = null;
	if(selected.length==1 && $(selected[0]).val()=='0')
	{
		//默认的没有时，后台自动添加的话，
		requrl = 'deleteAuthRole.do';
		reqdata='authId='+authId; 
	}
	else if(selected!=null &&selected!="undefined" && selected.length>0)
	{
		requrl = 'updateAuthRole.do';
		var selVal = "";
		for(var i=0;i<selected.length;i++)
		{
			
			selVal =  selVal+ $(selected[i]).val()+",";
		}
		reqdata = 'authId='+authId + '&roleId=' + selVal;
	}
	else
	{
		requrl = 'deleteAuthRole.do';
		reqdata='authId='+authId;
		//一个都没有选择
	
	}
		$.ajax({
			url:requrl,
			type:'POST',
			data:reqdata,
			dataType:'text',
			success:function(msg)
			{
				if(msg=='success')
				{
					alert('配置人员角色成功!');
				}
				else
				{
					alert('配置人员角色失败!');
				}
			}
		});
}

function goBack()
{
history.back();
}

</SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></HEAD>

<BODY style="MARGIN: 0px" scroll=no topmargin="0" leftmargin="0">

<input type="hidden" id="authId" name="authId" value="${authId }"/>


<table id="dataTable" style="display: block;" width="100%" border="0" cellspacing="0" cellpadding="0"   class="tabBox">
<tr>
	<td colspan="4" >
		当前配置人员:${authName }
	</td>
	</tr>
	<tr>
	<td colspan="4" >
		<div id="test1">  
		</div>
	</td>
	</tr>
	  	<tr>				
			<td colspan="4" height="45" align="center" bgcolor="#FFFFFF"><img
				src="${ROOT}/images/button_ok.gif" onclick="doAuthRoles();"  style="cursor: pointer"/>&nbsp;&nbsp;
				<img
				src="${ROOT}/images/button_back_big.gif"
				onClick="javascript:goBack();"  style="cursor: pointer"/>
			</td>
		</tr>
 </table>
</BODY>

</HTML>
