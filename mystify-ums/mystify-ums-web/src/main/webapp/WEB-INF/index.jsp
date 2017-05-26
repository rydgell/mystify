<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
  	request.setAttribute("ROOT", host);
%>
<%--设置相对路径 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统管理后台登录</title>
<style>
	body { width:100%; height:100%;margin:0px; padding:0px; font-size:12px;  *text-align:center;  font-family:"微软雅黑", Microsoft YaHei;color:#333333; position: relative; }
	ol,ul,li,dl,dt,dd,form,p,h1,h2,h3,h4,h5{ margin:0px; padding:0px;}
	h1,h2,h3,h4,h5 { font-weight:normal;}
	ol,ul,li{ list-style:none;}
	img{ border:none;}
	a{ color:#616161; text-decoration:none;}
	a:hover{ color:#137fef;}
	input,textarea{ outline:0; resize:none;}
	input,td{ margin:0; padding:0;font-family:"微软雅黑", Microsoft YaHei;}
	body{background-color: #333333;}
	.loginMaiin {margin: 0 auto; width:380px; padding-top: 95px;}
	.loginArea {border-radius: 5px; background-color: #FFF;}
	.loginTitle {height:50px; line-height: 50px; text-align: center; color: #FFF; font-size: 20px; font-family:"微软雅黑", Microsoft YaHei; background-color: #3195B9; border-bottom:1px solid #dfb800; border-top-left-radius: 3px;border-top-right-radius: 3px;}
	.loginForm {width:320px; margin: 30px auto 0; padding-bottom: 30px;}
	.loginInput {width:278px; height:43px; *line-height: 43px; padding: 0 5px 0 39px; margin-bottom: 15px; border: 1px solid #dddddd; border-radius: 3px;font-family:"微软雅黑", Microsoft YaHei; font-size: 14px; color: #aaaaaa;}
	#TxtPassword{background: url(images/login_inputbg01.jpg) no-repeat 15px center;}
	#TxtUserName{background: url(images/login_inputbg02.png) no-repeat 14px center;}
	.loginButton {width:320px; height:45px; line-height: 45px; text-align: center; background-color: #3195B9; color: #FFF; font-size: 20px;font-family:"微软雅黑", Microsoft YaHei; border:0; border-radius: 3px; cursor: pointer;}  
</style>
 
	<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/icon.css">
	<META http-equiv=Content-Type content="text/html; charset=UTF-8">
	<META content="MSHTML 6.00.6000.16674" name=GENERATOR>
</head>

<script type="text/javascript" src="${ROOT}/js/jquery-1.7.2.min.js"></script>
<script src="${ROOT}/js/jquery-easyui/jquery.easyui.min.js"></script>	
<script type="text/javascript">
var root_path = '${ROOT}';
		
		$(function(){
					var info = '${param.loginerror}';
					if(info!=null && info=="yes"){
						$.messager.alert('验证失败','用户名或密码错误','error');
					}
				}
		);
		
		function check()
		{ 
			var username = $("#username").val();
			var password=$("#password").val();
		
			if(username==null || username=="")
			{
				/* alert("请输入用户名!"); */
				return false;
			}
			
			else if(password==null ||password=="")
			{
						
				/* alert("请输入密码!"); */
				return false;
			}
			return true;
		}
		
		function loginSys()
		{
		 	 
			$("#form1").submit();
				
		}
		
		function refreshTop()
		{
			//避免在session失效时，在子页面显示登录界面,在界面一加载后，就检查
			//当前页面是否与父页面相同
			//如果不相同,就取父界面，并使父界面重定向到登陆界面
			 var currentHref="${ROOT}/login.do";  
		    var parentPage=window.parent;
		    var currentPage=window;
		    var topPage;
		    while(parentPage!=currentPage){
		        topPage=parentPage;
		        currentPage=parentPage;
		        parentPage=parentPage.parent;
		    }
		   if(topPage){
		       topPage.location.href=currentHref;
		   }
		}
</script>
	
	<body  onload="refreshTop()">
		<form id="form1" name="form1" method="post" onsubmit="check();" action="${ROOT}/login.do" >
		<div class="loginMaiin">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<div class="loginArea">
				<div class="loginTitle">用户登录3</div>
				<div class="loginForm">
						<input type="text" class="loginInput" id="username" name ="username" placeholder="用户名">
						<input type="password" class="loginInput" id="password" name ="password" placeholder="密码">
						<button class="loginButton" onclick='javascript:loginSys();'>登录</button>
				</div>
			</div>
		</div>
	</form>
		 
	</body>
 
</HTML>
