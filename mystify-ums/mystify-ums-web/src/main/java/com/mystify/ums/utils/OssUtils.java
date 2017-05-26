package com.mystify.ums.utils;

import java.io.File;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;

public class OssUtils {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File uploadedFile = new File("F:/99.jpg");
		uploadImgFile2Oss(uploadedFile,LocalCache.ossImgFile+"99.jpg");
	}
	
	
	 
	/**
	 * 上传图片到oss服务器
	 * @param file 图片文件
	 * @param key 文件目录和名称  文件夹/文件.jpg
	 * @return 可访问的图片url
	 */
	public static String uploadImgFile2Oss(File file,String key){
		OSSClient ossClient = new OSSClient(LocalCache.ossEndpoint, LocalCache.ossAccessKeyId, LocalCache.ossAccessKeySecret);
		if (!ossClient.doesBucketExist(LocalCache.ossBucketName)) {
            ossClient.createBucket(LocalCache.ossBucketName);
            CreateBucketRequest createBucketRequest= new CreateBucketRequest(LocalCache.ossBucketName);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
		ossClient.putObject(new PutObjectRequest(LocalCache.ossBucketName, key, file));
		boolean exists = ossClient.doesObjectExist(LocalCache.ossBucketName, key);
		if(exists){
			return LocalCache.ossImgurl+key;
		}else{
			return null;
		}
		
	}

}
