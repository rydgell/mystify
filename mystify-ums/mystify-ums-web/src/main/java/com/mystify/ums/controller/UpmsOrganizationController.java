package com.mystify.ums.controller;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

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

import com.mystify.common.base.BaseController;
import com.mystify.common.exception.IllegalParameterException;
import com.mystify.common.validator.LengthValidator;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.service.UmsOrganizationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织controller
 * Created by rydge on 2017/2/6.
 */
@Controller
@Api(value = "组织管理", description = "组织管理")
@RequestMapping("/manage/organization")
public class UpmsOrganizationController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsOrganizationController.class);

    @Autowired
    private UmsOrganizationService umsOrganizationService;

    @ApiOperation(value = "组织首页")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/organization/index";
    }

    @ApiOperation(value = "组织列表")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
    	Page<UmsOrganization> page = new Page<UmsOrganization>(offset,limit);
    	EntityWrapper<UmsOrganization> entity = new EntityWrapper<UmsOrganization>();
    	page = umsOrganizationService.selectPage(page, entity);
        //long total = upmsOrganizationService.countByExample(upmsOrganizationExample);*/
        Map<String, Object> result = new HashMap<>();
        result.put("rows", page.getRecords());
        result.put("total", page.getTotal());
        return result;
    }

    @ApiOperation(value = "新增组织")
    @RequiresPermissions("upms:organization:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UmsOrganization umsOrganization,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsOrganization.getName(), new LengthValidator(1, 30, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
       if (!result.isSuccess()) {
    	   throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        long time = System.currentTimeMillis();
        umsOrganization.setCtime(time);
        umsOrganizationService.update(umsOrganization);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除组织")
    @RequiresPermissions("upms:organization:delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id,ModelMap modelMap) {
    	umsOrganizationService.delete(id);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "修改组织")
    @RequiresPermissions("upms:organization:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UmsOrganization umsOrganization,ModelMap modelMap) {
        ComplexResult result = FluentValidator.checkAll()
                .on(umsOrganization.getName(), new LengthValidator(1, 30, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
        	 throw new IllegalParameterException("INVALID_LENGTH") ;
        }
        umsOrganizationService.update(umsOrganization);
        return setSuccessModelMap(modelMap);
    }

}
