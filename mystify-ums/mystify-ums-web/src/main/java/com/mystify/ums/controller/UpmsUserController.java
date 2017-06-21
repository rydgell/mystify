package com.mystify.ums.controller;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mystify.common.base.BaseController;
import com.mystify.common.base.BasePage;
import com.mystify.common.exception.DataParseException;
import com.mystify.common.exception.IllegalParameterException;
import com.mystify.common.utils.MD5Util;
import com.mystify.common.validator.LengthValidator;
import com.mystify.common.validator.NotNullValidator;
import com.mystify.ums.entity.UmsRole;
import com.mystify.ums.entity.UmsUser;
import com.mystify.ums.service.UmsOrganizationService;
import com.mystify.ums.service.UmsRoleService;
import com.mystify.ums.service.UmsUserOrganizationService;
import com.mystify.ums.service.UmsUserPermissionService;
import com.mystify.ums.service.UmsUserRoleService;
import com.mystify.ums.service.UmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户controller
 * Created by rydge on 2017/2/6.
 */
@Controller
@Api(value = "用户管理", description = "用户管理")
@RequestMapping("/manage/user")
public class UpmsUserController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserController.class);

    @Autowired
    private UmsUserService umsUserService;

    @Autowired
    private UmsRoleService umsRoleService;

    @Autowired
    private UmsOrganizationService umsOrganizationService;

    @Autowired
    private UmsUserOrganizationService umsUserOrganizationService;

    @Autowired
    private UmsUserRoleService umsUserRoleService;

    @Autowired
    private UmsUserPermissionService umsUserPermissionService;

    @ApiOperation(value = "用户首页")
    @RequiresPermissions("upms:user:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/user/index";
    }

    @ApiOperation(value = "用户组织")
    @RequiresPermissions("upms:user:organization")
    @RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
    public String organization(@PathVariable("id") int id, ModelMap modelMap) {
        // 所有组织
       /* List<UmsOrganization> upmsOrganizations = umsOrganizationService.selectByExample(new UmsOrganizationExample());
        // 用户拥有组织
        UpmsUserOrganizationExample upmsUserOrganizationExample = new UpmsUserOrganizationExample();
        upmsUserOrganizationExample.createCriteria()
                .andUserIdEqualTo(id);
        List<UpmsUserOrganization> upmsUserOrganizations = upmsUserOrganizationService.selectByExample(upmsUserOrganizationExample);
        modelMap.put("upmsOrganizations", upmsOrganizations);
        modelMap.put("upmsUserOrganizations", upmsUserOrganizations);*/
        return "/user/organization";
    }

    @ApiOperation(value = "用户组织")
    @RequiresPermissions("upms:user:organization")
    @RequestMapping(value = "/organization/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object organization(@PathVariable("id") int id, HttpServletRequest request) {
        /*String[] organizationIds = request.getParameterValues("organizationId");
        upmsUserOrganizationService.organization(organizationIds, id);
        return new UpmsResult(UpmsResultConstant.SUCCESS, "");*/
    	return "";
    }

    @ApiOperation(value = "用户角色")
    @RequiresPermissions("upms:user:role")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public String role(@PathVariable("id") int id, ModelMap modelMap) {
        // 所有角色
        /*List<UpmsRole> upmsRoles = upmsRoleService.selectByExample(new UpmsRoleExample());
        // 用户拥有角色
        UpmsUserRoleExample upmsUserRoleExample = new UpmsUserRoleExample();
        upmsUserRoleExample.createCriteria()
                .andUserIdEqualTo(id);
        List<UpmsUserRole> upmsUserRoles = upmsUserRoleService.selectByExample(upmsUserRoleExample);
        modelMap.put("upmsRoles", upmsRoles);
        modelMap.put("upmsUserRoles", upmsUserRoles);*/
        return "/user/role";
    }

    @ApiOperation(value = "用户角色")
    @RequiresPermissions("upms:user:role")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id, HttpServletRequest request) {
       /* String[] roleIds = request.getParameterValues("roleId");
        upmsUserRoleService.role(roleIds, id);
        return new UpmsResult(UpmsResultConstant.SUCCESS, "");*/
    	return "";
    }

    @ApiOperation(value = "用户权限")
    @RequiresPermissions("upms:user:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public String permission(@PathVariable("id") int id, ModelMap modelMap) {
        /*UpmsUser user = upmsUserService.selectByPrimaryKey(id);
        modelMap.put("user", user);*/
        return "/user/permission";
    }

    @ApiOperation(value = "用户权限")
    @RequiresPermissions("upms:user:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object permission(@PathVariable("id") int id, HttpServletRequest request) {
        /*JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        umsUserPermissionService.permission(datas, id);
        return new UpmsResult(UpmsResultConstant.SUCCESS, datas.size());
    	return "";*/
    	return "";
    }

    @ApiOperation(value = "用户列表")
    @RequiresPermissions("upms:user:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order,UmsUser umsUser) {
        BasePage<UmsUser> page = new BasePage<UmsUser>(offset,limit,sort);
    	page = umsUserService.selectPage(page, umsUser);
    	Map<String, Object> result = new HashMap<>();
    	result.put("rows", page.getRecords());
        result.put("total", page.getTotal());
        return result;
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/user/create";
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UmsUser umsUser,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
                .on(umsUser.getPassword(), new LengthValidator(5, 32, "密码"))
                .on(umsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        umsUser.setSalt(salt);
        umsUser.setPassword(MD5Util.MD5(umsUser.getPassword() + umsUser.getSalt()));
        umsUser.setCtime(time);
        umsUser = umsUserService.createUser(umsUser);
        if (null == umsUser) {
        	throw new DataParseException("user already exists") ;
        }
        _log.info("新增用户，主键：userId={}", umsUser.getId());
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除用户")
    @RequiresPermissions("upms:user:delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id,ModelMap modelMap) {
        umsUserService.delete(id);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UmsUser user = umsUserService.queryById(id);
        modelMap.put("user", user);
        return "/user/update";
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UmsUser umsUser,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
                .on(umsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        // 不允许直接改密码
        umsUser.setPassword(null);
        umsUserService.update(umsUser);
        return setSuccessModelMap(modelMap);
    }
    
    

}
