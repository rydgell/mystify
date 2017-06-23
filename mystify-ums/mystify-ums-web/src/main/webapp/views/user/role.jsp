<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="roleDialog" class="crudDialog">
	<form id="roleForm" method="post">
		<div class="form-group">
			<select id="roleIds" name="roleIds" multiple="multiple" style="width: 100%">
				<c:forEach var="umsRole" items="${umsRoles}">
					<option value="${umsRole.id}" <c:forEach var="umsUserRole" items="${umsUserRoles}"><c:if test="${umsRole.id==umsUserRole.roleId}">selected="selected"</c:if></c:forEach>>${umsRole.title}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="roleSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="roleDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
function roleSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/user/role/' + roleUserId,
        data: $('#roleForm').serialize(),
		beforeSend: function() {

		},
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
				roleDialog.close();
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
</script>