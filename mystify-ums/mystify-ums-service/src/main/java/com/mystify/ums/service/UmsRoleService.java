package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BasePage;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsLog;
import com.mystify.ums.entity.UmsRole;
import com.mystify.ums.mapper.UmsRoleMapper;

import org.apache.commons.lang.StringUtils;
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
	 
	 public BasePage<UmsRole> selectPage(BasePage<UmsRole> page, UmsRole entity) {
		 	EntityWrapper<UmsRole> ew = new EntityWrapper<UmsRole>();
		 	if(entity!=null){
		 		ew.where("1=1", "");
		 		if(StringUtils.isNotBlank(entity.getName())){
			 		ew.and().like("name", entity.getName());
			 	}
		 		/*if(StringUtils.isNotBlank(entity.getIp())){
		 			ew.andNew("ip={0}", entity.getIp());
			 	}*/
		 	}
		    page.setRecords(umsRoleMapper.selectPage(page, ew));
		    return page;
	 }
}
