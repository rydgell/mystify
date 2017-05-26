package com.mystify.ums.service;

import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsRole;
import com.mystify.ums.mapper.UmsRoleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsRoleService")
@CacheConfig(cacheNames = "umsRole")
public class UmsRoleService extends BaseService<UmsRole> {
	 private static Logger log = LoggerFactory.getLogger(UmsRoleService.class);
	 
	 @Autowired
	 private UmsRoleMapper umsRoleMapper;
}
