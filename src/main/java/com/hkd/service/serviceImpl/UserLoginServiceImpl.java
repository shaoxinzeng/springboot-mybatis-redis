package com.hkd.service.serviceImpl;

import static com.hkd.entity.BorrowOrder.borrowStatusMap_shenheFail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hkd.entity.BackConfigParams;
import com.hkd.entity.BorrowOrder;
import com.hkd.entity.User;
import com.hkd.entity.UserCardInfo;
import com.hkd.service.BackConfigParamsService;
import com.hkd.service.BorrowOrderService;
import com.hkd.service.UserLoginService;
import com.hkd.service.UserService;
import com.hkd.util.Constant;
import com.hkd.util.encrypt.Base64Utils;

/**
 * “我的”功能模块的Service层
 * @author lx
 *
 */
@Service
public class UserLoginServiceImpl implements UserLoginService{
	
	private static Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@Value("${domainOfBucket}")
	private String domainOfBucket;	//脸部识别和身份证验证变量
	
	@Autowired
	private BackConfigParamsService backConfigParamsService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	public BorrowOrderService borrowOrderService;

	/**
	 * 获取客服电话
	 */
	@Override
	public String getHelpTel() {
		HashMap<String,Object> intervalParams = new HashMap<String,Object>();
		intervalParams.put("sysType", BackConfigParams.WEBSITE);
		intervalParams.put("syskey", "service_phone");
		
		List<BackConfigParams> intervalList = backConfigParamsService.findParams(intervalParams);
		BackConfigParams backConfigParams = intervalList.get(0);
		
		logger.info("客服电话：" + backConfigParams.getSysValue());
		
		return backConfigParams.getSysValue();
	}

	/**
     * 获取APP的配置参数
     * @param appName
     * @param param
     * @return
     */
	@Override
	public String getAppConfig(String appName, String param) {
		if(StringUtils.isBlank(appName)){
            appName = "hkd";
        }
        BackConfigParams backConfigParams = backConfigParamsService.findConfigValue(param+"_"+appName);
        if(backConfigParams!=null) {
        	return backConfigParams.getSysValue();
        }
        return "";
	}

	/**
	 * 获取个人用户信息
	 */
	@Override
	public Map<String, Object> getPersonInfo(User user) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<Map<String, String>> degrees_all = new ArrayList<Map<String, String>>();
		List<Map<String, String>> marriage_all = new ArrayList<Map<String, String>>();
		List<Map<String, String>> live_time_type_all = new ArrayList<Map<String, String>>();
		
		//字段是否为空校验
		String name = StringUtils.isNotBlank(user.getRealname()) ? user.getRealname() : "";
		String id_number = StringUtils.isNotBlank(user.getIdNumber()) ? user.getIdNumber() : "";
		String address = StringUtils.isNotBlank(user.getPresentAddress()) ? user.getPresentAddress() : "";
		String live_period = StringUtils.isNotBlank(user.getPresentPeriod()) ? user.getPresentPeriod() : "";
		String address_distinct = StringUtils.isNotBlank(user.getPresentAddressDistinct()) ? user.getPresentAddressDistinct() : "";
		String longitude = StringUtils.isNotBlank(user.getPresentLongitude()) ? user.getPresentLongitude() : "";
		String latitude = StringUtils.isNotBlank(user.getPresentLatitude()) ? user.getPresentLatitude() : "";
		String face_recognition_picture =  "";
		String id_number_z_picture = "";
		String id_number_f_picture = "";
		
		//脸部识别图片信息和身份证图片信息
		if(StringUtils.isNotBlank(user.getHeadPortrait())){
			face_recognition_picture = Constant.HTTP + domainOfBucket + "/" + user.getHeadPortrait();
		}
		if(StringUtils.isNotBlank(user.getIdcardImgZ())){
			id_number_z_picture = Constant.HTTP + domainOfBucket + "/" + user.getIdcardImgZ();
		}
		if(StringUtils.isNotBlank(user.getIdcardImgF())){
			id_number_f_picture = Constant.HTTP + domainOfBucket + "/" + user.getIdcardImgF();
		}
		
		String degrees = StringUtils.isNotBlank(user.getEducation()) ? user.getEducation() : "";
		String marriage = StringUtils.isNotBlank(user.getMaritalStatus()) ? user.getMaritalStatus() : "";

