/**
 * 
 */
package com.mystify.common.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.mystify.common.Constants;
import com.mystify.common.exception.BaseException;
import com.mystify.common.exception.IllegalParameterException;
import com.mystify.common.support.HttpCode;
import com.mystify.common.utils.CacheUtil;
import com.mystify.common.utils.InstanceUtil;
import com.mystify.common.utils.WebUtil;

/**
 * 控制器基类
 * 
 * @author rydge
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/** 获取当前用户Id */
	protected Long getCurrUser() {
		return WebUtil.getCurrentUser();
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		Map<String, Object> map = InstanceUtil.newLinkedHashMap();
		map.putAll(modelMap);
		modelMap.clear();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
				modelMap.put(key, map.get(key));
			}
		}
		if (data != null) {
			if (data instanceof Page) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
				modelMap.put("iTotalRecords", page.getTotal());
				modelMap.put("iTotalDisplayRecords", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("iTotalRecords", ((List<?>) data).size());
				modelMap.put("iTotalDisplayRecords", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("httpCode", code.value());
		modelMap.put("msg", code.msg());
		modelMap.put("timestamp", System.currentTimeMillis());
		return ResponseEntity.ok(modelMap);
	}

	/** 异常处理 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		logger.error(Constants.Exception_Head, ex);
		ModelMap modelMap = new ModelMap();
		if (ex instanceof BaseException) {
			((BaseException) ex).handler(modelMap);
		} else if (ex instanceof IllegalArgumentException) {
			new IllegalParameterException(ex.getMessage()).handler(modelMap);
		} else if (ex instanceof UnauthorizedException) {
			modelMap.put("httpCode", HttpCode.FORBIDDEN.value());
			modelMap.put("msg", StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.FORBIDDEN.msg()));
		} else {
			modelMap.put("httpCode", HttpCode.INTERNAL_SERVER_ERROR.value());
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
			modelMap.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}
		response.setContentType("application/json;charset=UTF-8");
		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info(JSON.toJSON(modelMap).toString());
		byte[] bytes = JSON.toJSONBytes(modelMap, SerializerFeature.DisableCircularReferenceDetect);
		response.getOutputStream().write(bytes);
	}
}
