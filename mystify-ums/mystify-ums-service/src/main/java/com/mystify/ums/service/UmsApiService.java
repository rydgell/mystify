package com.mystify.ums.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BaseService;
import com.mystify.ums.mapper.*;
import com.mystify.ums.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户service实现
 */
@Service("umsApiService")  
@Transactional
public class UmsApiService  {

    private static Logger _log = LoggerFactory.getLogger(UmsApiService.class);

    @Autowired
    private UmsUserMapper umsUserMapper;

    @Autowired
    private UmsApiMapper umsApiMapper;

    @Autowired
    private UmsRolePermissionMapper umsRolePermissionMapper;

    @Autowired
    private UmsUserPermissionMapper umsUserPermissionMapper;

    @Autowired
    private UmsSystemMapper umsSystemMapper;

    @Autowired
    private UmsOrganizationMapper umsOrganizationMapper;

    @Autowired
    private UmsLogMapper umsLogMapper;

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    public List<UmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        // 用户不存在或锁定状态
        UmsUser upmsUser = umsUserMapper.selectById(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UmsPermission> upmsPermissions = umsApiMapper.selectUmsPermissionByUmsUserId(upmsUserId);
        return upmsPermissions;
    }

    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    public List<UmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        // 用户不存在或锁定状态
        UmsUser upmsUser = umsUserMapper.selectById(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsRoleByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UmsRole> upmsRoles = umsApiMapper.selectUmsRoleByUmsUserId(upmsUserId);
        return upmsRoles;
    }

    /**
     * 根据角色id获取所拥有的权限
     * @param upmsRoleId
     * @return
     */
    public List<UmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        EntityWrapper<UmsRolePermission> ew = new EntityWrapper<UmsRolePermission>();
        ew.where("roleId={0}", upmsRoleId);
        List<UmsRolePermission> upmsRolePermissions = umsRolePermissionMapper.selectList(ew);
        return upmsRolePermissions;
    }

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    public List<UmsUserPermission> selectUpmsUserPermissionByUpmsUserId(Integer upmsUserId) {
        EntityWrapper<UmsUserPermission> ew = new EntityWrapper<UmsUserPermission>();
        ew.where("userId={0}", upmsUserId);
        List<UmsUserPermission> upmsUserPermissions = umsUserPermissionMapper.selectList(ew);
        return upmsUserPermissions;
    }

  /*  *//**
     * 根据条件获取系统数据
     * @param upmsSystemExample
     * @return
     *//*
    public List<UmsSystem> selectUpmsSystemByExample1(UmsSystem upmsSystemExample) {
        return upmsSystemMapper.selectByExample(upmsSystemExample);
    }

    *//**
     * 根据条件获取组织数据
     * @param upmsOrganizationExample
     * @return
     *//*
    public List<UmsOrganization> selectUpmsOrganizationByExampl1e(UpmsOrganizationExample upmsOrganizationExample) {
        return upmsOrganizationMapper.selectByExample(upmsOrganizationExample);
    }*/

    /**
     * 根据username获取UpmsUser
     * @param username
     * @return
     */
    public UmsUser selectUpmsUserByUsername(String username) {
        EntityWrapper<UmsUser> ew = new EntityWrapper<UmsUser>();
        ew.where("username={0}", username);
        List<UmsUser> upmsUsers = umsUserMapper.selectList(ew);
        if (null != upmsUsers && upmsUsers.size() > 0) {
            return upmsUsers.get(0);
        }
        return null;
    }

    /**
     * 写入操作日志
     * @param record
     * @return
     */
    public int insertUpmsLogSelective(UmsLog record) {
        return umsLogMapper.insert(record);
    }

}