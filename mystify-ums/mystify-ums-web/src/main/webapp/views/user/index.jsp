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
	<title>用户管理</title>
	<jsp:include page="/views/common/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="upms:user:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增用户</a></shiro:hasPermission>
		<a class="waves-effect waves-button" href="javascript:;" data-toggle="modal" data-target="#searchModal"><i class="zmdi zmdi-search"></i> 查询</a>
		<%-- <shiro:hasPermission name="upms:user:organization"><a class="waves-effect waves-button" href="javascript:;" onclick="organizationAction()"><i class="zmdi zmdi-accounts-list"></i> 用户组织</a></shiro:hasPermission>
		<shiro:hasPermission name="upms:user:role"><a class="waves-effect waves-button" href="javascript:;" onclick="roleAction()"><i class="zmdi zmdi-accounts"></i> 用户角色</a></shiro:hasPermission>
		<shiro:hasPermission name="upms:user:permission"><a class="waves-effect waves-button" href="javascript:;" onclick="permissionAction()"><i class="zmdi zmdi-key"></i> 用户权限</a></shiro:hasPermission> --%>
	</div>
	<table id="table"></table>
	<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="width:360px">
        <div class="modal-content">
            <div class="modal-header">
            </div>
            <div class="modal-body">
            <form>
			<div class="form-group">
				<label for="username">帐号</label>
				<input id="username" type="text" class="form-control" name="username" maxlength="20">
			</div>
			<div class="form-group">
				<label for="realname">姓名</label>
				<input id="realname" type="text" class="form-control" name="realname" maxlength="20">
			</div>
			<div class="form-group">
				<label for="sex">性别</label>
				<select class="form-control" id="sex" name="sex" style="width: 100%" >
				  <option  value="">全部</option>
			      <option value="1">男</option>
			      <option value="0">女</option>
			    </select>
			</div>
			<div class="form-group">
				<label  >姓名</label>
				<select class="form-control" id="locked" name="locked" style="width: 100%">
				  <option  value="">全部</option>
			      <option value="0">正常</option>
			      <option value="1">锁定 </option>
			    </select>
			</div>
			</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">查询</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</div>
<jsp:include page="/views/common/footer.jsp" flush="true"/>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/user/list',
		height: getHeight(),
		striped: true,
		search: false,
		showRefresh: false,
		showColumns: false,
		minimumCountColumns: 2,
		clickToSelect: true,
		pagination: true,
		paginationLoop: false,
		sidePagination: 'server',
		silentSort: false,
		smartDisplay: false,
		escape: true,
		searchOnEnterKey: true,
		idField: 'id',
		maintainSelected: true,
		toolbar: '#toolbar',
		mobileResponsive:true,
	    checkOnInit:true,
		columns: [
			{field: 'id', title: '编号', sortable: false, align: 'center',width:20},
            {field: 'username', title: '帐号'},
			{field: 'realname', title: '姓名'},
			{field: 'avatar', title: '头像', align: 'center', formatter: 'avatarFormatter'},
			{field: 'phone', title: '电话'},
			{field: 'email', title: '邮箱'},
			{field: 'sex', title: '性别', formatter: 'sexFormatter'},
			{field: 'locked', title: '状态', sortable: true, align: 'center', formatter: 'lockedFormatter'},
			{field: 'ctime', title: '创建时间', formatter: 'dateFormatter'},
			{field: 'action', title: '操作', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
		]
	});
});
// 格式化操作按钮
function actionFormatter(value, row, index) {
    return [
		'<shiro:hasPermission name="upms:user:update"><a class="update" href="javascript:;" onclick="updateAction('+row.id+')" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　</shiro:hasPermission>',
		'<shiro:hasPermission name="upms:user:delete"><a class="delete" href="javascript:;" onclick="deleteAction('+row.id+')" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i>&nbsp &nbsp</a></shiro:hasPermission>',
		'<shiro:hasPermission name="upms:user:organization"><a class="delete" href="javascript:;" onclick="organizationAction('+row.id+')" data-toggle="tooltip" title="用户组织"><i class="glyphicon glyphicon-th-list"></i>&nbsp &nbsp </a></shiro:hasPermission>',
		'<shiro:hasPermission name="upms:user:role"><a class="delete" href="javascript:;" onclick="roleAction('+row.id+')" data-toggle="tooltip" title="用户角色"><i class="glyphicon glyphicon-user"></i> &nbsp &nbsp</a></shiro:hasPermission>',
		'<shiro:hasPermission name="upms:user:permission"><a class="delete" href="javascript:;" onclick="permissionAction('+row.id+')" data-toggle="tooltip" title="用户权限"><i class="glyphicon glyphicon-lock"></i> &nbsp &nbsp</a></shiro:hasPermission>'
    ].join('');
}
// 格式化图标
function avatarFormatter(value, row, index) {
    return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
}
// 格式化性别
function sexFormatter(value, row, index) {
	if (value == 1) {
		return '男';
	}
	if (value == 0) {
		return '女';
	}
	return '-';
}
// 格式化状态
function lockedFormatter(value, row, index) {
	if (value == 1) {
		return '<span class="label label-default">锁定</span>';
	} else {
		return '<span class="label label-success">正常</span>';
	}
}
// 新增
var createDialog;
function createAction() {
	createDialog = $.dialog({
		animationSpeed: 300,
		title: '新增用户',
		content: 'url:${basePath}/manage/user/create',
		onContentReady: function () {
			initMaterialInput();
		}
	});
}
// 编辑
var updateDialog;
function updateAction(userId) {
	updateDialog = $.dialog({
		animationSpeed: 300,
		title: '编辑用户',
		content: 'url:${basePath}/manage/user/update/' + userId,
		onContentReady: function () {
			initMaterialInput();
		}
	});
}
// 删除
var deleteDialog;
function deleteAction(userId) {
	deleteDialog = $.confirm({
		type: 'red',
		animationSpeed: 300,
		title: false,
		content: '确认删除该用户吗？',
		buttons: {
			confirm: {
				text: '确认',
				btnClass: 'waves-effect waves-button',
				action: function () {
					$.ajax({
						type: 'get',
						url: '${basePath}/manage/user/delete/' + userId,
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
// 用户组织
var organizationDialog;
var organizationUserId;
function organizationAction(userId) {
	organizationUserId = userId;
	organizationDialog = $.dialog({
		animationSpeed: 300,
		title: '所属部门',
		content: 'url:${basePath}/manage/user/organization/' + organizationUserId,
		onContentReady: function () {
			initMaterialInput();
			$('select').select2({
				placeholder: '请选择用户组织',
				allowClear: true
			});
		}
	});
}
// 用户角色
var roleDialog;
var roleUserId;
function roleAction(userId) {
	roleUserId = userId;
	roleDialog = $.dialog({
		animationSpeed: 300,
		title: '用户角色',
		content: 'url:${basePath}/manage/user/role/' + roleUserId,
		onContentReady: function () {
			initMaterialInput();
			$('select').select2({
				placeholder: '请选择用户角色',
				allowClear: true
			});
		}
	});
}
// 用户权限
var permissionDialog;
var permissionUserId;
function permissionAction(userId) {
	permissionUserId = userId;
	permissionDialog = $.dialog({
		animationSpeed: 300,
		title: '用户授权',
		columnClass: 'large',
		content: 'url:${basePath}/manage/user/permission/' + permissionUserId,
		onContentReady: function () {
			initMaterialInput();
			initTree();
		}
	});
}
</script>
</body>
</html>