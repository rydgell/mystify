package com.mystify.ums.service;

import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsLog;
import com.mystify.ums.mapper.UmsLogMapper;

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
@CacheConfig(cacheNames = "umsLog")
public class UmsLogService extends BaseService<UmsLog> {
	 private static Logger log = LoggerFactory.getLogger(UmsLogService.class);

	 @Autowired
	 UmsLogMapper umsLogMapper;
	 
	 
}
