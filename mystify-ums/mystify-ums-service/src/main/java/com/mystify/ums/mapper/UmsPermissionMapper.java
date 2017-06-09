package com.mystify.ums.mapper;

import com.mystify.ums.entity.UmsPermission;

import java.util.List;
import java.util.Map;

import com.mystify.common.base.BaseMapper;

/**
 * <p>
  * 权限 Mapper 接口
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
public interface UmsPermissionMapper extends BaseMapper<UmsPermission> {

	/**
	 * 根据用户id获取 相关类型的权限
	 * @param param
	 * @return
	 */
	List<UmsPermission> selectPermissionByUpmsUserId(Map<String, Object> param);
}