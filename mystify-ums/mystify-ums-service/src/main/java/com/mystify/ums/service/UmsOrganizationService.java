package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BasePage;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.mapper.UmsOrganizationMapper;


import org.apache.commons.lang.StringUtils;
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
	 
	 
	 public BasePage<UmsOrganization> selectPage(BasePage<UmsOrganization> page, UmsOrganization entity) {
		 	EntityWrapper<UmsOrganization> ew = new EntityWrapper<UmsOrganization>();
		 	if(StringUtils.isNotBlank(entity.getName())){
		 		ew.like("name", entity.getName());
		 	}
		    page.setRecords(umsOrganizationMapper.selectPage(page, ew));
		    return page;
	 }
	 
	 
}