		for (Entry<String, String> entry : User.EDUCATION_TYPE.entrySet()) {// 学历
			Map<String, String> map = new HashMap<String, String>();
			map.put("degrees", entry.getKey());
			map.put("name", entry.getValue());
			degrees_all.add(map);
			resultMap.put("degrees_all", degrees_all);
		}
		for (Entry<String, String> entry : User.MARRIAGE_STATUS.entrySet()) {// 婚姻状况
			Map<String, String> map = new HashMap<String, String>();
			map.put("marriage", entry.getKey());
			map.put("name", entry.getValue());
			marriage_all.add(map);
			resultMap.put("marriage_all", marriage_all);
		}
		for (Entry<String, String> entry : User.RESIDENCE_DATE.entrySet()) {// 居住时长
			Map<String, String> map = new HashMap<String, String>();
			map.put("live_time_type", entry.getKey());
			map.put("name", entry.getValue());
			live_time_type_all.add(map);
			resultMap.put("live_time_type_all", live_time_type_all);
		}
		resultMap.put("id", user.getId());// ID
		resultMap.put("name", name);// 真实姓名
		resultMap.put("id_number", id_number);// 身份证号码
		resultMap.put("degrees", degrees);// 学历
		resultMap.put("marriage", marriage);// 婚姻状况
		resultMap.put("address", address);// 居住地址
		resultMap.put("live_period", live_period);// 居住时长
		resultMap.put("address_distinct", address_distinct);// 居住详细地址
		resultMap.put("longitude", longitude);// 纬度
		resultMap.put("latitude", latitude);// 经度
		resultMap.put("real_verify_status", user.getRealnameStatus());// 实名认证状态
		resultMap.put("face_recognition_picture", face_recognition_picture);// 头像
		resultMap.put("id_number_z_picture", id_number_z_picture);// 身份证正面
		resultMap.put("id_number_f_picture", id_number_f_picture);// 身份证反面
		
