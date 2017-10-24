package com.hkd.util.encrypt;

import com.hkd.entity.Constant;
import com.hkd.entity.RiskCreditUser;
import com.hkd.util.GsonUtil;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Map;

public class AESUtil {
	public final static String KEY = "!@#$%^&*(";

	/**
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			if (StringUtils.isBlank(password)) {
				password = KEY;
			}
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return ByteUtil.byteArr2HexStr(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String content, String password) {
		try {
			if (StringUtils.isBlank(password)) {
				password = KEY;
			}
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");// 修复linux系统报错问题
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(ByteUtil.hexStr2ByteArr(content));
			return new String(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查openId
	 */
	public static void getOpenId() {
		try {
			String name = "范银川";
			String cardNum = "411324199009044030";
			String userId = "7";
			String value = "userName=" + name + "&cardNum=" + cardNum
					+ "&userId=" + userId;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查openId
	 */
	public static void getURL() {
		try {
			String name = "范银川";
			String cardNum = "411324199009044030";
			String userId = "7";
			String value = "userName=" + name + "&cardNum=" + cardNum
					+ "&userId=" + userId;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查openId
	 */
	public static void getScore() {
		try {
			String openId = "268816231939676957957903450";
			String userId = "7";
			String value = "userId=" + userId + "&openId=" + openId;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成同盾贷前报告
	 */
	public static void getTd() {
		try {
			// 893939741@qq.com
			String userId = "7";
			String userName = "皮晴晴";
			String userPhone = "13333333333";
			String cardNum = "370404199006301915";
			// String userId = "7";
			// String userName = "陈凯炜";
			// String userPhone = "13918201587";
			// String cardNum = "330381198511281455";
			String value = "userId=" + userId + "&userName=" + userName
					+ "&userPhone=" + userPhone + "&cardNum=" + cardNum;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得同盾贷前报告
	 */
	public static void getTdReport() {
		try {
			String userId = "7";
			String reportId = "ER2016121312594913325789";
			String value = "userId=" + userId + "&reportId=" + reportId;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			Map<String,Object> serviceResult = GsonUtil.GsonToMaps(result);
			JSONObject jsonObject = new JSONObject(serviceResult.get("msg"));
			if (jsonObject.getBool("success")) {
				BigDecimal tdScore = new BigDecimal(jsonObject
						.get("final_score").toString());
				JSONArray jsonArray = jsonObject.getJSONArray("risk_items");
				Integer tdPhoneBlack = 0;
				Integer tdCardNumBlack = 0;
				Integer tdMonth1Borrow = 0;
				Integer tdDay7Borrow = 0;
				Integer tdMonth3CardApply = 0;
				Integer tdMonth3ApplyCard = 0;
				Integer tdMonth1CardNumDeviceBorrow = 0;
				Integer tdDay7DeviceCardOrPhoneBorrow = 0;
				Integer tdDay7CardDevice = 0;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String itemName = jsonObject2.getStr("item_name");
					JSONObject jsonObject3 = jsonObject2
							.getJSONObject("item_detail");
					if (Constant.TD_PHONE_BLACK.equals(itemName)) {
						tdPhoneBlack = jsonObject3.getJSONArray(
								"namelist_hit_details").size();
					} else if (Constant.TD_CARD_BLACK.equals(itemName)) {
						tdCardNumBlack = jsonObject3.getJSONArray(
								"namelist_hit_details").size();
					} else if (Constant.TD_MONTH1_BLACK.equals(itemName)) {
						tdMonth1Borrow = jsonObject3.getInt("platform_count");
					} else if (Constant.TD_DAY7_BLACK.equals(itemName)) {
						tdDay7Borrow = jsonObject3.getInt("platform_count");
					} else {
						if (jsonObject3.containsKey("frequency_detail_list")) {
							JSONArray jsonArray2 = jsonObject3
									.getJSONArray("frequency_detail_list");
							if (jsonArray2 != null && jsonArray2.size() > 0) {
								for (int j = 0; j < jsonArray2.size(); j++) {
									JSONObject jsonObject4 = jsonArray2
											.getJSONObject(j);
									String detail = jsonObject4
											.getStr("detail");
									int index = detail.indexOf("：") + 1;
									if (index >= 0) {
										int num = Integer.valueOf(detail
												.substring(index));
										if (Constant.TD_MONTH3_CARD_APPLY
												.equals(itemName)) {
											tdMonth3CardApply += num;
										} else if (Constant.TD_MONTH3_APPLY_CARD
												.equals(itemName)) {
											tdMonth3ApplyCard += num;
										} else if (Constant.TD_MONTH1_CARD_NUM_DEVICE_BORROW
												.equals(itemName)) {
											tdMonth1CardNumDeviceBorrow += num;
										} else if (Constant.TD_DAY7_DEVICE_CARD_OR_PHONE_BORROW
												.equals(itemName)) {
											tdDay7DeviceCardOrPhoneBorrow += num;
										} else if (Constant.TD_DAY7_CARD_DEVICE
												.equals(itemName)) {
											tdDay7CardDevice += num;
										}
									}
								}
							}
						}
					}
				}
				RiskCreditUser riskCreditUser = new RiskCreditUser(Integer
						.valueOf(userId), tdScore, tdPhoneBlack,
						tdCardNumBlack, tdMonth1Borrow, tdDay7Borrow,
						tdMonth1CardNumDeviceBorrow,
						tdDay7DeviceCardOrPhoneBorrow, tdDay7CardDevice,
						tdMonth3ApplyCard, tdMonth3CardApply, null);
			}

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 白骑士白名单
	 */
	public static void getBqs() {
		try {
			String userId = "7";
			String userName = "陈凯炜";
			String userPhone = "13918201587";
			String cardNum = "330381198511281455";
			// String userName = "范银川";
			// String userPhone = "18917583863";
			// String cardNum = "411324199009044030";
			String value = "userId=" + userId + "&userName=" + userName
					+ "&userPhone=" + userPhone + "&cardNum=" + cardNum;
			System.out.println(value);
			String result = HttpUtil.get("http://180.173.0.188:8999/back/risk/test?" + value);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void main(String[] args) {
		// getOpenId();
		// getURL();
		// getScore();
		// getTd();
		// getTdReport();
		// getBqs();
		// getJy();
		// getMg();
		// getJxl();
//		getYx();
	}
}
