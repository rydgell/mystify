package com.mystify.ums.service;

import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsSystem;
import com.mystify.ums.mapper.UmsSystemMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 系统 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsSystemService")
@CacheConfig(cacheNames = "umsSystem")
public class UmsSystemService extends BaseService<UmsSystem> {
	 private static Logger log = LoggerFactory.getLogger(UmsSystemService.class);
	 
	 @Autowired
	 private UmsSystemMapper umsSystemMapper;
	 
	 
}
