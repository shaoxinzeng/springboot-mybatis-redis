package com.hkd.entity;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	/** 系统参数中返回list时使用的key的后缀 */
	public static final String SYS_CONFIG_LIST = "_LIST";

	/** 状态 */
	public static final String STATUS_VALID = "1";// 有效
	public static final Integer STATUS_INT_VALID = 1; //银行认证完成
	public static final int INDEX_LIMIT = 20;// 有效

	/** index */
	public static final String CARD_VERIFY_STEP = "认证";

	public static final String CACHE_INDEX_KEY = "CASH_MAN_INDEX";// 默认缓存KEY
	public static final String CACHE_INDEX_INFO_KEY = "INDEX_INFO_";// 动态缓存KEY

	public static final int FOR_BASE = 10000;// 默认基数
	public static final int AMOUNT_MAX = 1000000;// 默认最大金额
	public static final int AMOUNT_MIN = 50000;// 默认最小金额

	public static final double RATE_MIN = 0.098;// 默认3期-1000利率
	public static final double RATE_MAX = 0.15;// 默认6期 1500利率
	
	public static final double CREDIT_RATE_MIN = 0.05;//默认7天-信审查询费
	public static final double CREDIT_RATE_MAX = 0.08;//默认14天-信审查询费
	
	public static final double ACCOUNT_MANAGE_RATE_MIN = 0.0438;//默认7天-账户管理费
	public static final double ACCOUNT_MANAGE_RATE_MAX = 0.063;//默认14天-账户管理费

	public static final double ACCRUAL_RATE_MIN = 0.0042;//默认7天-利息
	public static final double ACCRUAL_RATE_MAX = 0.007;//默认14天-利息
	
	public static final int DEFAULT_DAYS = 27;// 默认下次再申请的天数

	public static final int LOAN_DAYS_14 = 14;// 借款14天
	public static final int LOAN_DAYS_7 = 7;// 借款7天

	public static final String RENEWAL_MAX = "RENEWAL_MAX";// 最大申请续期次数
	public static final String COLLECTION_PATH = "COLLECTION_PATH";// 催收地址
	public static final String LUCKY_DRAW = "LUCKY_DRAW";// 抽奖接口地址

	public static final double XJX_RATE = 0.014;

	public static final String BUTTON_MSG = "朕知道了";
	/**魔蝎异步回调taskId的前缀(成功)*/
	public static final String MX_CALL_BACK_SUC = "MX_CALL_BACK_SUC_";
	/**魔蝎异步回调taskId的前缀(失败)*/
	public static final String MX_CALL_BACK_FAIL = "MX_CALL_BACK_FAIL_";

	public static final String XJX_AWARD_MONEY_URL = "http://www.xianjinxia.com:8086/xjx-platform/a/jsaward/awardCenterWeb/drawAwardIndexList";
	public static final Map<String, String> CARD_PROVINCE = new HashMap<String, String>();
	static {
		CARD_PROVINCE.put("11", "北京市");
		CARD_PROVINCE.put("12", "天津市");
		CARD_PROVINCE.put("13", "河北省");
		CARD_PROVINCE.put("14", "山西省");
		CARD_PROVINCE.put("15", "内蒙古自治区");
		CARD_PROVINCE.put("21", "辽宁省");
		CARD_PROVINCE.put("22", "吉林省");
		CARD_PROVINCE.put("23", "黑龙江省");
		CARD_PROVINCE.put("31", "上海市");
		CARD_PROVINCE.put("32", "江苏省");
		CARD_PROVINCE.put("33", "浙江省");
		CARD_PROVINCE.put("34", "安徽省");
		CARD_PROVINCE.put("35", "福建省");
		CARD_PROVINCE.put("36", "江西省");
		CARD_PROVINCE.put("37", "山东省");
		CARD_PROVINCE.put("41", "河南省");
		CARD_PROVINCE.put("42", "湖北省");
		CARD_PROVINCE.put("43", "湖南省");
		CARD_PROVINCE.put("44", "广东省");
		CARD_PROVINCE.put("45", "广西壮族自治区");
		CARD_PROVINCE.put("46", "海南省");
		CARD_PROVINCE.put("50", "重庆市");
		CARD_PROVINCE.put("51", "四川省");
		CARD_PROVINCE.put("52", "贵州省");
		CARD_PROVINCE.put("53", "云南省");
		CARD_PROVINCE.put("54", "西藏自治区");
		CARD_PROVINCE.put("61", "陕西省");
		CARD_PROVINCE.put("62", "甘肃省");
		CARD_PROVINCE.put("63", "青海省");
		CARD_PROVINCE.put("64", "宁夏回族自治区");
		CARD_PROVINCE.put("65", "新疆维吾尔自治区");
		CARD_PROVINCE.put("71", "台湾省");
		CARD_PROVINCE.put("81", "香港特别行政区");
		CARD_PROVINCE.put("91", "澳门特别行政区");
	}

	/** 同盾请求 */
	public static final String TD = "TD";
	/** 个人贷前审核,生成报告 */
	public static final String TD_PRELOAN_APPLY = "TD_PRELOAN_APPLY";
	/** 个人贷前查询 ，查询报告 */
	public static final String TD_PRELOAN_REPORT = "TD_PRELOAN_REPORT";
	/** 同盾手机号命高低风险关注名单的标识 */
	public static final String TD_PHONE_BLACK = "手机号命中高风险关注名单";
	/** 同盾身份证命高低风险关注名单的标识 */
	public static final String TD_CARD_BLACK = "身份证命中高风险关注名单";
	/** 同盾1个月内申请人在多个平台申请借款 的标识 */
	public static final String TD_MONTH1_BLACK = "1个月内申请人在多个平台申请借款";
	/** 同盾7天内申请人在多个平台申请借款 的标识 */
	public static final String TD_DAY7_BLACK = "7天内申请人在多个平台申请借款";
	/** 同盾1个月内身份证使用过多设备申请 的标识 */
	public static final String TD_MONTH1_CARD_NUM_DEVICE_BORROW = "1个月内身份证使用过多设备申请";
	/** 同盾1个月内设备使用多身份证或手机号申请的标识 */
	public static final String TD_DAY7_DEVICE_CARD_OR_PHONE_BORROW = "7天内设备使用过多的身份证或手机号进行申请";
	/** 同盾7天内身份证关联设备数的标识 */
	public static final String TD_DAY7_CARD_DEVICE = "7天内身份证关联设备数";
	/** 同盾3个月内申请信息关联多个身份证的标识 */
	public static final String TD_MONTH3_APPLY_CARD = "3个月内申请信息关联多个身份证";
	/** 同盾3个月内身份证关联多个申请信息的标识 */
	public static final String TD_MONTH3_CARD_APPLY = "3个月内身份证关联多个申请信息";

	/** 白骑士请求 */
	public static final String BQS = "BQS";
	/** 白骑士风险名单 */
	public static final String BQS_RISK = "BQS_RISK";

}
