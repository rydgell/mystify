package com.mystify.ums.model;

import java.util.Date;

import org.apache.shiro.session.Session;

import com.mystify.ums.shiro.session.UpmsSession;

public class SessionView {
	
	private String id;
	
	private Date startTimestamp;
	
	private Date lastAccessTime;
	
	private Boolean expired;
	
	private String host;
	
	private String userAgent;
	
	private String status;
	
	private Long timeout;
	
	public SessionView(){
		
	}
	public SessionView(UpmsSession session){
		this.id = session.getId().toString();
		this.startTimestamp = session.getStartTimestamp();
		this.lastAccessTime = session.getLastAccessTime();
		this.expired = session.isExpired();
		this.host = session.getHost();
		this.userAgent = session.getUserAgent();
		this.status = session.getStatus().toString();
		this.timeout = session.getTimeout();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	
	
}
