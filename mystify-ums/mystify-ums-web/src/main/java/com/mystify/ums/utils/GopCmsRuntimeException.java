package com.mystify.ums.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 运行异常
 * 2014-2014年4月24日
 */
public class GopCmsRuntimeException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorCode = "";  //错误代码
	String errorMsg = "";	//错误信息
	String errorCause = ""; //错误原因
	String errorAction = "";//建议解决方案
	String errorSn = ""; //错误唯一号
	
	protected Throwable throwable;
	public GopCmsRuntimeException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.errorSn = getNewSn();
	}

	public GopCmsRuntimeException(String errorCode, String errorMsg,
			String errorCause, String errorAction) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.errorCause = errorCause;
		this.errorAction = errorAction;
		this.errorSn = getNewSn();
	}

	/**
	 * 获取新的唯一错误号
	 * @return -- 有序UUID值
	 */
	protected static String getNewSn() {
		UUID uid = UUID.randomUUID();
		long hash = toDJBHash(uid.toString());
		long now = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder(50);
		sb.append(Long.toHexString(now));
		sb.append("-");
		sb.append(Long.toHexString(hash)); 
		return sb.toString();		
	}
	/**
	 * DJB hash 算法
	 * @param key 
	 * @return
	 */
	protected static long toDJBHash(String key) {
		long hash = 5381;
		for (int i = 0; i < key.length(); i++) {
			hash = ((hash << 5) + hash) + key.charAt(i);
		}
		return hash;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n@").append(this.getClass().getSimpleName()).append("{");
		sb.append(" errorSn:'").append(errorSn).append("',");
		sb.append(" errorCode:'").append(errorCode).append("',");
		sb.append("\n errorMsg:'").append(errorMsg).append("',");
		sb.append("\n errorCause:'").append(errorCause).append("',");
		sb.append("\n errorAction:'").append(errorAction+"'");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 根据异常类型样本类型和异常原因对象构造一个Map对象
	 * @param ex - 异常对象
	 * @return - Map对象: {errorSn, errorCode, errorMsg, errorCause, errorAction}
	 */
	public Map<String, String> toMap() {
		Map<String, String> errorMap = new LinkedHashMap<String, String>();
		errorMap.put("errorCode", this.getErrorCode());
		errorMap.put("errorSn", this.getErrorSn());
		errorMap.put("errorMsg", this.getErrorMsg());
		errorMap.put("errorCause", this.getErrorCause());
		errorMap.put("errorAction", this.getErrorAction());
		return errorMap;
	}
	
	public void setThrowable(Exception exception) {
		this.throwable = exception;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
	public String printString() {
		StringWriter stringWriter = new StringWriter();
	    PrintWriter pw = new PrintWriter(stringWriter);
	    printStackTrace(pw);
	    return stringWriter.toString();
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	public String getErrorAction() {
		return errorAction;
	}
	public void setErrorAction(String errorAction) {
		this.errorAction = errorAction;
	}

	public String getErrorSn() {
		return errorSn;
	}

	public void setErrorSn(String errorSn) {
		this.errorSn = errorSn;
	}
	
}
