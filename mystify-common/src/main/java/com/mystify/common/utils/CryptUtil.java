package com.mystify.common.utils;


import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;



/**
 ***************************************************************************** 
 ** Module :Android DES 加密和java DES加密结果不一样， 调用DES加密算法包最精要的就是下面两句话：
 * 
 * Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
 * 
 * cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
 * 
 * CBC是工作模式，DES一共有电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）四种模式，
 * 
 * PKCS5Padding是填充模式，还有其它的填充模式：
 * 
 * 然后，cipher.init()一共有三个参数：Cipher.ENCRYPT_MODE, key,
 * zeroIv，zeroIv就是初始化向量，一个8为字符数组。
 * 
 * 工作模式、填充模式、初始化向量这三种因素一个都不能少。否则，如果你不指定的话，那么就要程序就要调用默认实现。问题就来了，这就与平台有关了。（
 * 之前我并没有指定IV，导致结果不一致）
 ** 
 * 客服端加密算法，服务器需要解密 Date: 2011-11-6 Time: 上午11:22:59
 ** 
 ***************************************************************************** 
 */
public class CryptUtil { 
	
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final Logger LOGGER = Logger.getLogger(CryptUtil.class);
	/**
	 * 加密
	 * 
	 * @param encryptString
	 *            待加密的字符串
	 * @param encryptKey
	 *            key 密钥
	 * @return
	 */
	public static String encryptDES(String encryptString, String encryptKey) {
		encryptKey = getKey(encryptKey);
		if (encryptKey!=null&&!"".equals(encryptKey)) {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			try {
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
				byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
				return new String(Base64.encode(encryptedData));
			} catch (Exception e) {
				LOGGER.debug("加密异常", e);
			}
		}
		return "";
	}
	
	/**
	 * 加密
	 * 
	 * @param encryptString
	 *            待加密的字符串
	 * @param encryptKey
	 *            key 密钥
	 * @return
	 */
	public static String encryptDESUtf8(String encryptString, String encryptKey) {
		encryptKey = getKey(encryptKey);
		if (encryptKey!=null&&!"".equals(encryptKey)) {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			try {
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
				byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
				return encodeUTF8(new String(Base64.encode(encryptedData)));
			} catch (Exception e) {
				LOGGER.debug("加密异常", e);
			}
		}
		return "";
	}
	
	public static byte[] encryptDESBytes(String encryptString, String encryptKey) {
		encryptKey = getKey(encryptKey);
		if (encryptKey!=null&&!"".equals(encryptKey)) {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			try {
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
				byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
				return encryptedData;
			} catch (Exception e) {
				System.out.println("加密异常");
			}
		}
		return null;
	}
	
	public static String decryptDESBytes(byte[] byteMi, String decryptKey) {
		decryptKey = getKey(decryptKey);
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData, "utf-8");
		} catch (Exception e) {
			LOGGER.debug("解密异常", e);
		}
		return "";
	}
	
	/**
	 * 字节数组解密，用于post方式上报数据
	 * 
	 * @param src
	 * @return
	 */
	public static String decryptPostByte(byte[] src,String decryptKey) {
		if (src != null && src.length > 0) {
			return  decryptDESBytes(src, decryptKey);
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param encryptString
	 *            待解密的字符串
	 * @param encryptKey
	 *            key 密钥
	 */
	public static String decryptDES(String decryptString, String decryptKey) {
		decryptKey = getKey(decryptKey);
		try {
			byte[] byteMi = Base64.decode(decryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData, "utf-8");
		} catch (Exception e) {
			LOGGER.debug("解密异常", e);
		}
		return "";
	}
	
	private static String getKey(String decryptKey) {
		String key = null;
		if (decryptKey!=null&&!"".equals(decryptKey)) {
			int length = decryptKey.length();
			if (length > 8) {
				key = decryptKey.substring(0, 8);
			} else {
				int s = 8 - length;// 相差多少个字符
				StringBuilder sb = new StringBuilder(decryptKey);
				for (int i = 0; i < s; i++) {
					sb.append("*");
				}
				key = sb.toString();
			}
		}
		return key;
	}
	
	
	/**
	 * 将字符串进行utf-8编码
	 * 
	 * @param src
	 * @return
	 * @Author: seara
	 */
	public static String encodeUTF8(String src) {
		String value = "";
			try {
				value = URLEncoder.encode(src, "utf-8");
			} catch (Exception e) {
		}
		return value;
	}
	
	String ss= "%3C!DOCTYPE%20html%20PUBLIC%20%5C%22-%2F%2FW3C%2F%2FDTD%20XHTML%201.0%20Transitional%2F%2FEN%5C%22%20%5C%22http%3A%2F%2Fwww.w3.org%2FTR%2Fxhtml1%2FDTD%2Fxhtml1-transitional.dtd%5C%22%3E%3Chtml%20xmlns%3D%5C%22http%3A%2F%2Fwww.w3.org%2F1999%2Fxhtml%5C%22%20xml%3Alang%3D%5C%22en%5C%22%20lang%3D%5C%22en%5C%22%3E...%3C%2Fhtml%3E";
	public static void main(String[] args){
		
	}
 
}