package com.mystify.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mystify.common.Constants;
import com.mystify.common.utils.CacheUtil;


/**
 * 会话监听器
 * 
 * @author rydge
 * @version $Id: SessionListener.java, v 0.1 2014年3月28日 上午9:06:12 rydge Exp
 */
public class SessionListener implements HttpSessionListener {
	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		session.setAttribute(Constants.WEBTHEME, "default");
		logger.info("创建了一个Session连接:[" + session.getId() + "]");
		setAllUserNumber(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if (getAllUserNumber() > 0) {
			logger.info("销毁了一个Session连接:[" + session.getId() + "]");
		}
		session.removeAttribute(Constants.CURRENT_USER);
		setAllUserNumber(-1);
	}

	private void setAllUserNumber(int n) {
        String key = Constants.CACHE_NAMESPACE + "SESSION:LOCK";
        while (!CacheUtil.getLock(key)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        try {
            Integer number = getAllUserNumber() + n;
            if (number >= 0) {
                logger.info("用户数：" + number);
                CacheUtil.getCache().set(Constants.ALLUSER_NUMBER, number);
            }
        } finally {
            CacheUtil.unlock(key);
        }
	}

	/** 获取在线用户数量 */
	public static Integer getAllUserNumber() {
		Integer v = (Integer) CacheUtil.getCache().get(Constants.ALLUSER_NUMBER);
		if (v != null) {
			return v;
		}
		return 0;
	}
}
