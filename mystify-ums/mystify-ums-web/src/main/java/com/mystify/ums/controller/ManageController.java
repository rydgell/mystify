package com.mystify.ums.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mystify.common.base.BaseController;
import com.mystify.ums.service.UmsSystemService;


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

	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		 
		// 已注册系统
		/*UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
		upmsSystemExample.createCriteria()
				.andStatusEqualTo((byte) 1);
		List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(upmsSystemExample);
		modelMap.put("upmsSystems", upmsSystems);
		// 当前登录用户权限
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
		List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());
		modelMap.put("upmsPermissions", upmsPermissions);*/
		return "/system/systemIndex";
	}

}