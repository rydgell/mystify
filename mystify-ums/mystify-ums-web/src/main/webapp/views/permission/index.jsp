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
	<title>权限管理</title>
	<jsp:include page="/views/common/head.jsp" flush="true"/>
</head>
<link href="${basePath}/plugins/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" />
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="upms:permission:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增权限</a></shiro:hasPermission>
	</div>
	<!--  <table id="table"></table>  -->
	<table id="table" class="table table-bordered table-striped table-responsive " >
			<thead><tr>
			<th>权限名称</th>
			<th>id</th>
			<th>父id</th>
			<th>路径</th>
			<th>排序</th>
			<th>可见</th>
			<th>类型</th>
			<th>图标</th>
			<th>权限标识</th>
			<th>操作</th>
			</thead>
			<tbody><c:forEach items="${umsPermissions}" var="menu">
				<tr id="${menu.id}" pId="${menu.pid}">
					<td nowrap><i class=""></i>${menu.name}</td>
					<td width="50px">${menu.id}</td>
					<td width="50px">${menu.pid}</td>
					<td>${menu.uri}</td>
					<td width="50px">${menu.orders}</td>
					<td width="50px">${menu.status eq '1'?'<span class="label label-success">正常</span>':'<span class="label label-default">锁定</span>'}</td>
					<td width="50px">
						<c:choose>
						    <c:when test="${menu.type == 1}">
						       		目录
						    </c:when>
						    <c:when test="${menu.type == 2}">
						        	菜单
						    </c:when>
						    <c:when test="${menu.type == 3}">
						       		按钮
						    </c:when>
						    <c:otherwise>
						       		-
						    </c:otherwise>
						</c:choose></td>
					<td width="50px"><i class="${menu.icon}"></i></td>
					<td>${menu.permissionValue}</td>
					<td width="300px">
						 <shiro:hasPermission name="upms:permission:create"><a  href="javascript:;" onclick="createAction()" data-toggle="tooltip" title="添加"><i class="glyphicon glyphicon-plus"></i> &nbsp</a>  </shiro:hasPermission>
						 <shiro:hasPermission name="upms:permission:update"><a class="update" href="javascript:;" onclick="updateAction(${menu.id})" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　</shiro:hasPermission>
						 <shiro:hasPermission name="upms:permission:delete"><a class="delete" href="javascript:;" onclick="deleteAction(${menu.id})" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a></shiro:hasPermission>
					</td>
				</tr>
			</c:forEach></tbody>
		</table> 
</div>
<jsp:include page="/views/common/footer.jsp" flush="true"/>
<script src="${basePath}/plugins/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<script>
var $table = $('#table');
$(function() {
	/* $('#table').treeTable({expandLevel : 3}).show();  */
	// bootstrap table初始化
	/* $table.bootstrapTable({
		url: '${basePath}/manage/permission/list',
		height: getHeight(),
		striped: true,
		search: false,
		showRefresh: false,
		showColumns: true,
		minimumCountColumns: 2,
		clickToSelect: true,
		pagination: false,
		paginationLoop: false,
		sidePagination: 'server',
		silentSort: false,
		smartDisplay: false,
		escape: true,
		idField: 'id',
		toolbar: '#toolbar',
		columns: [
			{field: 'id', title: '编号', sortable: true, align: 'center'},
            {field: 'systemId', title: '所属系统'},
			{field: 'pid', title: '所属上级'},
			{field: 'name', title: '权限名称'},
			{field: 'type', title: '类型', formatter: 'typeFormatter'},
			{field: 'permissionValue', title: '权限值'},
			{field: 'uri', title: '路径'},
			{field: 'icon', title: '图标', align: 'center', formatter: 'iconFormatter'},
			{field: 'status', title: '状态', sortable: true, align: 'center', formatter: 'statusFormatter'},
			{field: 'action', title: '操作', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
		]
	});  */
	
	$('#table').treeTable({expandLevel : 3}).show(); 
});

 
// 格式化操作按钮
function actionFormatter(value, row, index) {
    return [
		'<a class="update" href="javascript:;" onclick="updateAction('+row.id+')" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
		'<a class="delete" href="javascript:;" onclick="deleteAction('+row.id+')" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}
// 格式化图标
function iconFormatter(value, row, index) {
    return '<i class="' + value + '"></i>';
}
// 格式化类型
function typeFormatter(value, row, index) {
	if (value == 1) {
		return '目录';
	}
	if (value == 2) {
		return '菜单';
	}
	if (value == 3) {
		return '按钮';
	}
	return '-';
}
// 格式化状态
function statusFormatter(value, row, index) {
	if (value == 1) {
		return '<span class="label label-success">正常</span>';
	} else {
		return '<span class="label label-default">锁定</span>';
	}
}
// 新增
var createDialog;
function createAction() {
	createDialog = $.dialog({
		animationSpeed: 300,
		title: '新增权限',
		content: 'url:${basePath}/manage/permission/create',
		onContentReady: function () {
			initMaterialInput();
			$('select').select2();
		}
	});
}
// 编辑
var updateDialog;
function updateAction(id) {
	updateDialog = $.dialog({
		animationSpeed: 300,
		title: '编辑权限',
		content: 'url:${basePath}/manage/permission/update/' + id,
		onContentReady: function () {
			initMaterialInput();
			$('select').select2();
			initType();
			initSelect2();
		}
	});
}
// 删除
var deleteDialog;
function deleteAction(id) {
	deleteDialog = $.confirm({
		type: 'red',
		animationSpeed: 300,
		title: false,
		content: '确认删除该权限吗？',
		buttons: {
			confirm: {
				text: '确认',
				btnClass: 'waves-effect waves-button',
				action: function () {
					$.ajax({
						type: 'get',
						url: '${basePath}/manage/permission/delete/' + id,
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
								//$table.bootstrapTable('refresh');
								window.location.reload();
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
</script>
</body>
</html>