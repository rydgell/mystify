package com.mystify.ums.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mystify.common.base.BaseController;
import com.mystify.ums.entity.UmsPermission;
import com.mystify.ums.entity.UmsUser;
import com.mystify.ums.service.UmsApiService;
import com.mystify.ums.service.UmsSystemService;
import com.mystify.ums.service.UmsUserService;


/**
 * 后台controller
 * Created by Zhangrydge on 2017/01/19.
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ManageController.class);

    @Autowired
	private UmsSystemService umsSystemService;
    
    @Autowired
	private UmsUserService  umsUserService;
    
    @Autowired
	private UmsApiService upmsApiService;

	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		// 当前登录用户权限
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UmsUser upmsUser = new UmsUser();
		upmsUser.setUsername(username);
		upmsUser = umsUserService.selectOne(upmsUser);
		List<UmsPermission> umsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getId());
		modelMap.put("umsPermissions", umsPermissions);
		modelMap.put("umsUser", upmsUser);
		return "/index";
	}

}