package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.mapper.UmsOrganizationMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 组织 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsOrganizationService")
@CacheConfig(cacheNames = "umsOrganization")
public class UmsOrganizationService extends BaseService<UmsOrganization> {
	 private static Logger log = LoggerFactory.getLogger(UmsOrganizationService.class);
	 
	 @Autowired
	 private UmsOrganizationMapper umsOrganizationMapper;
	 
	 
	 public Page<UmsOrganization> selectPage(Page<UmsOrganization> page, @Param("ew")Wrapper<UmsOrganization> ew) {
		    page.setRecords(umsOrganizationMapper.selectPage(page, ew));
		    return page;
	 }
	 
	 
}
