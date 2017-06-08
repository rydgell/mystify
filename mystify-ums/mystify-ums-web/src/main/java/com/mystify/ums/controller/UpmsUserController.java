package com.mystify.ums.controller;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mystify.common.base.BaseController;
import com.mystify.common.exception.DataParseException;
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

/**
 * 用户controller
 * Created by shuzheng on 2017/2/6.
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
        return "/manage/user/index.jsp";
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
        return "/manage/user/organization.jsp";
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
        return "/manage/user/role.jsp";
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
        modelMap.put("user", user);
        return "/manage/user/permission.jsp";*/
    	return "";
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
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
    	/* UpmsUserExample upmsUserExample = new UpmsUserExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsUserExample.or()
                    .andRealnameLike("%" + search + "%");
            upmsUserExample.or()
                    .andUsernameLike("%" + search + "%");
        }
        List<UpmsUser> rows = upmsUserService.selectByExampleForOffsetPage(upmsUserExample, offset, limit);
        long total = upmsUserService.countByExample(upmsUserExample);*/
        Map<String, Object> result = new HashMap<>();
        //result.put("rows", rows);
        //result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/user/create.jsp";
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UmsUser umsUser) {
        /*ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
                .on(upmsUser.getPassword(), new LengthValidator(5, 32, "密码"))
                .on(upmsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.MD5(upmsUser.getPassword() + upmsUser.getSalt()));
        upmsUser.setCtime(time);
        upmsUser = upmsUserService.createUser(upmsUser);
        if (null == upmsUser) {
            return new UpmsResult(UpmsResultConstant.FAILED, "帐号名已存在！");
        }
        _log.info("新增用户，主键：userId={}", upmsUser.getUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);*/
    	return "";
    }

    @ApiOperation(value = "删除用户")
    @RequiresPermissions("upms:user:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        //int count = upmsUserService.deleteByPrimaryKeys(ids);
        //return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    	return "";
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UmsUser user = umsUserService.queryById(id);
        modelMap.put("user", user);
        return "/manage/user/update.jsp";
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UmsUser umsUser) {
        /*ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getUsername(), new LengthValidator(1, 20, "帐号"))
                .on(upmsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        // 不允许直接改密码
        upmsUser.setPassword(null);
        upmsUser.setUserId(id);
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);*/
    	return "";
    }
    
    @ApiOperation(value = "获取用户权限列表")
    @RequiresAuthentication
    @RequestMapping(value = "/userPermissions")
	@ResponseBody
	public String getUserPermissions() {
    	System.out.println("getUserPermissions");
    	Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
    	String json =null;
    	
		try {
			
			if (null == json) {
				// 没有任何权限时
				json = "{'menus':[{'menuid':'','icon':'icon-sys','menuname':'','menus':[{'menuid':'','menuname':'没有任何权限','icon':'icon-page','url':''}]}]}";
			}
		 
		} catch (Exception e) {
			throw new DataParseException("获取用户权限错误!,错误原因:  " + e.getMessage());
		}
		return json;
	}

}
