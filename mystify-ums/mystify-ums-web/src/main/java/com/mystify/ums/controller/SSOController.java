package com.mystify.ums.controller;

 
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.mystify.common.base.BaseController;
import com.mystify.common.exception.LoginException;
import com.mystify.ums.service.UmsSystemService;
import com.mystify.ums.service.UmsUserService;
import com.mystify.ums.shiro.session.UpmsSessionDao;
import com.mystify.ums.shiro.session.UpmsSession;
import com.mystify.ums.utils.RedisUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * 单点登录管理
 * Created by rydge on 2016/12/10.
 */
@Controller
@RequestMapping("/sso")
@Api(value = "单点登录管理", description = "单点登录管理")
public class SSOController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(SSOController.class);
    // 全局会话key
    private final static String UMS_SERVER_SESSION_ID = "ums-server-session-id";
    // 全局会话key列表
    private final static String UMS_SERVER_SESSION_IDS = "ums-server-session-ids";
    // code key
    private final static String UMS_SERVER_CODE = "ums-server-code";

    @Autowired
    private UmsSystemService umsSystemService;

    @Autowired
    private UmsUserService umsUserService;

    @Autowired
    private UpmsSessionDao umsSessionDao;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	  System.out.println("login get");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        String code = RedisUtil.get(UMS_SERVER_SESSION_ID + "_" + serverSessionId);
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            String backurl = "/manage/index";
            _log.debug("认证通过，带code回跳：{}", backurl);
            return "redirect:" + backurl;
        }
        return "/login";
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password,String verificationCode, ModelMap modelMap) {
        System.out.println("login POST");
        Subject subject = SecurityUtils.getSubject();  
        Session session = subject.getSession();
        String kaptchaCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
        System.out.println("验证码: " + kaptchaCode );  
        if(StringUtils.isBlank(verificationCode)||!verificationCode.equalsIgnoreCase(kaptchaCode)){
        	 throw new LoginException("INVALID verificationCode");
        }
       
        String sessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(UMS_SERVER_SESSION_ID + "_" + sessionId);
        // code校验值
        if (StringUtils.isBlank(hasCode)) {
            // 使用shiro认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                throw new LoginException("INVALID username");
            } catch (IncorrectCredentialsException e) {
            	 throw new LoginException("INVALID PASSWORD");
            } catch (LockedAccountException e) {
            	throw new LoginException("locking user");
            }
            // 更新session状态
            umsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(UMS_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(UMS_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(UMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        String backurl = "/manage/index";
        modelMap.addAttribute("data",backurl );
        return setSuccessModelMap(modelMap);
    }

    
    @Autowired  
    private Producer captchaProducer = null;  
  
    @ApiOperation(value = "验证码")
    @RequestMapping(value = "/code",method = RequestMethod.GET)  
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpSession session = request.getSession();  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        
        String capText = captchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }
    
    @ApiOperation(value = "测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test(String xx) {
    	System.out.println("--------------------------------------> "+xx);
    	return "xxoo";
    }

}