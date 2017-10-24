package com.hkd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkd.entity.Content;
import com.hkd.entity.Result;
import com.hkd.entity.User;
import com.hkd.service.BackConfigParamsService;
import com.hkd.service.BorrowOrderService;
import com.hkd.service.ContentService;
import com.hkd.service.RedisService;
import com.hkd.service.UserLoginService;
import com.hkd.service.UserService;
import com.hkd.util.ResultUtils;
import com.hkd.util.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.hkd.entity.BorrowOrder.borrowStatusMap_shenheFail;
/**
 * 实现APP中的“我的”功能
 * @author Administrator
 *
 */
@Controller
@Api(value = "APP_‘我的’功能模块",description = "APP_‘我的’功能模块")
public class UserLoginController {
	
	private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private RedisService redisService;

	@Autowired 
	private UserService userService;
	
	@Autowired
	public ContentService contentService;
	
	@Autowired
	public BorrowOrderService borrowOrderService;
	
	@Autowired
	public BackConfigParamsService backConfigParamsService;
	
	@Autowired
	private UserLoginService userLoginService;

	
	/**
	 * 获取个人信息
	 * @param request
	 * @return
	 */
	@GetMapping(value = "credit-card/get-person-info")
    @ApiOperation(value = "查询用户个人信息@liuxia", notes = "查询用户个人信息")
	@ResponseBody
	public Result<Map<String,Object>> findPersonInfo(@ApiParam(value = "访问设备ID",required = true)@RequestParam String deviceId,
													@ApiParam(value = "手机号",required = true)@RequestParam String mobilePhone,
													HttpServletRequest request) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	    String code = "-1";
		String msg = "获取失败";
		User user = null;
	    try {
	    	code = "0";
			msg = "成功获取身份信息";
			
			String userId = redisService.getLoginUser(deviceId, mobilePhone);
			
	    	//根据deviceId, mobilePhone参数查询不到用户信息，则显示请先登录信息
	    	if (userId != null)  {
				user = userService.searchByUserid(userId);
				resultMap = userLoginService.getPersonInfo(user);
	    	} else {
	    		code = "-2";
				msg = "请先登录";
	    	}
	    } catch (Exception e) {
	    	
	    	e.printStackTrace();
	    	
	    	code = "500";
	    	msg = "系统异常";
	    } finally {
	    	dataMap.put("item", resultMap);
	    	
	    	return ResultUtils.result(code,msg,dataMap); 
	    }
	}
	
	/***
	 * 用户界面
	 */
	@GetMapping("credit-user/get-info")
	@ApiOperation(value = "查询用户界面信息@liuxia", notes = "查询用户界面信息")
	@ResponseBody
	public Result<Map<String, Map<String,Object>>> userInfoIndex(@ApiParam(value = "访问设备ID",required = true)@RequestParam String deviceId,
			                                                         @ApiParam(value = "手机号",required = true) @RequestParam String mobilePhone,
			                                                         String appName, HttpServletRequest request) {
		HashMap<String,Map<String,Object>> dataMap = new HashMap<String, Map<String,Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = "-1";
		String msg = "获取失败";
		User user = null;
		try {
			String userId = redisService.getLoginUser(deviceId, mobilePhone);
			
	    	//根据deviceId, telephone参数查询不到用户信息，则显示请先登录信息
	    	if (userId != null)  {
				user = userService.searchByUserid(userId);
				//获取分享链接logo地址
				String shareLogoUrl =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
				//获取分享地址
				String shareUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + "act/light-loan-xjx?invite_code=";
				//已绑定跳转银行卡页面路径
//				String bankUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
				String bankUrl = "/";

				resultMap = userLoginService.getUserInfo(user,shareLogoUrl,shareUrl,bankUrl,mobilePhone,appName);
				
				code = "0";
				msg = "获取成功";
			} else {
				code = Status.LOGIN.getName();
				msg = Status.LOGIN.getValue();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			code = "500";
			msg = "系统异常";
		} finally {
 			dataMap.put("item", resultMap);
 			
 			return ResultUtils.result(code,msg,dataMap); 
		}
	}
	
	/**
	 * 跳转到我的邀请码页面
	 * @return
	 */
	@GetMapping(value = "/page/detail")
    @ApiOperation(value = "跳转到我的邀请码页面@liuxia", notes = "跳转到我的邀请码页面")
	public String userInvitation(HttpServletRequest request, HttpServletResponse response) {
		return "content/invitation";
	}
	
	
	/**
	 * 跳转到消息中心页面
	 * @return
	 */
	@GetMapping(value = "/content/activity")
	@ApiOperation(value = "跳转到消息中心页面@liuxia", notes = "跳转到消息中心页面")
	public String gotoInfoCenter(){
		return "content/activity";
	}
	
	/**
	 * 跳转到帮助中心页面
	 * @return
	 */
	@GetMapping(value = "/help")
	@ApiOperation(value = "跳转到帮助中心页面@liuxia", notes = "跳转到帮助中心页面")
	public String gotoHelp(String deviceId, String mobilePhone,Map<String,Object> map){
		User logUser = null;
		
		String userId = redisService.getLoginUser(deviceId, mobilePhone);
		if (userId != null) {
    		logUser = userService.searchByUserid(userId);
    	}
		
		if (logUser == null) {
			map.put("tel", userLoginService.getHelpTel());
		}
		
		return "content/help";
	}
	
	/**
	 * 查询帮助问题列表
	 * @param request
	 */
	@PostMapping("help/findTheHotIssueList")
	@ApiOperation(value = "跳转到帮助页面时，自动加载帮助问题列表@liuxia", notes = "在帮助中心页面中显示帮助问题列表")
	@ResponseBody
	public Result<PageInfo<Map<String,Object>>> findTheHotIssueList(String pageNum,String pageSize,String contentTitle,String contentTxt){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("channelType","CHANNEL_QUESTION");
		params.put("contentTitle", contentTitle);
		params.put("contentTxt", contentTxt);
		
		PageInfo<Map<String,Object>> pageInfo = contentService.findHelps(params,pageNum,pageSize);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 查询活动列表
	 * @param request
	 */
	@PostMapping("help/findactivity")
	@ApiOperation(value = "跳转到消息中心页面时，自动加载活动列表@liuxia", notes = "在消息中心页面中显示活动列表")
	@ResponseBody
	public Result<PageInfo<Map<String,Object>>> findactivity(String pageNum,String pageSize,String contentTitle,String contentTxt){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("channelType","CHANNEL_QUESTION");
		params.put("contentTitle", contentTitle);
		params.put("contentTxt", contentTxt);
		
		PageInfo<Map<String,Object>> pageInfo = contentService.findActivities(params,pageNum,pageSize);
		
		return ResultUtils.success(pageInfo);
	}
	
	/*
	 * 公告详情
	 */
	@GetMapping("content/details")
	@ApiOperation(value = "点击消息中心页面中公告列表中的公告，显示公告详情@liuxia", notes = "在消息中心页面中显示公告详情信息")
	public String details(HttpServletRequest request,Map<String,Object> map){
		//从消息中心页面传ID值
		String id=request.getParameter("id")==null?"":request.getParameter("id");
		if (id!=null && id!="") {
			Content content=contentService.findById(id);
			map.put("content", content);
		}
		return "content/contentdetails";
	}
	
}
