<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%
	String path = request.getContextPath();
	String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
  	request.setAttribute("ROOT", host);
  	request.setAttribute("JS_ROOT", host + "/js");
  	request.setAttribute("CSS_ROOT", host + "/css");
  	request.setAttribute("IMAGE_ROOT", host + "/images");
%>
<%--设置相对路径 --%>
<script>
	var root_path = '${ROOT}';
</script>
	

