package com.mystify.ums.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.mystify.common.base.BaseController;
import com.mystify.common.base.BasePage;
import com.mystify.common.exception.IllegalParameterException;
import com.mystify.common.validator.LengthValidator;
import com.mystify.ums.entity.UmsLog;
import com.mystify.ums.entity.UmsRole;
import com.mystify.ums.service.UmsRolePermissionService;
import com.mystify.ums.service.UmsRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色controller
 * Created by rydge on 2017/2/6.
 */
@Controller
@Api(value = "角色管理", description = "角色管理")
@RequestMapping("/manage/role")
public class UpmsRoleController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsRoleController.class);

    @Autowired
    private UmsRoleService umsRoleService;

    @Autowired
    private UmsRolePermissionService umsRolePermissionService;

    @ApiOperation(value = "角色首页")
    @RequiresPermissions("upms:role:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/role/index";
    }

    @ApiOperation(value = "角色权限")
    @RequiresPermissions("upms:role:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public String permission(@PathVariable("id") int id, ModelMap modelMap) {
    	UmsRole role = umsRoleService.queryById(id);
        modelMap.put("role", role);
        return "/role/permission";
    }

    @ApiOperation(value = "角色权限")
    @RequiresPermissions("upms:role:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object permission(@PathVariable("id") int id, String datas,ModelMap modelMap) {
        JSONArray permissions = JSONArray.parseArray(datas);
        umsRolePermissionService.rolePermission(permissions, id);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "角色列表")
    @RequiresPermissions("upms:role:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order,
            UmsRole role) {
        BasePage<UmsRole> page = new BasePage<UmsRole>(offset,limit,sort);
    	page = umsRoleService.selectPage(page, role);
    	Map<String, Object> result = new HashMap<>();
    	result.put("rows", page.getRecords());
        result.put("total", page.getTotal());
        return result;
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("upms:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/role/create";
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("upms:role:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UmsRole umsRole,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsRole.getName(), new LengthValidator(1, 20, "名称"))
                .on(umsRole.getTitle(), new LengthValidator(1, 20, "标题"))
                .doValidate()
                .result(ResultCollectors.toComplex());
       if (!result.isSuccess()) {
    	   throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        long time = System.currentTimeMillis();
        umsRole.setCtime(time);
        umsRole.setOrders(time);
        umsRoleService.update(umsRole);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除角色")
    @RequiresPermissions("upms:role:delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") String id,ModelMap modelMap) {
        umsRoleService.delete(Integer.valueOf(id));
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("upms:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UmsRole role = umsRoleService.queryById(id);
        modelMap.put("role", role);
        return "/role/update";
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("upms:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UmsRole umsRole,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsRole.getName(), new LengthValidator(1, 20, "名称"))
                .on(umsRole.getTitle(), new LengthValidator(1, 20, "标题"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        umsRoleService.update(umsRole);
        return setSuccessModelMap(modelMap);
    }

}