		return resultMap;
	}

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
	public Map<String,Object> getUserInfo (User user,String shareLogoUrl,String shareUrl,String bankUrl,String mobilePhone,String appName) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> verifyInfoMap = new HashMap<String, Object>();
		
		// 信用额度
		resultMap.put("userId", user.getId());// 用户ID
		String phone = user.getUserName() == null ? "" : user.getUserName();
		if (phone.length() > 4) {
			phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
		}
		resultMap.put("phone", phone);// 手机号码
		String inviteCode = Base64Utils.encodeStr(user.getId()).trim();
		String invite = inviteCode.replaceAll("=", "");// 替换特殊符号
		resultMap.put("invite_code", invite);// 生成邀请码
		String realNameStatus = user.getRealnameStatus();
		verifyInfoMap.put("real_verify_status", realNameStatus);// 用户实名认证状态

		String payPasswordStatus = "0";
		if (user.getPayPassword() != null) {
			payPasswordStatus = "1";
		}
		verifyInfoMap.put("real_pay_pwd_status", payPasswordStatus);// 用户交易密码状态
		String bankStatus = "0";

		resultMap.put("userId", user.getId());// 用户

		UserCardInfo cardInfo = userService.findUserBankCard(Integer.parseInt(user.getId()));
		HashMap<String, String> cardMap = new HashMap<String, String>();
		String card_amount = user.getAmountMax();
		String card_unused_amount = user.getAmountAvailable();
		cardMap.put("card_amount", card_amount);// 总额度
		cardMap.put("card_unused_amount", card_unused_amount);// 剩余额度
		String cardName = "";
		String cardEndNO = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if (cardInfo != null) {
			// cardMap.put("card_no", cardInfo.getCard_no());//银行卡
			String card = cardInfo.getCard_no();
			cardEndNO = card.substring(card.length() - 4, card.length());// 银行卡号码
			cardName = cardInfo.getBankName();// 银行名称
			bankStatus = "1";
		}
		map.put("card_no_end", cardEndNO);
		map.put("bank_name", cardName);
		verifyInfoMap.put("real_bind_bank_card_status", bankStatus);// 用户银行卡状态
		
		//获取用户最近借款状态 代替风控
		// 如果最近借款被拒绝, 那么就显示问号, 跳转去其他的app
		// 0 不显示 1显示
		cardMap.put("risk_status","0");
		BorrowOrder bo = borrowOrderService.selectBorrowOrderNowUseId(Integer.valueOf(user.getId()));

		if(bo!=null){
			logger.info("用户最新借款状态"+ bo.getStatus());
			//借款审核被拒绝 添加全局控制开关 1 打开 0 关闭
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("sysType","SYS_NOCACHE");
			params.put("syskey","SYJ_SWITCH");
			List<BackConfigParams> list = backConfigParamsService.findParams(params);
			logger.info("引流开关 数量"+ list.size());
			String offon = "1";
			if(list.size()==1){
				offon = list.get(0).getSysValue();
			}
			//借款审核被拒绝
			if(borrowStatusMap_shenheFail.containsKey(bo.getStatus())
					&&"1".equals(offon)){
				cardMap.put("risk_status","1");
			}
		}
		//获取页面引流的地址
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("sysType","SYS_NOCACHE");
		params.put("syskey","drainage_url");
		List<BackConfigParams> list = backConfigParamsService.findParams(params);
		cardMap.put("drainage_url","http://mobile.souyijie.com/recommend");
		if(list.size()==1){
			cardMap.put("drainage_url",list.get(0).getSysValue());
		}

		resultMap.put("credit_info", cardMap);// 额度信息
		resultMap.put("card_info", map);// 银行卡信息

		if(null != mobilePhone){
			resultMap.put("card_url", bankUrl + "lianlianBindCard/credit-card/firstUserBank");// 银行卡页面
		}
		else{
			resultMap.put("card_url", bankUrl + "credit-card/card-list");// 银行卡页面
		}
		resultMap.put("verify_info", verifyInfoMap);// 认证状态

		String path = getAppConfig(appName, "XJX_LOGO");  //分享logo地址
		
		// 分享链接
		resultMap.put("share_body", "1分钟认证，20分钟到账，无抵押，纯信用贷。时下最流行的移动贷款APP。国内首批利用大数据、人工智能实现风控审批的信贷服务平台。");
		resultMap.put("share_logo",shareLogoUrl + path);
		resultMap.put("share_title",getAppConfig(appName, "APP_TITLE"));
		String user_from="0";
		if(StringUtils.isNotBlank(user.getUserFrom()) && !"null".equals(user.getUserFrom())){
			user_from=user.getUserFrom();
		}
		String share_url = shareUrl + invite + "&user_from=" + user_from;
		if(StringUtils.isNotBlank(appName)){
			share_url +="&appName="+ appName;
		}
		resultMap.put("share_url", share_url);


		// 动态客服数据
		Map<String, Object> serviceMap = new HashMap<String, Object>();
		
		HashMap<String,Object> intervalParams = new HashMap<String,Object>();
		intervalParams.put("sysType", BackConfigParams.WEBSITE);
		List<BackConfigParams> intervalList = backConfigParamsService.findParams(intervalParams);
		for (BackConfigParams backConfigParams : intervalList) {
			if("qq_group".equals(backConfigParams.getSysKey())) {
				serviceMap.put("qq_group", backConfigParams.getSysValue());
			}
			if("service_phone".equals(backConfigParams.getSysKey())) {
				serviceMap.put("service_phone", backConfigParams.getSysValue());
			}
			if("peacetime".equals(backConfigParams.getSysKey())) {
				serviceMap.put("peacetime", backConfigParams.getSysValue());
			}
			if("holiday".equals(backConfigParams.getSysKey())) {
				serviceMap.put("holiday", backConfigParams.getSysValue());
			}
		}
		
		HashMap<String,Object> qqParams = new HashMap<String,Object>();
		qqParams.put("sysType", BackConfigParams.WEBSITE);
		qqParams.put("syskey", "services_qq");
		List<BackConfigParams> servicesQQList =  backConfigParamsService.findParams(qqParams);
		List<String> servicesQQ = new ArrayList<String>();
		
		for (BackConfigParams backConfigParams : servicesQQList) {
			servicesQQ.add(backConfigParams.getSysValue());
		}
		serviceMap.put("services_qq", servicesQQ);

		resultMap.put("service", serviceMap);
		
		return resultMap;
	}

}
