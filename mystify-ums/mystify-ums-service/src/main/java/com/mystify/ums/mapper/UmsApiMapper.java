package com.mystify.ums.mapper;



import java.util.List;

import com.mystify.ums.entity.UmsPermission;
import com.mystify.ums.entity.UmsRole;

/**
 * 用户VOMapper
 * Created by shuzheng on 2017/01/07.
 */
public interface UmsApiMapper {

	// 根据用户id获取所拥有的权限
	List<UmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

	// 根据用户id获取所属的角色
	List<UmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
	
}