<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>  
<link href="${ROOT}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ROOT}/css/common.css" rel="stylesheet" type="text/css" />



<script src="${ROOT}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ROOT}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery.form.js"></script>


<script type="text/javascript">
	function findMenu()
	{
		var menuname = $("#name").val();
		if(menuname==null || menuname=="")
		{
			alert("请输入菜单名称进行查询");
			return false ;
		}
		else
		{
				var url = "queryMenuByName.do";
				var data= "menuname=" + menuname;
			
				var $table = $("#dataTable tr");//获取table的tr集合  
				var len = $table.length;//获取table的行数
			
				if(len>1)
				{
					for(var i =1 ;i<=len+1;i++)
					{
						$("#dataTable tr:eq("+1+")").remove();
					}
				}
				$.ajax({
						url:url,
						type:'POST',
						data:data,
						dataType:'json',
						success:function(msg)
						{
							if(msg==null || msg=="")
							{
								$("#dataTable").append("<tr class='tabTextMain'  onmouseout='this.style.background='#FFFFFF';'  onmouseover='this.style.background='#F4F7FA';'><td colspan='4' align='center'>没有您要查找的数据</td></tr>")
							}
							else
							{
								for(var i=0;i<msg.length;i++)
								{
									var menu = msg[i];
									$("#dataTable").append("<tr class='tabTextMain'  onmouseout='this.style.background='#FFFFFF';'  onmouseover='this.style.background='#F4F7FA';'><td align='center' class='table_bottom1'><td align='center' class='table_bottom1' id='menuname'>"+menu.name+"</td><td align='center' name='showname' class='table_bottom1' id='modulename'>"+menu.type+"</td><td align='center' class='table_bottom1' id='status'>"+menu.status+" </td><td align='center' class='table_bottom1'><a onclick=opentab("+menu.id+","+menu.name+","+menu.url+",'icon-page') href='#'>go</a> </td></tr>")
								}
							}
						}
					});
		}
		
	}
</script>
</head>


<body style='overflow-y:hidden;overflow-x:hidden'>
<%-- <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    <td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">查找菜单</td>
    <td width="90" align="center" class="subnavcss" style="background:url(${ROOT}/images/subnav.jpg) repeat-x"> 
	<td align="right" width="3"><img src="${ROOT}/images/subnav2.jpg" width="3" height="30" /></td>
  </tr>
</table> --%>


<table width="900" border="0" cellspacing="0" cellpadding="0"  style="margin:20px 10px;">
	  	  <tr >    	  	    
		    <td align="right" width="10%">菜单名称: &nbsp;&nbsp;&nbsp;</td>		
		    <td align="left" width="15%"><input type="text" name="menuname" id="queryMenumenuname"></td>	    	   
		    <td><img src="${ROOT}/images/toolsSearchs.gif" width="82" height="22"  id="queryButtonId" onclick="findMenu();" style="cursor:pointer;" /></td>
		   </tr>
	</table>

	<table id="queryMenudataTable" width="100%" border="0" cellspacing="0" cellpadding="0"   class="tabBox">
	  <tr  class="tabTitleMain">	    

	    <td align="center" class="table_top1">名称</td>
	    <td align="center" class="table_top1">模块</td>
	    <td align="center" class="table_top1">URL</td>
	   
	    <td align="center" class="table_top1">进入</td>
	    <td align="center" class="table_top1">设置成常用功能</td>
	  </tr>
	  <tbody>
	  	  		<tr  class="tabTextMain"  onmouseout="this.style.background='#FFFFFF';" onmouseover="this.style.background='#F4F7FA';">
	  			<td colspan="5" align="center">没有你要查找的数据</td>
  				</tr>
	  
	  
<!--	  	<s:if test="null==functionList || functionList.isEmpty()">-->
<!--	  		<tr  class="tabTextMain"  onmouseout="this.style.background='#FFFFFF';" onmouseover="this.style.background='#F4F7FA';">-->
<!--	  			<td colspan="6" align="center">没有你要查找的数据</td>-->
<!--	  		</tr>-->
<!--	  	</s:if>-->
<!--	  	<s:else>-->
<!--	  	<s:iterator var="function" value="functionList">-->
<!--	  		<tr class="tabTextMain"  onmouseout="this.style.background='#FFFFFF';" onmouseover="this.style.background='#F4F7FA';">	  			-->
<!--	  			<td align="center" class="table_bottom1"><input type="checkbox" name="selectid" value="${function.id}"/></td>  	-->
<!--	  			<td align="center" class="table_bottom1"><s:property value="#function.id"/> </td>		-->
<!--	  			<td align="center" class="table_bottom1"><s:property value="#function.name"/> </td>-->
<!--	  			<td align="center" class="table_bottom1">-->
<!--	  			<s:if test="#function.status==1">可用</s:if>-->
<!--	  			<s:else>不可用</s:else>-->
<!--	  			</td>-->
<!--	  			<td align="center" class="table_bottom1"><s:property value="#function.url"/> </td>	-->
<!--	  			<td align="center" class="table_bottom1"><s:property value="#function.moduleid"/> </td>					  			-->
<!--	  		</tr>-->
<!--	  	</s:iterator> -->
<!--	  	</s:else>  	  	 	-->
	  </tbody>
	  </table>
</body>
</html>