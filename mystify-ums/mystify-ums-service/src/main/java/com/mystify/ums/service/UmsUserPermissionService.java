package com.mystify.ums.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsUserPermission;
import com.mystify.ums.mapper.UmsUserPermissionMapper;
import com.sun.javafx.collections.MappingChange.Map;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户权限关联表 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsUserPermissionService")
@CacheConfig(cacheNames = "umsUserPermission")
public class UmsUserPermissionService extends BaseService<UmsUserPermission> {
	 private static Logger log = LoggerFactory.getLogger(UmsUserPermissionService.class);
	 
	 @Autowired
	 private UmsUserPermissionMapper umsUserPermissionMapper;
	 
	 public int permission(JSONArray datas, int id) {
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (json.getBoolean("checked")) {
                // 新增权限
                UmsUserPermission umsUserPermission = new UmsUserPermission();
                umsUserPermission.setUserId(id);
                umsUserPermission.setPermissionId(json.getIntValue("id"));
                umsUserPermission.setType(json.getIntValue("type"));
                umsUserPermissionMapper.insert(umsUserPermission);
            } else {
                // 删除权限
            	HashMap<String, Object> columnMap = new HashMap<String, Object>();
            	columnMap.put("permissionId", json.getIntValue("id"));
            	columnMap.put("type", json.getIntValue("type"));
                umsUserPermissionMapper.deleteByMap(columnMap);
            }
        }
        return datas.size();
    }
}
