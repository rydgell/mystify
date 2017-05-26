package com.mystify.ums.service;

import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsUserRole;
import com.mystify.ums.mapper.UmsUserRoleMapper;

import java.util.HashMap;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsUserRoleService")
@CacheConfig(cacheNames = "umsUserRole")
public class UmsUserRoleService extends BaseService<UmsUserRole> {
	 private static Logger log = LoggerFactory.getLogger(UmsUserRoleService.class);
	 
	 @Autowired
	 private UmsUserRoleMapper umsUserRoleMapper;
	 
	 public int role(String[] roleIds, int id) {
        int result = 0;
        // 删除旧记录
        HashMap<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("userId", id);
        umsUserRoleMapper.deleteByMap(columnMap);
        // 增加新记录
        if (null != roleIds) {
            for (String roleId : roleIds) {
                if (StringUtils.isBlank(roleId)) {
                    continue;
                }
                UmsUserRole umsUserRole = new UmsUserRole();
                umsUserRole.setUserId(id);
                umsUserRole.setRoleId(NumberUtils.toInt(roleId));
                result = umsUserRoleMapper.insert(umsUserRole);
            }
        }
        return result;
    }
}
