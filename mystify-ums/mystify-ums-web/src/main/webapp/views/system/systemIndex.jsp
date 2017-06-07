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

		 //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        
                //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }
        
                //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }
            
            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }
            
            if (!validatePassword($newpass.val())) {    			
    			return false;
    		}
 
            $.post('${ROOT}/changePassword.do?password=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + $newpass.val(), 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
        }
     
                
        function validatePassword(password){
    		if (checkPass(password) < 3) {
    			alert("密码复杂度不够，请使用小写字母加数字且长度>6位，例如:abcDEF123");			
    			return false;
    		}else{
    			return true;
    		}
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
      <span style="float:right; padding-right:20px;" class="head">欢迎您, <sec:authentication property="principal.username"/> 
      <a href="#" id="editpass">修改密码</a> 
      <a href="#" id="loginOut">安全退出</a></span>
       <span style="padding-left:10px; font-size: 16px; ">
       <img src="${ROOT}/images/blocks.gif" width="20" height="20" align="absmiddle" />评测系统管理后台</span> 
    </div>
    <!-- 最下面的一行 -->
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
      <div class="footer">评测系统管理后台</div>
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
	                    collapsible:true,minimizable:false,maximizable:true,fit:true,href:'showUsalLink.do'">  
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
    
   
<!--修改密码窗口-->
<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
      <div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
          <table cellpadding=3>
        <tr>
              <td>新密码：</td>
              <td><input id="txtNewPass" type="Password" class="txt01" /></td>
            </tr>
        <tr>
              <td>确认密码：</td>
              <td><input id="txtRePass" type="Password" class="txt01" /></td>
            </tr>
      </table>
        </div>
    <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
     <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > 确定</a> 
     <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a> 
     </div>
  </div>
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
