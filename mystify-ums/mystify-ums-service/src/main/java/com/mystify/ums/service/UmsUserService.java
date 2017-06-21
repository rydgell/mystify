package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BasePage;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsRole;
import com.mystify.ums.entity.UmsUser;
import com.mystify.ums.mapper.UmsUserMapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsUserService")
@CacheConfig(cacheNames = "umsUser")
public class UmsUserService extends BaseService<UmsUser> {
	 private static Logger log = LoggerFactory.getLogger(UmsUserService.class);
	 
	 @Autowired
	 private UmsUserMapper umsUserMapper;
	 
	 public UmsUser createUser(UmsUser upmsUser) {
		 UmsUser u = new UmsUser();
		 u.setUsername(upmsUser.getUsername());
		 u = umsUserMapper.selectOne(u);
         if (u !=null) {
            return null;
         }
         umsUserMapper.insert(upmsUser);
         	return upmsUser;
	    }
	 
	 public BasePage<UmsUser> selectPage(BasePage<UmsUser> page, UmsUser entity) {
		 	EntityWrapper<UmsUser> ew = new EntityWrapper<UmsUser>();
		 	if(entity!=null){
		 		ew.where("1=1", "");
		 		if(StringUtils.isNotBlank(entity.getUsername())){
			 		ew.and().like("username", entity.getUsername());
			 	}
		 		/*if(StringUtils.isNotBlank(entity.getIp())){
		 			ew.andNew("ip={0}", entity.getIp());
			 	}*/
		 	}
		    page.setRecords(umsUserMapper.selectPage(page, ew));
		    return page;
	 }
}
