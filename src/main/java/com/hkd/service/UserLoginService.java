package com.hkd.service;

import java.util.Map;

import com.hkd.entity.User;

/**
 * App中“我的”功能模块中获取用户信息
 * @author Administrator
 *
 */
public interface UserLoginService {
	/**
	 * 获取客服电话
	 * @return
	 */
	public String getHelpTel();
	
	/**
	 * 获取APP的配置参数
	 * @param appName
	 * @param param
	 * @return
	 */
	public String getAppConfig(String appName, String param);
	
	/**
	 * 获取个人用户信息
	 * @param user
	 * @return
	 */
	public Map<String,Object> getPersonInfo(User user);
	
	/**
	 * 获取用户信息
	 * @param user
	 * @param shareLogoUrl	分享链接logo地址
	 * @param shareUrl	分享链接地址
	 * @param bankUrl	已绑定跳转银行卡页面路径
	 * @param mobilePhone	手机号
	 * @param appName	app名称
	 * @return
	 */
	public Map<String,Object> getUserInfo (User user,String shareLogoUrl,String shareUrl,String bankUrl,String mobilePhone,String appName);
}
