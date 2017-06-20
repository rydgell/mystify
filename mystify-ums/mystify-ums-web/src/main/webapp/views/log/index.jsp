<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>日志管理</title>
	<jsp:include page="/views/common/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<!-- <div class="panel panel-default">
		<div class="panel-heading" data-toggle="collapse" data-parent="#accordion" 
				    data-target="#collapseTwo">
			<h6 class="panel-title">
				日志记录
			</h6>
		</div>
		<div id="collapseTwo" class="panel-collapse collapse">
			<div class="panel-body form-horizontal" id="searchTools">
                           <div class="form-group">
                               <div class="col-lg-2">
                                   <input type="text" id="description" name="description" class="form-control input-sm m-bot3" placeholder="描述 "> 
                               </div>
                           </div>
                           <div class="form-group">
                               <div class="col-lg-2">
                                   <input type="text" id="description" name="description" class="form-control input-sm m-bot3" placeholder="描述 "> 
                               </div>
                           </div>
                           <div class="form-group">
                               <div class="col-lg-offset-2 col-lg-10">
                                   <button type="button" class="btn btn-primary" onclick="searchData()">查询</button>
                                   <button type="button" class="btn btn-default" onclick="resetSearch()">重置</button>
                               </div>
                           </div>
                       </div>
		</div>
	</div> -->
	<div id="toolbar">
		<shiro:hasPermission name="upms:log:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除日志</a></shiro:hasPermission>                 
	</div>
	
	<table id="table"></table>
</div>
<jsp:include page="/views/common/footer.jsp" flush="true"/>
<script src="${basePath}/plugins/bootstrap-table/extensions/toolbar/bootstrap-table-toolbar.min.js"></script>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/log/list',
		height: getHeight(),
		pagination: true,
		paginationLoop: true,
		sidePagination: 'server',
		striped: true,
		search: true,
		showRefresh: true,
		showColumns: true,
		minimumCountColumns: 2,
		clickToSelect: true,
		silentSort: false,
		escape: true,
		searchOnEnterKey: true,
		idField: 'id',
	    pageSize: 10,  //每页显示的记录数  
	    pageNumber:1, //当前第几页  
	    pageList: [10, 20, 30,50],  //记录数可选列表  
	    toolbar: '#toolbar', 
	    //queryParams:queryParams,
	    mobileResponsive:true,
        checkOnInit:true,
        advancedSearch:true,
        idTable:'advancedTable',
		columns: [
			{field: 'ck', checkbox: true},
			{field: 'id', title: '编号', sortable: false, width:20},
			{field: 'description', title: '操作'},
            {field: 'username', title: '操作用户'},
			{field: 'startTime', title: '操作时间', formatter: 'dateFormatter',width:150},
			{field: 'spendTime', title: '耗时'},
			{field: 'url', title: '请求路径'},
			{field: 'method', title: '请求类型'},
			{field: 'parameter', title: '请求参数'},
			{field: 'userAgent', title: '用户标识'},
			{field: 'ip', title: 'IP地址'},
			{field: 'permissions', title: '权限值'}
		],
		onLoadSuccess: function(){  //加载成功时执行  
            // layer.msg("加载成功");  
        },  
        onLoadError: function(){  //加载失败时执行  
             //layer.msg("加载数据失败", {time : 1500, icon : 2});  
        } 
	});
});

 
function dateFormatter(value, row, index){
	if(value==null||value==""){ 
		return "";
	}
	return getSmpFormatDateByLong(parseInt(value) ,true);
}
// 删除
var deleteDialog;
function deleteAction() {
	var rows = $table.bootstrapTable('getSelections');
	if (rows.length == 0) {
		$.confirm({
			title: false,
			content: '请至少选择一条记录！',
			autoClose: 'cancel|3000',
			backgroundDismiss: true,
			buttons: {
				cancel: {
					text: '取消',
					btnClass: 'waves-effect waves-button'
				}
			}
		});
	} else {
		deleteDialog = $.confirm({
			type: 'red',
			animationSpeed: 300,
			title: false,
			content: '确认删除该日志吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].id);
						}
						$.ajax({
							type: 'get',
							url: '${basePath}/manage/log/delete/' + ids.join("-"),
							success: function(result) {
								if (result.httpCode != 200) {
									$.confirm({
										theme: 'dark',
										animation: 'rotateX',
										closeAnimation: 'rotateX',
										title: false,
										content: result.msg,
										buttons: {
											confirm: {
												text: '确认',
												btnClass: 'waves-effect waves-button waves-light'
											}
										}
									});
								} else {
									deleteDialog.close();
									$table.bootstrapTable('refresh');
								}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								$.confirm({
									theme: 'dark',
									animation: 'rotateX',
									closeAnimation: 'rotateX',
									title: false,
									content: textStatus,
									buttons: {
										confirm: {
											text: '确认',
											btnClass: 'waves-effect waves-button waves-light'
										}
									}
								});
							}
						});
					}
				},
				cancel: {
					text: '取消',
					btnClass: 'waves-effect waves-button'
				}
			}
		});
	}
}
</script>
</body>
</html>