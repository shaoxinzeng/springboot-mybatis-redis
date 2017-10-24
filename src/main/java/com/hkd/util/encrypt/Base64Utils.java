package com.hkd.util.encrypt;

import java.io.UnsupportedEncodingException;

import org.apache.tomcat.util.codec.binary.Base64;

import com.hkd.util.Constant;


public class Base64Utils {

	/**
	 * 
	 * 创建日期2014-4-24上午10:12:38 修改日期 作者： TODO 使用Base64加密算法加密字符串 return
	 */
	public static String encodeStr(String plainText) {
		try {
			return new String(Base64.encodeBase64(
					plainText.getBytes(Constant.UTF8), true));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * 创建日期2014-4-24上午10:15:11 修改日期 作者： TODO 使用Base64加密 return
	 */
	public static String decodeStr(String encodeStr) {
		try {
			return new String(Base64.decodeBase64(encodeStr
					.getBytes(Constant.UTF8)));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(Base64Utils.encodeStr("7"));
	}
}
