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
</script>
</head>


<body style='overflow-y:hidden;overflow-x:hidden'>



<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cssposition">
  <tr>
    <td width="54"><img src="${ROOT}/images/subnav1.jpg" width="94" height="30" /></td>
    <td style="background:url(${ROOT}/images/subnav.jpg) repeat-x" class="subnavcss">常用功能</td>
    <td width="90" align="center" class="subnavcss" style="background:url(${ROOT}/images/subnav.jpg) repeat-x"> 
	<td align="right" width="3"><img src="${ROOT}/images/subnav2.jpg" width="3" height="30" /></td>
  </tr>
</table>
	<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0"   class="tabBox">
	  <tr  class="tabTitleMain">	    
	    <td align="center" class="table_top1">菜单ID</td>
	    <td align="center" class="table_top1">菜单名称</td>
<!-- 	    <td align="center" class="table_top1">模块</td> -->
	    <td align="center" class="table_top1">URL</td>
	    <td align="center" class="table_top1">进入</td>
	    <td align="center" class="table_top1">删除</td>
	  </tr>
	  <tbody>
	  	<c:choose>
		   <c:when test="null==functionList || functionList.isEmpty()">
				<tr  class="tabTextMain"  onmouseout="this.style.background='#FFFFFF';" onmouseover="this.style.background='#F4F7FA';">
		  			<td colspan="6" align="center">没有设置常用功能</td>
		  		</tr>
		   </c:when>
		   <c:otherwise>
				<c:forEach var="function" items="${functionList}" >
			  		<tr class="tabTextMain"  onmouseout="this.style.background='#FFFFFF';" onmouseover="this.style.background='#F4F7FA';">	  			
			  			<td align="center" class="table_bottom1">${function.id }</td>		
			  			<td align="center" class="table_bottom1">${function.name }</td>
		<!-- 	  			<td align="center" class="table_bottom1"><s:property value="#function.moduleid"/></td> -->
			  			<td align="center" class="table_bottom1">${function.url }</td>	
			  			<td align="center" class="table_bottom1"><a href="#" onclick="opentab('${function.id}','${function.name}','${function.url}','icon-log')">go</a> </td>	
			  			<td align="center" class="table_bottom1"><a href="#" onclick="delUsalLink('${function.id}')">删除</a> </td>					  			
			  		</tr>
			  	</c:forEach> 
		   </c:otherwise>
		  
		</c:choose> 	 	
	  </tbody>
	  </table>
	  

</body>
</html>