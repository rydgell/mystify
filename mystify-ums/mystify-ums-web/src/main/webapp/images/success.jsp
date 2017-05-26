<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  	String path = request.getContextPath();
  	String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
  	request.setAttribute("ROOT", host);
  	request.setAttribute("JS_ROOT", host + "/js");
  	request.setAttribute("CSS_ROOT", host + "/css");
  	request.setAttribute("IMAGE_ROOT", host + "/images");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta id="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=1.0; user-scalable=no" name="viewport">
 
<link rel="stylesheet" href="${CSS_ROOT}/global.css" />
<%-- <script src="${JS_ROOT}/jquery-1.7.2.min.js"></script> --%>
 
<title>提交成功</title>
 
</head>
 
<body>
 
<div id="main">
	<div class="header">
		<a href="" class="back_btn"><img src="${IMAGE_ROOT}/back_btn.png" height="100%" /></a>
		<span>充值</span>
	</div>
	<div class="message_success">
		<img src="${IMAGE_ROOT}/success.png" class="icon" />
		<span class="text">充值进行中,请稍后</span>
		</br>
		<span class="text"><img src="loading-gif.gif"></span>
	</div>
	<!-- <div class="button">返回游戏</div> -->
</div>
 
<script> 
$(document).ready(function(){
	$("#quit").bind('click',function(){
		window.close();
	});
});
</script>
 
</body>
</html>

