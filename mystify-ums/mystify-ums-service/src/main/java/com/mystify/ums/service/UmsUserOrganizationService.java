package com.mystify.ums.service;

import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsUserOrganization;
import com.mystify.ums.mapper.UmsUserOrganizationMapper;

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
 * 用户组织关联表 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsUserOrganizationService")
@CacheConfig(cacheNames = "umsUserOrganization")
public class UmsUserOrganizationService extends BaseService<UmsUserOrganization> {
	 private static Logger log = LoggerFactory.getLogger(UmsUserOrganizationService.class);
	 
	 @Autowired
	 private UmsUserOrganizationMapper umsUserOrganizationMapper;
	 
	 public int organization(String[] organizationIds, int id) {
        int result = 0;
        // 删除旧记录
        HashMap<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("user_id", id);
        umsUserOrganizationMapper.deleteByMap(columnMap);
        // 增加新记录
        if (null != organizationIds) {
            for (String organizationId : organizationIds) {
                if (StringUtils.isBlank(organizationId)) {
                    continue;
                }
                UmsUserOrganization upmsUserOrganization = new UmsUserOrganization();
                upmsUserOrganization.setUserId(id);
                upmsUserOrganization.setOrganizationId(NumberUtils.toInt(organizationId));
                result = umsUserOrganizationMapper.insert(upmsUserOrganization);
            }
        }
        return result;
    }
}
