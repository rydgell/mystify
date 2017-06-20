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
		 	
		    page.setRecords(umsLogMapper.selectPage(page, null));
		    return page;
	 }
}
