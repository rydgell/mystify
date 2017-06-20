package com.mystify.ums.controller;

 
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

import com.baomidou.mybatisplus.plugins.Page;
import com.mystify.common.base.BaseController;
import com.mystify.common.base.BasePage;
import com.mystify.ums.entity.UmsLog;
import com.mystify.ums.entity.UmsOrganization;
import com.mystify.ums.service.UmsLogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志controller
 * Created by rydge on 2017/3/14.
 */
@Controller
@Api(value = "日志管理", description = "日志管理")
@RequestMapping("/manage/log")
public class UpmsLogController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(UpmsLogController.class);

    @Autowired
    private UmsLogService umsLogService;

    @ApiOperation(value = "日志首页")
    @RequiresPermissions("upms:log:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/log/index";
    }

    @ApiOperation(value = "日志列表")
    @RequiresPermissions("upms:log:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "id",value = "sort") String sort,
            @RequestParam(required = false,value = "order") String order,UmsLog log) {
    	BasePage<UmsLog> page = new BasePage<UmsLog>(offset,limit,sort);
    	page.setAsc(false);
    	page = umsLogService.selectPage(page, log);
    	Map<String, Object> result = new HashMap<>();
    	result.put("rows", page.getRecords());
        result.put("total", page.getTotal());
        return result;
    }

    @ApiOperation(value = "删除日志")
    @RequiresPermissions("upms:log:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids,ModelMap modelMap) {
    	 String[] idList = ids.split("-");
    	 for(String id:idList){
    		 umsLogService.delete(Integer.valueOf(id));
    	 }
    	 return setSuccessModelMap(modelMap);
    }

}