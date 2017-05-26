package com.mystify.ums.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.mystify.common.base.BaseModel;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@TableName("ums_user_role")
public class UmsUserRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="user_role_id", type= IdType.AUTO)
	private Integer userRoleId;
    /**
     * 用户编号
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 角色编号
     */
	@TableField("role_id")
	private Integer roleId;


	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
