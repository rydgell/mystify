<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>评测系统管理后台</TITLE> 
<%@ include file="/common/jsp/init.jsp"%> 
<script type="text/javascript">
	window.ROOT = '${ROOT}';
</script>
<script type="text/javascript" src="${ROOT}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/mainframe.js?<%=new Date().getTime()%>"></script>
<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ROOT}/css/style.css">
<link rel="stylesheet" type="text/css" href="${ROOT}/css/default.css"/>
<script type="text/javascript" src="${ROOT}/js/jquery-easyui/jquery.easyui.min.js"></script>
	<SCRIPT type="text/javascript">

		$(function()
			{
			$("body").layout('collapse','east');
				//得到菜单数据
				 openPwd();
				  $('#editpass').click(function() 
				  	{
                		$('#w').window('open');
            		});
            		 $('#btnEp').click(function() {
               			 serverLogin();
            		})
            		$('#btnCancel').click(function(){closePwd();})
            		
            		     $('#loginOut').click(function() {
                				$.messager.confirm('系统提示', '您确定要退出本次登录吗?',
                				 function(r) {
			
					                    if (r) 
					                    {
					                        location.href = '${ROOT}/sso/logout';	//如果点击退出,这里就先直接退出系统，
					                    }
               				 });
            })
         
            
			});
		 
	</SCRIPT>
	

</HEAD>
<BODY class="easyui-layout" style="overflow-y: hidden;width:100%;" scroll="no">
	<!-- 浏览器没有启用script的话 -->
	<noscript>
	    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;"> <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' /> </div>
	</noscript>
	<!-- 系统界面最上面的一行 -->
	<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(${ROOT}/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> 
      <span style="float:right; padding-right:20px;" class="head">
      <shiro:user>
		欢迎[<shiro:principal/>]登录，<a href="${ROOT}/sso/logout">安全退出</a>
	  </shiro:user>
    
    <!--   <a href="#" id="editpass">修改密码</a> 
      <a href="#" id="loginOut">安全退出</a> --></span>
       <span style="padding-left:10px; font-size: 16px; ">
       <img src="${ROOT}/images/blocks.gif" width="20" height="20" align="absmiddle" />系统管理后台</span> 
    </div>
    <!-- 最下面的一行 -->
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
      <div class="footer">系统管理后台</div>
    </div>
    
    <!-- 左边的一行 -->
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	      <div id="nav" class="easyui-accordion" fit="true" border="false"> 
	    	<!--导航内容 --> 
	      </div>
   </div>
   
   <!-- 主工作区 -->  
   <div id="mainPanle" region="center" style="overflow-y:hidden">
      <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
    	<div title="欢迎使用" style="padding:20px;overflow:hidden;  " >
 
 <div id="usalLink" class="easyui-panel" title="常用功能"   
	            data-options="iconCls:'icon-tip',closable:false,  
	                    collapsible:true,minimizable:false,maximizable:true,fit:true">  
    		</div>  
			   <!--  <div id="p" class="easyui-panel" title="查找菜单"  
	            data-options="iconCls:'icon-tip',closable:false,  
	                    collapsible:true,minimizable:false,maximizable:true,fit:true,href:'showqueryMenu.do'">  
    		</div>   -->
			   
        </div>
  	  </div>
   </div>
   <div region="east" title="其他功能" split="true" style="width:180px;overflow:hidden;">
      <div class="easyui-calendar"></div>
   </div>
    
 
  <!-- 在标签页上右击时的界面 -->
  <div id="mm" class="easyui-menu" style="width:150px;">
      <div id="mm-tabupdate">刷新</div>
      <div class="menu-sep"></div>
      <div id="mm-tabclose">关闭</div>
      <div id="mm-tabcloseall">全部关闭</div>
      <div id="mm-tabcloseother">除此之外全部关闭</div>
      <div class="menu-sep"></div>
      <div id="mm-tabcloseright">当前页右侧全部关闭</div>
      <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
      <div class="menu-sep"></div>
    </div>
</BODY>
