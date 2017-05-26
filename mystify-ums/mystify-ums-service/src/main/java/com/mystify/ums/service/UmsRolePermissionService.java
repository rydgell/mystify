package com.mystify.ums.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsRolePermission;
import com.mystify.ums.mapper.UmsRolePermissionMapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsRolePermissionService")
@CacheConfig(cacheNames = "umsRolePermission")
public class UmsRolePermissionService extends BaseService<UmsRolePermission> {
	 private static Logger log = LoggerFactory.getLogger(UmsRolePermissionService.class);
	 
	 @Autowired
	 private UmsRolePermissionMapper umsRolePermissionMapper;
	 
	 public int rolePermission(JSONArray datas, int id) {
        List<Integer> deleteIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (!json.getBoolean("checked")) {
                deleteIds.add(json.getIntValue("id"));
            } else {
                // 新增权限
                UmsRolePermission umsRolePermission = new UmsRolePermission();
                umsRolePermission.setRoleId(id);
                umsRolePermission.setPermissionId(json.getIntValue("id"));
                umsRolePermissionMapper.insert(umsRolePermission);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
        	EntityWrapper<UmsRolePermission> ew = new EntityWrapper<UmsRolePermission>();
        	ew.where("roleId={0}", id).in("status", deleteIds.toArray());
        	umsRolePermissionMapper.delete(ew);
        }
        return datas.size();
    }
}
