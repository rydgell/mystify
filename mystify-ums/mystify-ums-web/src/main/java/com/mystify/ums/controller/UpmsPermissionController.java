package com.mystify.ums.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mystify.common.base.BaseController;
import com.mystify.common.exception.DataParseException;
import com.mystify.common.exception.IllegalParameterException;
import com.mystify.common.validator.LengthValidator;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.entity.UmsPermission;
import com.mystify.ums.entity.UmsUser;
import com.mystify.ums.model.ParentMenu;
import com.mystify.ums.model.SubMenu;
import com.mystify.ums.service.UmsApiService;
import com.mystify.ums.service.UmsPermissionService;
import com.mystify.ums.service.UmsSystemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限controller
 * Created by rydge on 2017/2/6.
 */
@Controller
@Api(value = "权限管理", description = "权限管理")
@RequestMapping("/manage/permission")
public class UpmsPermissionController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsPermissionController.class);

    @Autowired
    private UmsPermissionService umsPermissionService;

    @Autowired
    private UmsSystemService umsSystemService;

    @Autowired
    private UmsApiService umsApiService;

    @ApiOperation(value = "权限首页")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index( ModelMap modelMap) {
    	EntityWrapper<UmsPermission> ew = new EntityWrapper<UmsPermission>();
    	List<UmsPermission> rows =UmsPermissionService.formatUmsPermissions(umsPermissionService.selectList(ew)); 
    	modelMap.put("umsPermissions", rows);
        return "/permission/index";
    }

    @ApiOperation(value = "权限列表")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "0", value = "type") int type,
            @RequestParam(required = false, defaultValue = "0", value = "systemId") int systemId,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
    	EntityWrapper<UmsPermission> ew = new EntityWrapper<UmsPermission>();
        if (0 != type) {
            ew.where("type={0}", type);
        }
    	System.out.println("type="+type);
    	List<UmsPermission> rows = umsPermissionService.selectList(ew);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", rows.size());
        return result;
    }

    @ApiOperation(value = "角色权限列表")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id) {
        return umsPermissionService.getTreeByRoleId(id);
    }

    @ApiOperation(value = "用户权限列表")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object user(@PathVariable("id") int id, HttpServletRequest request) {
        return umsPermissionService.getTreeByUserId(id, NumberUtils.toInt(request.getParameter("type")));
    }

    @ApiOperation(value = "新增权限")
    @RequiresPermissions("upms:permission:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        return "/permission/create";
    }

    @ApiOperation(value = "新增权限")
    @RequiresPermissions("upms:permission:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UmsPermission umsPermission,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsPermission.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        long time = System.currentTimeMillis();
        umsPermission.setCtime(time);
        umsPermissionService.update(umsPermission);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除权限")
    @RequiresPermissions("upms:permission:delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id,ModelMap modelMap) {
        umsPermissionService.delete(id);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("upms:permission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UmsPermission permission = umsPermissionService.queryById(id);
        modelMap.put("permission", permission);
        return "/permission/update";
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("upms:permission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UmsPermission umsPermission,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsPermission.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        umsPermissionService.update(umsPermission);
        return setSuccessModelMap(modelMap);
    }
    
    @ApiOperation(value = "获取用户权限列表")
    @RequiresAuthentication
    @RequestMapping(value = "/userPermissions")
	@ResponseBody
	public String getUserPermissions() {
    	Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UmsUser upmsUser = umsApiService.selectUpmsUserByUsername(username);
    	String json =null;
    	List<UmsPermission> parentPermissions = umsPermissionService.getPermissionByUser(upmsUser.getId(), 1,null);
    	List<ParentMenu> parentMenus = new ArrayList<ParentMenu>();
    	for(UmsPermission p:parentPermissions){
    		ParentMenu pm = new ParentMenu();
    		pm.setMenuid(p.getId().toString());
    		pm.setMenuname(p.getName());
    		List<UmsPermission> subPermissions = umsPermissionService.getPermissionByUser(upmsUser.getId(), 2,p.getId());
    		List<SubMenu> subMenus = new ArrayList<SubMenu>();
    		for(UmsPermission sp:subPermissions){
    			SubMenu sm = new SubMenu();
    			sm.setMenuid(sp.getId().toString());
    			sm.setMenuname(sp.getName());
    			sm.setUrl(sp.getUri());
    			sm.setPid(sp.getPid().toString());
    			subMenus.add(sm);
    		}
    		pm.setMenus(subMenus);
    		parentMenus.add(pm);
    	}
    	
    	if(parentPermissions!=null&&parentPermissions.size()>0){
    		Map<String, Object> result = new HashMap<String, Object>();
    		result.put("menus", parentMenus);
    		json = JSONObject.toJSONString(result);
    	}
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
