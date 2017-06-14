<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找不到您需要的页面..</title>
<style type="text/css">
<!--
body {
	font-size: 13px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#dbebfa,endColorStr=#f9fcfd);
	margin: 0px;
}
-->
</style>
</head>

<body>
<table border=0 cellpadding=0 cellspacing=0 width="100%" height="100%">
<tr>
	<td align="center" style="padding-top:60px;">
    <img src="${pageContext.request.contextPath}/images/404.jpg" />    </td>
</tr>
<tr>
<form name=loading>
<td align=center>
<p id ="info"><font color=gray>正在载入首页，请稍候.......</font></p>
<p>
<input type=text name=chart size=46 style="font-family:Arial;
font-weight:bolder; color:gray;
background-color:white; padding:0px; border-style:none;">
<br>
<input type=text name=percent size=46 style="font-family:Arial;
color:gray; text-align:center;
border-width:medium; border-style:none;">
<script>var bar = 0
var line = "||";
var amount ="||";
count();
function count(){
bar= bar+2
amount =amount + line
document.loading.chart.value=amount
document.loading.percent.value=bar+"%";
if (bar<99)
{setTimeout("count()",20);}//这里修改载入时间
else
{
	 	var parentPage=window.parent;
	    var currentPage=window;
	    if(parentPage!=currentPage){
	    	
	    }else{
	    	window.location = "${pageContext.request.contextPath}";
	    }
	
}//这里改成你的网站地址
}
</script>
</p>
</td>
</form>
</tr>
</table>
</body>
</html>
