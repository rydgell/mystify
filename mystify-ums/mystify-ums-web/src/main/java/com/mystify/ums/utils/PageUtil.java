package com.mystify.ums.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PageUtil{
	public static String decoder(String target , String code){
		try {
			return URLDecoder.decode(target,code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String decodeStr(String target , String coder){
		try {
			return new String(target.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}