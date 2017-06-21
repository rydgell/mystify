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
	<title>角色管理</title>
	<jsp:include page="/views/common/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="upms:role:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增角色</a></shiro:hasPermission>
	</div>
	<table id="table" ></table>
</div>
<jsp:include page="/views/common/footer.jsp" flush="true"/>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/role/list',
		height: getHeight(),
		striped: true,
		search: false,
		showRefresh: true,
		showColumns: true,
		minimumCountColumns: 2,
		clickToSelect: true,
		pagination: true,
		paginationLoop: false,
		sidePagination: 'server',
		silentSort: false,
		smartDisplay: false,
		escape: true,
		idField: 'id',
		maintainSelected: true,
		toolbar: '#toolbar',
		columns: [
			{field: 'id', title: 'id', sortable: false},
			{field: 'name', title: '角色名称'},
			{field: 'title', title: '角色标题'},
            {field: 'description', title: '角色描述'},
            {field: 'ctime', title: '创建时间',formatter: 'dateFormatter'},
			{field: 'action', title: '操作', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
		]
	});
});
// 格式化操作按钮
function actionFormatter(value, row, index) {
    return [
		'<shiro:hasPermission name="upms:role:update"><a class="update" href="javascript:;" onclick="updateAction('+row.id+')" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a></shiro:hasPermission>　',
		'<shiro:hasPermission name="upms:role:delete"><a class="delete" href="javascript:;" onclick="deleteAction('+row.id+')" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a></shiro:hasPermission>  ',
		'<shiro:hasPermission name="upms:role:permission"><a style="margin-left: 8px;" href="javascript:;" onclick="permissionAction('+row.id+')" data-toggle="tooltip" title="角色权限"><i class="zmdi zmdi-key"></i> </a></shiro:hasPermission>'
    ].join('');
}
// 新增
var createDialog;
function createAction() {
	createDialog = $.dialog({
		animationSpeed: 300,
		title: '新增角色',
		content: 'url:${basePath}/manage/role/create',
		onContentReady: function () {
			initMaterialInput();
		}
	});
}
// 编辑
var updateDialog;
function updateAction(id) {
	updateDialog = $.dialog({
		animationSpeed: 300,
		title: '编辑角色',
		content: 'url:${basePath}/manage/role/update/' + id,
		onContentReady: function () {
			initMaterialInput();
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
		content: '确认删除该角色吗？',
		buttons: {
			confirm: {
				text: '确认',
				btnClass: 'waves-effect waves-button',
				action: function () {
					$.ajax({
						type: 'get',
						url: '${basePath}/manage/role/delete/' + id,
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
// 角色权限
var permissionDialog;
var roleId;
function permissionAction(id) {
	roleId = id;
	permissionDialog = $.dialog({
		animationSpeed: 300,
		title: '角色权限',
		content: 'url:${basePath}/manage/role/permission/' + roleId,
		onContentReady: function () {
			initMaterialInput();
			initTree();
		}
	});
}

</script>
</body>
</html>