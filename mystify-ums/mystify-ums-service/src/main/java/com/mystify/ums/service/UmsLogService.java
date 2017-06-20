package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mystify.common.base.BasePage;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsLog;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.mapper.UmsLogMapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsLogService")
public class UmsLogService extends BaseService<UmsLog> {
	 private static Logger log = LoggerFactory.getLogger(UmsLogService.class);

	 @Autowired
	 UmsLogMapper umsLogMapper;
	 
	 public BasePage<UmsLog> selectPage(BasePage<UmsLog> page, UmsLog entity) {
		 	EntityWrapper<UmsLog> ew = new EntityWrapper<UmsLog>();
		 	/*if(StringUtils.isNotBlank(entity.getName())){
		 		ew.like("name", entity.getName());
		 	}*/
		 	if(entity!=null){
		 		ew.where("1=1", "");
		 		if(StringUtils.isNotBlank(entity.getUsername())){
			 		ew.andNew("username={0}", entity.getUsername());
			 	}
		 		if(StringUtils.isNotBlank(entity.getDescription())){
		 			ew.and().like("description", entity.getDescription());
			 	}
		 		if(StringUtils.isNotBlank(entity.getUrl())){
		 			ew.and().like("url", entity.getUrl());
			 	}
		 		if(StringUtils.isNotBlank(entity.getIp())){
		 			ew.andNew("ip={0}", entity.getIp());
			 	}
		 		if(StringUtils.isNotBlank(entity.getPermissions())){
		 			ew.andNew("permissions={0}", entity.getPermissions());
			 	}
		 	}
		    page.setRecords(umsLogMapper.selectPage(page, ew));
		    return page;
	 }
}
