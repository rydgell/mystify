package com.mystify.ums.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BaseService;
import com.mystify.ums.entity.UmsPermission;
import com.mystify.ums.entity.UmsRolePermission;
import com.mystify.ums.entity.UmsSystem;
import com.mystify.ums.entity.UmsUserPermission;
import com.mystify.ums.mapper.UmsPermissionMapper;
import com.mystify.ums.mapper.UmsSystemMapper;
import com.mystify.ums.mapper.UmsUserPermissionMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author rydge
 * @since 2017-05-22
 */
@Service("umsPermissionService")
@CacheConfig(cacheNames = "umsPermission")
public class UmsPermissionService extends BaseService<UmsPermission> {
	 private static Logger log = LoggerFactory.getLogger(UmsPermissionService.class);
	 
	 @Autowired
	 private UmsPermissionMapper umsPermissionMapper;
	 
	 @Autowired
	 private UmsSystemMapper umsSystemMapper;


     @Autowired
     private UmsUserPermissionMapper umsUserPermissionMapper;

     public JSONArray getTreeByRoleId(Integer roleId) {
        // 角色已有权限
        List<UmsRolePermission> rolePermissions =  null;

        JSONArray systems = new JSONArray();
        // 系统
        EntityWrapper<UmsSystem> ew = new EntityWrapper<UmsSystem>();
        ew.orderBy("orders", true);
        ew.where("status={0}", 1);
        List<UmsSystem> umsSystems =  umsSystemMapper.selectList(ew);
        for (UmsSystem umsSystem : umsSystems) {
            JSONObject node = new JSONObject();
            node.put("id", umsSystem.getId());
            node.put("name", umsSystem.getTitle());
            node.put("nocheck", true);
            node.put("open", true);
            systems.add(node);
        }

        if (systems.size() > 0) {
            for (Object system: systems) {
                EntityWrapper<UmsPermission> ewp = new EntityWrapper<UmsPermission>();
                ewp.orderBy("orders", true);
                ewp.where("status={0}", 1).and("systemId={0}", ((JSONObject) system).getIntValue("id"));
               
                List<UmsPermission> umsPermissions =  umsPermissionMapper.selectList(ewp);
                if (umsPermissions.size() == 0) continue;
                // 目录
                JSONArray folders = new JSONArray();
                for (UmsPermission umsPermission: umsPermissions) {
                    if (umsPermission.getPid().intValue() != 0 || umsPermission.getType() != 1) continue;
                    JSONObject node = new JSONObject();
                    node.put("id", umsPermission.getId());
                    node.put("name", umsPermission.getName());
                    node.put("open", true);
                    for (UmsRolePermission rolePermission : rolePermissions) {
                        if (rolePermission.getPermissionId().intValue() == umsPermission.getId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    folders.add(node);
                    // 菜单
                    JSONArray menus = new JSONArray();
                    for (Object folder : folders) {
                        for (UmsPermission umsPermission2: umsPermissions) {
                            if (umsPermission2.getPid().intValue() != ((JSONObject) folder).getIntValue("id") || umsPermission2.getType() != 2) continue;
                            JSONObject node2 = new JSONObject();
                            node2.put("id", umsPermission2.getId());
                            node2.put("name", umsPermission2.getName());
                            node2.put("open", true);
                            for (UmsRolePermission rolePermission : rolePermissions) {
                                if (rolePermission.getPermissionId().intValue() == umsPermission2.getId().intValue()) {
                                    node2.put("checked", true);
                                }
                            }
                            menus.add(node2);
                            // 按钮
                            JSONArray buttons = new JSONArray();
                            for (Object menu : menus) {
                                for (UmsPermission upmsPermission3: umsPermissions) {
                                    if (upmsPermission3.getPid().intValue() != ((JSONObject) menu).getIntValue("id") || upmsPermission3.getType() != 3) continue;
                                    JSONObject node3 = new JSONObject();
                                    node3.put("id", upmsPermission3.getId());
                                    node3.put("name", upmsPermission3.getName());
                                    node3.put("open", true);
                                    for (UmsRolePermission rolePermission : rolePermissions) {
                                        if (rolePermission.getPermissionId().intValue() == upmsPermission3.getId().intValue()) {
                                            node3.put("checked", true);
                                        }
                                    }
                                    buttons.add(node3);
                                }
                                if (buttons.size() > 0) {
                                    ((JSONObject) menu).put("children", buttons);
                                    buttons = new JSONArray();
                                }
                            }
                        }
                        if (menus.size() > 0) {
                            ((JSONObject) folder).put("children", menus);
                            menus = new JSONArray();
                        }
                    }
                }
                if (folders.size() > 0) {
                    ((JSONObject) system).put("children", folders);
                }
            }
        }
        return systems;
    }

 
    public JSONArray getTreeByUserId(Integer usereId, Byte type) {
        // 角色权限
        EntityWrapper<UmsUserPermission> ew = new EntityWrapper<UmsUserPermission>();
        ew.orderBy("orders", true);
        ew.where("userId={0}", usereId).and("type={0}", type);
        List<UmsUserPermission> umsUserPermissions = umsUserPermissionMapper.selectList(ew);

        JSONArray systems = new JSONArray();
        // 系统
        EntityWrapper<UmsSystem> ewsys = new EntityWrapper<UmsSystem>();
        ewsys.orderBy("orders", true);
        ewsys.where("status={0}", 1);
      
        List<UmsSystem> umsSystems = umsSystemMapper.selectList(ewsys);
        for (UmsSystem umsSystem : umsSystems) {
            JSONObject node = new JSONObject();
            node.put("id", umsSystem.getId());
            node.put("name", umsSystem.getTitle());
            node.put("nocheck", true);
            node.put("open", true);
            systems.add(node);
        }

        if (systems.size() > 0) {
            for (Object system: systems) {
                EntityWrapper<UmsPermission> ewp = new EntityWrapper<UmsPermission>();
                ewp.orderBy("orders", true);
                ewp.where("status={0}", 1).and("systemId={0}", ((JSONObject) system).getIntValue("id"));
                
                List<UmsPermission> umsPermissions = umsPermissionMapper.selectList(ewp);
                if (umsPermissions.size() == 0) continue;
                // 目录
                JSONArray folders = new JSONArray();
                for (UmsPermission umsPermission: umsPermissions) {
                    if (umsPermission.getPid().intValue() != 0 || umsPermission.getType() != 1) continue;
                    JSONObject node = new JSONObject();
                    node.put("id", umsPermission.getId());
                    node.put("name", umsPermission.getName());
                    node.put("open", true);
                    for (UmsUserPermission umsUserPermission : umsUserPermissions) {
                        if (umsUserPermission.getPermissionId().intValue() == umsPermission.getId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    folders.add(node);
                    // 菜单
                    JSONArray menus = new JSONArray();
                    for (Object folder : folders) {
                        for (UmsPermission upmsPermission2: umsPermissions) {
                            if (upmsPermission2.getPid().intValue() != ((JSONObject) folder).getIntValue("id") || upmsPermission2.getType() != 2) continue;
                            JSONObject node2 = new JSONObject();
                            node2.put("id", upmsPermission2.getId());
                            node2.put("name", upmsPermission2.getName());
                            node2.put("open", true);
                            for (UmsUserPermission umsUserPermission : umsUserPermissions) {
                                if (umsUserPermission.getPermissionId().intValue() == upmsPermission2.getId().intValue()) {
                                    node2.put("checked", true);
                                }
                            }
                            menus.add(node2);
                            // 按钮
                            JSONArray buttons = new JSONArray();
                            for (Object menu : menus) {
                                for (UmsPermission umsPermission3: umsPermissions) {
                                    if (umsPermission3.getPid().intValue() != ((JSONObject) menu).getIntValue("id") || umsPermission3.getType() != 3) continue;
                                    JSONObject node3 = new JSONObject();
                                    node3.put("id", umsPermission3.getId());
                                    node3.put("name", umsPermission3.getName());
                                    node3.put("open", true);
                                    for (UmsUserPermission umsUserPermission : umsUserPermissions) {
                                        if (umsUserPermission.getPermissionId().intValue() == umsPermission3.getId().intValue()) {
                                            node3.put("checked", true);
                                        }
                                    }
                                    buttons.add(node3);
                                }
                                if (buttons.size() > 0) {
                                    ((JSONObject) menu).put("children", buttons);
                                    buttons = new JSONArray();
                                }
                            }
                        }
                        if (menus.size() > 0) {
                            ((JSONObject) folder).put("children", menus);
                            menus = new JSONArray();
                        }
                    }
                }
                if (folders.size() > 0) {
                    ((JSONObject) system).put("children", folders);
                }
            }
        }
        return systems;
    }
}
