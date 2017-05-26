package com.mystify.ums.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;


/**
 * 一些基础的公共操作
 *
 */
public class CommonUtil 
{
	
	  private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
	  private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
	  private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	  private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
	  
	  public static final String date_time_format = "yyyy-MM-dd HH:mm:ss";
	  
	  public static final String date_format = "yyyy-MM-dd";
	  
	  private static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿","十亿", "百亿", "千亿", "万亿" };
	  private static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

				 

	public static String foematInteger(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
		String m = val[i] + "";
		int n = Integer.valueOf(m);
		boolean isZero = n == 0;
		String unit = units[(len - 1) - i];
		if (isZero) {
		if ('0' == val[i - 1]) {
		// not need process if the last digital bits is 0
		continue;
		} else {
		// no unit for 0
		sb.append(numArray[n]);
		}
		} else {
		sb.append(numArray[n]);
		sb.append(unit);
		}
		}
		return sb.toString();
	}
	
	/**
	 * 把整数转换成
	 * @param val
	 * @return
	 */
	public static String IntegerToString(Integer val)
	{
		if(val==null)
		{
			return null;
		}
		try
		{
			return String.valueOf(val) ;
		}
		catch(Exception e)
		{
			throw new RuntimeException("IntegerToString ERROR!");
		}
	}
	
	/**
	 * 数字转换成String
	 * @param val
	 * @return
	 */
	public static Integer StringToInteger(String val)
	{
		if(val==null || "".equals(val))
		{
			return null;
		}
		try
		{
			return Integer.valueOf(val) ;
		}
		catch(Exception e)
		{
			throw new RuntimeException("StringConvertToInteger ERROR!");
		}
	}
	
	/**
	 * 判断是否为空，当参数为String时,判断是否为空，或为空字符串,
	 * 当参数为List时，判断List是否为null或list是否为空Ｌｉｓｔ
	 * 当参数为Map时， 判断Map是否为空， 或Map是否为空,
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object obj)
	{
		if(obj==null)
		{
			return true ; //如果为空， 直接返回true; 
		}
		if(obj instanceof String)
		{
			return "".equals(((String) obj).trim());
		}
		else if(obj instanceof List)
		{
			return 0==((List)obj).size();
		}
		else if(obj instanceof Map)
		{
			return 0== ((Map)obj).size();
		}
		else
		{
			throw new RuntimeException("不支持的参数!");
		}
	}
	/**
	 * 判断非空
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj)
	{
		return !isEmpty(obj);
	}
	
	/**
	 * 检查一个字符串是否整数
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str)
	{
		return str.matches("^[-+]?([0-9]+)$");
	}
	
	
	/**
	 * 以 yyyy-MM-dd HH:mm:ss 的格式返回当前时间
	 * @return
	 */
	public static String getNowTimeStr()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 返回 yyyyMMddHHmmss 格式
	 * @return
	 */
	public static String getTimeStr(Date date)
	{
		String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		d= d.replaceAll("-", "");
		d= d.replaceAll(":", "");
		d= d.replaceAll(" ", "");
		return d;
	}
	
	/**
	 * 随机一个抽奖码
	 * @param range 最大值
	 * @return
	 */
	public static int randomCode(double range){
		// 产生1至range之间的一个随机数
		int random =  (int)((Math.random()*range)+1);
		return random;
	}
	
	public static Date getDateByStr(String dateStr) {
		if (StringUtils.isNotEmpty(dateStr)) {
			SimpleDateFormat formatter = null;
			if (dateStr.length() == 10)
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			else if (dateStr.length() == 16)
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			else if (dateStr.length() == 19)
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else {
				return null;
			}
			try {
				return formatter.parse(dateStr);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 格式化时间
	 * @param date	要格式化的时间
	 * @param format 格式化字符串
	 * @return
	 */
	public static String formatDate(Date date, String format)
	{
		try
		{
			if(date==null)
			{
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		catch(Exception e)
		{
			throw new RuntimeException("日期转换成字符串失败,失败原因: " + e.getMessage());
		}
		
		
	}
	
	/**
     * 以同步方式请求某个URL，并返回响应内容
     * author: zhimin.mao@balintimes.com
     * @param url  页面地址
     * @param charset 页面编码，如:utf-8，gb2312
     * @param timeout 超时时间，单位：秒
     * @return
	 * @throws Exception 
     */
    public static String httpGet(String url, String charset, Integer timeout) throws Exception{
        StringBuilder contentBuilder = new StringBuilder(1024);
        try{
            URL uri = new URL(url);
            timeout = timeout * 1000;
            HttpURLConnection connection = (HttpURLConnection)uri.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/html;charset="+charset);
            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(timeout);
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(
                                            new InputStreamReader(connection.getInputStream(), charset)
                                        );
                char[] buffer = new char[0x20];
                while(true){
                    int size = reader.read(buffer);
                    if(size > 0){
                        contentBuilder.append(buffer,  0, size);
                    }else{
                        break;
                    }
                }
                reader.close();
            }
            connection.disconnect();
        } catch (Exception e) {
        	throw new Exception(String.format("请求外部地址[%s]时出错;error:%s", url,e));
        }
        return contentBuilder.toString();
    }
	
    /**
	 * 生成n位随机数
	 * @param n  
	 * @return
	 */
	public  static String getRanDomNum(int n){
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < n; i++)
			result.append(array[i]) ;
		//System.out.println("产生的6位随机数："+result.toString());
		return result.toString();
	}

	 
	/**
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，尽量保持图片完整性
	 * @param imgPath 原图路径
	 * @param w 压缩的宽
	 * @param h 压缩的高
	 * @param imgThumbnailPath 缩略图存放路径 
	 * @param formatImgType png,jpg,jpeg.gif (png的画质好,jpg的压缩率高)
	 * @return
	 */
	public static String formatImgToThumbnailator(String imgPath,int w,int h,String imgThumbnailPath,String formatImgType) {
		try {
			File imgFile = new File(imgPath);
	        BufferedImage image = ImageIO.read(imgFile);  
	        Builder<BufferedImage> builder = null;    
	        int imageWidth = image.getWidth();  
	        int imageHeitht = image.getHeight();  
	        
	        if ((float)w / h != (float)imageWidth / imageHeitht) {  
			    if (imageWidth > imageHeitht) {  
			    	image = Thumbnails.of(imgFile).height(h).asBufferedImage();  
			    } else {  
			    	image = Thumbnails.of(imgFile).width(w).asBufferedImage();  
			    }  
			    builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);  
	        } else {  
	            builder = Thumbnails.of(image).size(w, h);  
	        }  
	        String name = imgFile.getName();
	        name = name.substring(0, name.indexOf("."));
	        StringBuffer thumbnailName = new StringBuffer();
	        thumbnailName.append("thumbnail_").append(name).append(".").append(formatImgType);
	        builder.outputFormat(formatImgType).toFile(imgThumbnailPath+thumbnailName);  
	         
	        return thumbnailName.toString();
		}catch (IOException e){
			e.printStackTrace();
			 return null;
		}
       
	}
	
	/**
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，尽量保持图片完整性
	 * @param imgPath 原图路径
	 * @param w 压缩的宽
	 * @param h 压缩的高
	 * @param imgThumbnailPath 缩略图存放路径 
	 * @param formatImgType png,jpg,jpeg.gif (png的画质好,jpg的压缩率高)
	 * @return
	 */
	public static File formatImgToThumbnailator(File tempFile,int w,int h,String formatImgType) {
		try {
	        BufferedImage image = ImageIO.read(tempFile);  
	        Builder<BufferedImage> builder = null;    
	        int imageWidth = image.getWidth();  
	        int imageHeitht = image.getHeight();  
	        
	        if ((float)w / h != (float)imageWidth / imageHeitht) {  
			    if (imageWidth > imageHeitht) {  
			    	image = Thumbnails.of(image).height(h).asBufferedImage();  
			    } else {  
			    	image = Thumbnails.of(image).width(w).asBufferedImage();  
			    }  
			    builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);  
	        } else {  
	            builder = Thumbnails.of(image).size(w, h);  
	        }  
	        File temp = File.createTempFile("thumbnail_","."+formatImgType);
	        builder.outputFormat(formatImgType).toFile(temp);  
	        return temp;
		}catch (IOException e){
			 return null;
		}
       
	}
	
	
	/**
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，以图片的中心进行裁剪,图片不完整,多余部分裁剪掉
	 * @param imgPath 原图路径
	 * @param w 压缩的宽
	 * @param h 压缩的高
	 * @param imgThumbnailPath 缩略图存放路径 
	 * @param formatImgType png,jpg,jpeg.gif (png的画质好,jpg的压缩率高)
	 * @return
	 */
	public static File formatCutImgToThumbnailator(File tempFile,int w,int h,String formatImgType) {
		try {
	        BufferedImage image = ImageIO.read(tempFile);  
	        Builder<BufferedImage> builder = null;    
	        builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 750,
					250).size(750, 250).keepAspectRatio(false);
	        File temp = File.createTempFile("thumbnail_","."+formatImgType);
	        builder.outputFormat(formatImgType).toFile(temp);  
	        return temp;
		}catch (IOException e){
			 return null;
		}
       
	}
	
	/**
	 * 按照比例进行缩放
	 * @param imgPath
	 * @param scale
	 * @param imgThumbnailPath
	 * @param formatImgType
	 * @return
	 */
	public static String formatImgToScale(String imgPath,float scale,String imgThumbnailPath,String formatImgType) {
		try {
			File imgFile = new File(imgPath);
	        BufferedImage image = ImageIO.read(imgFile);  
	        Builder<BufferedImage> builder = null;    
	        builder =  Thumbnails.of(image).scale(scale);
	        String name = imgFile.getName();
	        name = name.substring(0, name.indexOf("."));
	        StringBuffer thumbnailName = new StringBuffer();
	        thumbnailName.append("thumbnail_").append(name).append(".").append(formatImgType);
	        builder.outputFormat(formatImgType).toFile(imgThumbnailPath+thumbnailName);  
	         
	        return thumbnailName.toString();
		}catch (IOException e){
			 return null;
		}
       
	}
	
	 /**
		 * 随机指定范围内N个不重复的数
		 * 利用HashSet的特征，只能存放不同的值
		 * @param min 指定范围最小值
		 * @param max 指定范围最大值
		 * @param n 随机数个数
		 * @param HashSet<Integer> set 随机数结果集
		 */
	    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
	        if (n > (max - min + 1) || max < min) {
	            return;
	        }
	        for (int i = 0; i < n; i++) {
	            // 调用Math.random()方法
	            int num = (int) (Math.random() * (max - min)) + min;
	            set.add(num);// 将不同的数存入HashSet中
	        }
	        int setSize = set.size();
	        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
	        if (setSize < n) {
	        	randomSet(min, max, n - setSize, set);// 递归
	        }
	    }
	    
	    
	   
	    
	    /**
		 * 2个日期直接相隔的天数
		 * 
		 * @param startDate
		 * @param endDate
		 * @return
		 */
		public static int getDiffDay(Date startDate, Date endDate) {
			int day = 0;
			try {
				day = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 24 * 3600));// 天数
			} catch (Exception e) {

			}
			return day;
		}
		
		
		/** 
	     * 两个时间相差  多少分 
	     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
	     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
	     */  
	    public static long getDiffMin(String str1, String str2) {  
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        Date one;  
	        Date two;  
	        long day = 0;  
	        long hour = 0;  
	        long min = 0;  
	        try {  
	            one = df.parse(str1);  
	            two = df.parse(str2);  
	            long time1 = one.getTime();  
	            long time2 = two.getTime();  
	            long diff ;  
	            diff = time2 - time1;  
	            day = diff / (24 * 60 * 60 * 1000);  
	            hour = (diff / (60 * 60 * 1000) - day * 24);  
	            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        return min;  
	    }  
	    
	      
	    /** 
	     * @param htmlStr 
	     * @return 
	     *  删除Html标签 
	     */  
	    public static String delHTMLTag(String htmlStr) {  
	        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        Matcher m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	  
	        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        Matcher m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	  
	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        Matcher m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	  
	        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
	        Matcher m_space = p_space.matcher(htmlStr);  
	        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
	        
	        htmlStr = htmlStr.replaceAll("&nbsp", "");
	        htmlStr = htmlStr.replaceAll("&amp", "");
	        return htmlStr.trim(); // 返回文本字符串  
	    }  
	   
	    /** 
	     * 从网络Url中下载文件 
	     * @param urlStr 
	     * @param fileName 
	     * @param savePath 
	     * @throws IOException 
	     */  
	    public static File  downLoadFromUrl(String urlStr) {  
	        try{
	    	URL url = new URL(urlStr);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为5秒  
	        conn.setConnectTimeout(5*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();    
	        //获取自己数组  
	        byte[] getData = readInputStream(inputStream);      
	  
	        //文件保存位置  
	        String fileName = url.getFile();
	        String suffix = "";
	        if(fileName.lastIndexOf(".")==-1){
	        	suffix = ".jpg";
	        }else{
	        	suffix = fileName.substring(fileName.lastIndexOf("."));
	        }
	        
	        File temp = File.createTempFile(url.getFile(), suffix);
   
	        FileOutputStream fos = new FileOutputStream(temp);       
	        fos.write(getData);   
	        if(fos!=null){  
	            fos.close();    
	        }  
	        if(inputStream!=null){  
	            inputStream.close();  
	        }  
	  
	        return temp;
	        }catch(Exception e){
	        	e.printStackTrace();
	        	return null;
	        }
	    }  
	    
	    /** 
	     * 从输入流中获取字节数组 
	     * @param inputStream 
	     * @return 
	     * @throws IOException 
	     */  
	    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
	        byte[] buffer = new byte[1024];    
	        int len = 0;    
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	        while((len = inputStream.read(buffer)) != -1) {    
	            bos.write(buffer, 0, len);    
	        }    
	        bos.close();    
	        return bos.toByteArray();    
	    }    
	    
	    
	     
	    /** 
	     * 返回存有图片地址的数组 
	     * @param tar 
	     * @return 
	     */  
	    public static Set<String> getHtmlImgSrc(String contentStr){  
	        Set<String> res = new HashSet<String>();
	        String regexImage = "<img.+?src=\"(.+?)\".+?/?>";       
	        String ImageSrcStr="";  
	        Pattern p = Pattern.compile(regexImage,Pattern.CASE_INSENSITIVE);  
	        Matcher m = p.matcher(contentStr);  
	        while (m.find()){  
	            ImageSrcStr = m.group(1);  
	            res.add(ImageSrcStr);
	        }    
	        return res;  
	    }  
	    
	    /**
		 * 获取时间为23:59:59的日期
		 * 
		 * @param date
		 * @return
		 */
		public static Date getDate23_59_59(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			return getDateByStr(sdf.format(date));
		}
		
		 
		
		/**
		 * 获取时间为00:00:00的日期
		 * 
		 * @param date
		 * @return
		 * @Author: seara
		 */
		public static Date getDate00_00_00(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			return getDateByStr(sdf.format(date));
		}
		
		 /**
		 * 获取昨天的日期
		 * @return
		 */
		public static Date getyesterday(){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			return calendar.getTime();
		}
		
		/** 
		   * 得到几天后的时间 
		   * @param d 
		   * @param day 
		   * @return 
		   */  
		  public static Date getDateAfter(Date d,int day){  
		   Calendar now =Calendar.getInstance();  
		   now.setTime(d);  
		   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
		   return now.getTime();  
		  }  
	    
	    public  static void main(String p[]){
	    	//HashSet<Integer> random = new HashSet<Integer>();
	    	//randomSet(0, 2, 2, random);
	    	//System.out.println(getDiffMin("2016-06-24 14:15:39", "2016-06-24 14:45:39"));
//	    	for(int i=0;i<100;i++)
//	    	System.out.println(getRanDomNum(2));
	    	System.out.println(getTimeStr(new Date()));
	    }
}
