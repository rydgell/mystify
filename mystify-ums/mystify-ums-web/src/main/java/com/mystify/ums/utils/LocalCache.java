package com.mystify.ums.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache {
	
	
	/**
	 * 本地图片存储路径
	 * 关于路径说明 :需要在tomcat的server.xml 配置好虚拟路径(如果有图片服务器请无视)具体请看config.properties,
	 * 文件夹需预先创建好,如果不是以root启动tomcat需要文件授权,存放路径可以在任何一个目录下
	 */
	//public static final String LOCALFILEPATH = System.getProperty("local.file.upload.path");
			 
	/**
	 * 本地缩略图路劲
	 */
	//public static final String LOCALTHUMBNAILFILEPATH = System.getProperty("local.file.thumbnail.path");

	/**
	 * 配置缩略图片访问路径	
	 */
	//public static final String ACCESSTHUMBNAILURL = System.getProperty("access.thumbnail.url");
			 
	/**
	 * 配置原图片访问路径		
	 */
	//public static final String ACCESSIMGURL = System.getProperty("access.img.url");
	
	public static final String encryptKey = "JiJi"; //密钥
	
	/**
	 * 托管中心特定角色ID 系统配置key
	 */
	public static final String careCenterRoleKey = "ROLE_ID"; 
	
	/**
	 * 攻略预览url超时时间  系统配置key
	 */
	public static final String urlTimeOutKey = "URL_TIMEOUT"; 
	
	/**
	 * 编辑过的文章是否需要审核 需要:1 不需要:0   系统配置key
	 */
	public static final String articleReAuditKey = "ARTICLE_REAUDIT"; 
	
	public static String GETWECHATTOKENURL ;
	
	public static String WECHATMENUSURL;
	
	
	public static String ossEndpoint ;
	
	public static String ossAccessKeyId ;
    
	public static String ossAccessKeySecret ;
    
	public static String ossBucketName ;
	
	public static String ossImgFile;
	 
	public static String ossThumbnailFile;
	
	public static String ossImgurl;
	
	public static String logEndpoint;
	
	public static String logAccessKeyId;
	
	public static String logAccessKeySecret;
	
	public static String logProject;
	
	public static String logLogstore;
	
	public static final String taskListTopic = "ToThemeIndex";//主题首页
	
	public static final String taskDetailTopic = "ToTaskDetail";//任务详情页
	
	public static final String testDetailTopic = "ToTestDetail";//测试题详情页
	
	public static final String finishTaskTopic = "ComplateTask";//完成任务
	
	public static final String finishTestTopic = "ComplateTest";//完成测试题
	  
	public static Map<Long, Boolean> hadInitChildExpCoefficient = new ConcurrentHashMap<Long, Boolean>();
	
	public static Map<Long, String> userTestResult = new ConcurrentHashMap<Long, String>(); 
	
	/**
	 * 默认问卷id
	 */
	public static final long testId =5;
	
	static{
		ossEndpoint = System.getProperty("oss.endpoint","http://oss-cn-shenzhen.aliyuncs.com");
		ossAccessKeyId = System.getProperty("oss.accessKeyId","EgSvMYl1u3szvGpE");
		ossAccessKeySecret = System.getProperty("oss.accessKeySecret","8Hs3d8k1nBHlByKelWYmppXITIuqWc");
		ossBucketName = System.getProperty("oss.bucketName","rz-img");
		ossImgFile = System.getProperty("oss.imgFile","imgs/");
		ossThumbnailFile = System.getProperty("oss.thumbnailFile","thumbnail/");
		ossImgurl = System.getProperty("oss.imgurl","http://img.run2smart.com/");
		
		logEndpoint = System.getProperty("log.endpoint","http://wechats.cn-shenzhen.log.aliyuncs.com");
		logAccessKeyId = System.getProperty("log.accessKeyId","EgSvMYl1u3szvGpE");
		logAccessKeySecret = System.getProperty("log.accessKeySecret","8Hs3d8k1nBHlByKelWYmppXITIuqWc");
		logProject = System.getProperty("log.project","wechats");
		logLogstore = System.getProperty("log.logstore","wechat_jjyx_log");
		
		GETWECHATTOKENURL =System.getProperty("wechat.token.url","http://localhost:10080/wechats/wx/getToken.json?code=1000");
		
		WECHATMENUSURL =System.getProperty("wechat.menu.url","http://localhost:10080/wechats/wx/addMenu.htm?menuStr=");
		
	}
	
	
	/**
	 * 判断该childId 是否已经初始化系数
	 * @param childId
	 * @return
	 */
	public static boolean checkInitChildExpCoefficient(Long childId){
		if(childId==null){
			return true;
		}
		Boolean init = hadInitChildExpCoefficient.get(childId);
		if(init==null){
			return false;
		}else{
			return init;
		}
	}
	
}
