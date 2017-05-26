package com.mystify.ums.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.mystify.ums.utils.CommonUtil;
import com.mystify.ums.utils.LocalCache;
import com.mystify.ums.utils.OssUtils;

@Controller
@RequestMapping("/upload")
public class UploadFileController {
	
	private static final Logger LOGGER = Logger.getLogger(UploadFileController.class);
	
	 
	/**
	 * @param response
	 * @param request
	 * @param width 要裁剪的宽
	 * @param height 要裁剪的高
	 * @param maxWidth 图片的最大宽度
	 * @param maxHeight 图片的最大高度
	 * @param minWidth 图片的最小宽度
	 * @param type 缩略图方式  默认:  null/0 (最优保持原形) ; 1 :(强制裁剪)
	 * @param file 上传的图片
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage.do", method = RequestMethod.POST)
	@ResponseBody
	public void addFileUp(HttpServletResponse response, HttpServletRequest request,
			Integer width,Integer height,Integer maxWidth,Integer maxHeight,Integer minWidth,Integer type,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		byte[] bytes = file.getBytes();
		InputStream inputStream;
		try{
			inputStream = new ByteArrayInputStream(bytes);
			BufferedImage image = ImageIO.read(inputStream); 
			inputStream.close();
			if(image.getWidth()>maxWidth||image.getHeight()>maxHeight){
				response.getWriter().write("sizeError");
				return;
			}else if(minWidth!=null&&image.getWidth()<minWidth){
				response.getWriter().write("minWidthError");
				return;
			}
		}catch(Exception e){
			return;
		}
		
		String fileName =  String.valueOf(System.currentTimeMillis())+new Random().nextInt(999);
		String realFileName = file.getOriginalFilename();
		fileName = fileName + realFileName.substring(realFileName.lastIndexOf("."), realFileName.length());
		File temp = File.createTempFile(fileName, realFileName.substring(realFileName.lastIndexOf(".")));
		file.transferTo(temp);
		
		//原图
		String imgurl  = OssUtils.uploadImgFile2Oss(temp, LocalCache.ossImgFile+fileName);
		File thumbnailFile =null;
		if(type==null||type==0){
			thumbnailFile = CommonUtil.formatImgToThumbnailator(temp,width, height,"jpg");
		}else if (type==1){
			thumbnailFile = CommonUtil.formatCutImgToThumbnailator(temp,width, height,"jpg");
		}
		
		
		if(thumbnailFile==null){
			response.getWriter().write("thumbnailError");
			return;
		}
		String thumbnailurl  = OssUtils.uploadImgFile2Oss(thumbnailFile, LocalCache.ossThumbnailFile+thumbnailFile.getName());
		StringBuffer str = new StringBuffer(); 
		// 原图路径,缩略图路径
		str.append(imgurl).append(",").append(thumbnailurl);
		LOGGER.info(str.toString());
		temp.deleteOnExit();
		thumbnailFile.deleteOnExit();
		response.getWriter().write(str.toString());
		
		//原图存储路劲
		/*String localFilePath = LocalCache.LOCALFILEPATH + fileName;
		File uploadedFile = new File(localFilePath);
		FileCopyUtils.copy(bytes, uploadedFile);
		
		String thumbnailName = CommonUtil.formatImgToThumbnailator(localFilePath, width, height, LocalCache.LOCALTHUMBNAILFILEPATH,"jpg");
		
		if(thumbnailName==null){
			response.getWriter().write("thumbnailError");
			return;
		}
		
		StringBuffer str = new StringBuffer(); 
		// 原图路径,缩略图路径
		str.append(LocalCache.ACCESSIMGURL+fileName).append(",").append(LocalCache.ACCESSTHUMBNAILURL+thumbnailName);
		response.getWriter().write(str.toString());
		*/
	}
	
	
	/*
	* 上传图片
	*/
	@RequestMapping(value = "/ckeditorUploadImg.do")
	public void ckeditorUploadImg(@RequestParam("upload") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();
			String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
			String uploadContentType = file.getContentType();
			String expandedName = "";
			if (uploadContentType.equals("image/pjpeg")|| uploadContentType.equals("image/jpeg")) {
				expandedName = ".jpg";
			} 
			else if (uploadContentType.equals("image/png")|| uploadContentType.equals("image/x-png")) {
				expandedName = ".png";
			} 
			else if (uploadContentType.equals("image/gif")) {
				expandedName = ".gif";
			} 
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction("
				+ CKEditorFuncNum + ",'',"+ "'文件格式不正确（必须为.jpg/.gif/.jpeg/.png文件）');");
				out.println("</script>");
				return ;
			}
			if (file.getSize() > 1024 * 1024 * 1) {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction("+ CKEditorFuncNum + ",''," + "'文件大小不得大于1M');");
				out.println("</script>");
				return ;
			}
				String fileName =  String.valueOf(System.currentTimeMillis())+new Random().nextInt(999);
				fileName = fileName + expandedName;
				File temp = File.createTempFile(fileName, expandedName);
				file.transferTo(temp);
				String imgurl  = OssUtils.uploadImgFile2Oss(temp, LocalCache.ossImgFile+fileName);
				
				if(imgurl==null){
					out.println("<script type=\"text/javascript\">");
					out.println("window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum + ",'',"+ "'上传失败!');");
					out.println("</script>");
					return ;
				}
				
				//String localFilePath = LocalCache.LOCALFILEPATH + fileName;
				//存储路劲
				//file.transferTo(new File(localFilePath));
 
				/*CommonUtil.formatImgToScale(localFilePath, 0.5f, LocalCache.LOCALTHUMBNAILFILEPATH,"jpg");*/
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction("
						+ CKEditorFuncNum + ",'" + imgurl+ "','')");
				out.println("</script>");
				return ;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
				return  ;
			}
		 
	}
	 
	@RequestMapping(value = "/ueditorUploadImg.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload(@RequestParam("upfile") MultipartFile upfile,Integer maxSize,
			HttpServletRequest request, HttpServletResponse response) {
		 
		String fileName =  String.valueOf(System.currentTimeMillis())+new Random().nextInt(999);
		String uploadContentType = upfile.getContentType();
		String expandedName = "";
		JSONObject result = new JSONObject();
		if (uploadContentType.equals("image/pjpeg")|| uploadContentType.equals("image/jpeg")) {
			expandedName = ".jpg";
		} 
		else if (uploadContentType.equals("image/png")|| uploadContentType.equals("image/x-png")) {
			expandedName = ".png";
		} 
		else if (uploadContentType.equals("image/gif")) {
			expandedName = ".gif";
		}
		else if (uploadContentType.equals("image/bmp")) {
			expandedName = ".bmp";
		}
		else {
			result.put("state", "文件格式不正确（必须为.jpg/.gif/.jpeg/.png/.bmp文件）')");
			return result.toJSONString();
		}
		
		if (upfile.getSize() > 1024 * 1024 * 1) {
			result.put("state", "文件大小超出限制,不能大于"+maxSize+"KB");
			return result.toJSONString();
		}
		
		fileName = fileName + expandedName;
		File temp;
		
	
		try {
			temp = File.createTempFile(fileName, expandedName);
			upfile.transferTo(temp);
			String imgurl  = OssUtils.uploadImgFile2Oss(temp, LocalCache.ossImgFile+fileName);
			result.put("state", "SUCCESS");
			result.put("url", imgurl);
			result.put("title", fileName);
			result.put("original", fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("state", "上传图片失败!");
		}
		
		return result.toJSONString();
	
	}
	
}
