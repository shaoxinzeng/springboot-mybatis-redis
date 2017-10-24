package com.hkd.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowOrder {
	private Integer id;

	private Integer userId;

	private String outTradeNo;

	private Integer orderType;
	private String orderTypeName;

	private Integer moneyAmount;

	private Integer apr;

	private Integer loanInterests;

	private Integer intoMoney;

	private Integer loanMethod;

	private Integer loanTerm;

	private String operatorName;

	private Date createdAt;

	private Date updatedAt;

	private Date orderTime;

	private Date loanTime;

	private Date loanEndTime;

	private Integer lateFeeApr;

	private Integer receiveCardId;

	private Integer debitCardId;

	private Integer capitalType;
	private String  serialNo;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getIdNumber() {
		return IdNumber;
	}

	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}

	private String reasonRemark;

	private Integer creditLv;

	private Byte isHitRiskRule;

	private Byte autoRiskCheckStatus;
	private Integer customerType;

	private String IdNumber;

	private Integer bankIscmb;  //对应银行是否是招行；1：是；2：否
	private String userPhone;
	private String realname;
	private String cardNo;
	private String bankNumber;
	private String paystatus;
	/**初始状态**/
	public static final String SUB_PAY_CSZT ="0";
	/**支付成功**/
	public static final String SUB_PAY_SUCC ="0000";
	
	/**提交异常**/
	public static final String SUB_ERROR ="500";
	/** 提交成功，需要查询结果  **/
	public static final String SUB_SUBMIT="5101";
	/** 支付失败 **/
	public static final String SUB_PAY_FAIL ="501";
	/** 提交失败，需要重新提交**/
	public static final String SUB_SUB_FAIL ="502";
	/** 报文解析失败，需要查询结果**/
//	public static final String PAY_RETURN_FAIL ="504";
	

	/**打款异常 **/
	public static final String PAY_ERROR ="600";
	/** 支付失败**/
	public static final String PAY_PAY_FAIL ="601";
	/**支付已被银行受理，需要再次查询 **/
	public static final String PAY_SUBMIT ="611";
	
 
 /**
  * 提交打款处理返回结果：5开头的接口
  *  0000支付成功
  * 5101:提交成功，需要查询结果  
  * 501: 支付失败 ,
  * 
  * 502:提交失败，需要重新提交
  * 504:报文解析失败，需要查询结果
  * 502提交失败，需要重新提交
  * 
  * 601 支付失败
  * 611 支付已被银行受理，需要再次查询
  * 
  * 
  */
       
 public static final Map<Integer, String> paystatusMap = new HashMap<Integer, String>();
 
 
 private String  payRemark; 
	private String yurref;

	private Integer status;
	private String statusName;
	private String remark;
//	private User user;

	private String verifyTrialUser; // 初审人
	private Date verifyTrialTime;// 初审时间
	private String verifyTrialRemark;// 初审意见

	private String verifyReviewUser;// 复审人
	private Date verifyReviewTime;// 复审时间
	private String verifyReviewRemark;// 复审意见
	
	private String verifyLoanUser;// 放款审核人
	private Date verifyLoanTime;// 放款审核时间
	private String verifyLoanRemark;// 放款意见

	public static final Map<Integer, String> loanMethed = new HashMap<Integer, String>();

	public static final Map<Integer, String> orderTypes = new HashMap<Integer, String>();

 
   /**机审或人工审核借款信息的标识*/
	public static final String REVIEW_BORROW = "REVIEW_BORROW_";
	/**
	 * 有效时间，单位秒
	 */
	public static final int REVIEW_BORROW_SECOND = 10;
	static {
		orderTypes.put(1, "现金侠");
		orderTypes.put(0, "其它平台");

		loanMethed.put(0, "天");
		loanMethed.put(1, "月");
		loanMethed.put(2, "年");
	}
	// 状态：:、:、:；
	/**
	 * 初审通过
	 */
	public static final Integer STATUS_CSTG = 1;
	/**
	 * 待初审(待机审)
	 */
	public static final Integer STATUS_DCS = 0;
//	/**
//	 * 机审拒绝
//	 */
//	public static final Integer STATUS_JSJJ = 10001;
	/** 初审驳回
	 */
	public static final Integer STATUS_CSBH = -3;
	/**复审驳回 
	 */
	public static final Integer STATUS_FSBH = -4;
	/***复审通过,待放款 
	 */
	public static final Integer STATUS_FSTG = 20;
	/** 放款驳回
	 */
	public static final Integer STATUS_FKBH = -5;
	/** 放款中
	 */
	public static final Integer STATUS_FKZ = 22;
	/**
	 * 放款失败
	 */
	public static final Integer STATUS_FKSB = -10;
	/**
	 * 已放款
	 */
	public static final Integer STATUS_HKZ = 21;
	/**
	 * 部分还款
	 */
	public static final Integer STATUS_BFHK = 23;
	/**
	 * 已还款
	 */
	public static final Integer STATUS_YHK = 30;
	/**
	 * 逾期已还款
	 */
	public static final Integer STATUS_YQYHK = 34;
	
	
	/**
	 * 已逾期
	 */
	public static final Integer STATUS_YYQ = -11;
	/**
	 * 已坏账
	 */
	public static final Integer STATUS_YHZ = -20;
	/**
	 * 扣款中
	 */
	public static final Integer STATUS_KKZ = 12;
	/**
	 * 扣款失败
	 */
	public static final Integer STATUS_KKSB = -7;

	public static final Map<Integer, String> borrowStatusMap = new HashMap<Integer, String>();

	static {
		borrowStatusMap.put(STATUS_DCS, "待机审");
//		borrowStatusMap.put(STATUS_JSJJ, "机审拒绝"); 
		borrowStatusMap.put(STATUS_CSBH, "初审驳回");
		
		borrowStatusMap.put(STATUS_CSTG, "初审通过/待复审");
		borrowStatusMap.put(STATUS_FSBH, "复审驳回");
		borrowStatusMap.put(STATUS_FSTG, "复审通过/待放款审核");
		borrowStatusMap.put(STATUS_FKBH, "放款驳回");
		borrowStatusMap.put(STATUS_FKZ, "放款中");
		borrowStatusMap.put(STATUS_FKSB, "放款失败");
		borrowStatusMap.put(STATUS_HKZ, "已放款/待还款");
		borrowStatusMap.put(STATUS_BFHK, "部分还款");
		borrowStatusMap.put(STATUS_YHK, "正常已还款");
		borrowStatusMap.put(STATUS_YQYHK, "逾期已还款");
		borrowStatusMap.put(STATUS_YYQ, "已逾期");
		borrowStatusMap.put(STATUS_YHZ, "已坏账");
		// 二级状态
		borrowStatusMap.put(STATUS_KKZ, "扣款中");// +
		borrowStatusMap.put(STATUS_KKSB, "扣款失败");// +

	}

 
//前台展示
	
	
	public static final String shenhezhong="审核中";
	public static final String fangkanSucee="待还款";
	public static final String shenheFail="审核失败";
	public static final String yiyuqi="已逾期";
	public static final String finish="已还款";
	/**
	 * 前端
	 */
	public static final Map<Integer, String> borrowStatusMap_front = new HashMap<Integer, String>();

	static {
		 //审核中
		borrowStatusMap_front.put(STATUS_DCS, shenhezhong);
//		borrowStatusMap_front.put(STATUS_JSJJ, shenhezhong);
		borrowStatusMap_front.put(STATUS_CSTG, shenhezhong);
		borrowStatusMap_front.put(STATUS_FSTG, shenhezhong);
		//审核失败
		borrowStatusMap_front.put(STATUS_CSBH, shenheFail);
		borrowStatusMap_front.put(STATUS_FSBH, shenheFail);
		borrowStatusMap_front.put(STATUS_FKBH, shenheFail);
		//已还款
		borrowStatusMap_front.put(STATUS_YHK, finish);
		borrowStatusMap_front.put(STATUS_YQYHK, finish);
		
		borrowStatusMap_front.put(STATUS_HKZ,fangkanSucee);
		
		borrowStatusMap_front.put(STATUS_FKZ, "放款中");
		borrowStatusMap_front.put(STATUS_FKSB, "放款中");
		
		borrowStatusMap_front.put(STATUS_HKZ,fangkanSucee);
		borrowStatusMap_front.put(STATUS_BFHK, "部分还款");

		borrowStatusMap_front.put(STATUS_YYQ, yiyuqi);
		borrowStatusMap_front.put(STATUS_YHZ, yiyuqi);
}
	/**
	 * 审核中
	 */
	public static final Map<Integer, String> borrowStatusMap_shenhezhong = new HashMap<Integer, String>();
//	/**
//	 * 放款成功,等待还款
//	 */
//	public static final Map<Integer, String> borrowStatusMap_fangkanSucee = new HashMap<Integer, String>();
//	/**
//	 * 审核失败
//	 */
	public static final Map<Integer, String> borrowStatusMap_shenheFail = new HashMap<Integer, String>();
//	/**
//	 * 已逾期
//	 */
//	public static final Map<Integer, String> borrowStatusMap_yiyuqi = new HashMap<Integer, String>();
//	/**
//	 * 已还款
//	 */
//	public static final Map<Integer, String> borrowStatusMap_finish = new HashMap<Integer, String>();
//	
	static {
//	    //审核中
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_DCS, shenhezhong);
//		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_JSJJ, shenhezhong);
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_CSTG, shenhezhong);
		borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_FSTG, shenhezhong);
//	    // 还款中 
//		borrowStatusMap_fangkanSucee.put(BorrowOrder.STATUS_HKZ, fangkanSucee);
//		//审核失败
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_CSBH, shenheFail);
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FSBH, shenheFail);
		borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FKBH, shenheFail);
//		//已逾期
//		borrowStatusMap_yiyuqi.put(BorrowOrder.STATUS_YYQ, yiyuqi);
//		borrowStatusMap_yiyuqi.put(BorrowOrder.STATUS_YHZ, yiyuqi);
//		
//		//已还款
//		borrowStatusMap_finish.put(BorrowOrder.STATUS_YHK, finish);
//		borrowStatusMap_finish.put(BorrowOrder.STATUS_YQYHK, finish);
//		 
//		
//		
//		
	}
	
 

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public String getVerifyReviewUser() {
		return verifyReviewUser;
	}

	public String getVerifyLoanUser() {
		return verifyLoanUser;
	}

	public void setVerifyLoanUser(String verifyLoanUser) {
		this.verifyLoanUser = verifyLoanUser;
	}

	public Date getVerifyLoanTime() {
		return verifyLoanTime;
	}

	public String getYurref() {
		return yurref;
	}

	public void setYurref(String yurref) {
		this.yurref = yurref;
	}

	public void setVerifyLoanTime(Date verifyLoanTime) {
		this.verifyLoanTime = verifyLoanTime;
	}

	public String getVerifyLoanRemark() {
		return verifyLoanRemark;
	}

	public void setVerifyLoanRemark(String verifyLoanRemark) {
		this.verifyLoanRemark = verifyLoanRemark;
	}

	public void setVerifyReviewUser(String verifyReviewUser) {
		this.verifyReviewUser = verifyReviewUser;
	}

	public Date getVerifyReviewTime() {
		return verifyReviewTime;
	}

	public void setVerifyReviewTime(Date verifyReviewTime) {
		this.verifyReviewTime = verifyReviewTime;
	}

	public String getVerifyReviewRemark() {
		return verifyReviewRemark;
	}

	public void setVerifyReviewRemark(String verifyReviewRemark) {
		this.verifyReviewRemark = verifyReviewRemark;
	}
	public String getVerifyTrialUser() {
		return verifyTrialUser;
	}

	public void setVerifyTrialUser(String verifyTrialUser) {
		this.verifyTrialUser = verifyTrialUser;
	}

	public Date getVerifyTrialTime() {
		return verifyTrialTime;
	}

	public void setVerifyTrialTime(Date verifyTrialTime) {
		this.verifyTrialTime = verifyTrialTime;
	}

	public String getVerifyTrialRemark() {
		return verifyTrialRemark;
	}

	public void setVerifyTrialRemark(String verifyTrialRemark) {
		this.verifyTrialRemark = verifyTrialRemark;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		this.orderTypeName = orderTypes.get(orderType);
	}

	public Integer getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(Integer moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public Integer getApr() {
		return apr;
	}

	public void setApr(Integer apr) {
		this.apr = apr;
	}

	public Integer getLoanInterests() {
		return loanInterests;
	}

	public void setLoanInterests(Integer loanInterests) {
		this.loanInterests = loanInterests;
	}

	public Integer getIntoMoney() {
		return intoMoney;
	}

	public void setIntoMoney(Integer intoMoney) {
		this.intoMoney = intoMoney;
	}

	public Integer getLoanMethod() {
		return loanMethod;
	}

	public void setLoanMethod(Integer loanMethod) {
		this.loanMethod = loanMethod;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Date getLoanEndTime() {
		return loanEndTime;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public Integer getLateFeeApr() {
		return lateFeeApr;
	}

	public void setLateFeeApr(Integer lateFeeApr) {
		this.lateFeeApr = lateFeeApr;
	}

	public Integer getReceiveCardId() {
		return receiveCardId;
	}

	public void setReceiveCardId(Integer receiveCardId) {
		this.receiveCardId = receiveCardId;
	}

	public Integer getDebitCardId() {
		return debitCardId;
	}

	public void setDebitCardId(Integer debitCardId) {
		this.debitCardId = debitCardId;
	}

	public Integer getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(Integer capitalType) {
		this.capitalType = capitalType;
	}

	public String getReasonRemark() {
		return reasonRemark;
	}

	public void setReasonRemark(String reasonRemark) {
		this.reasonRemark = reasonRemark == null ? null : reasonRemark.trim();
	}

	public Integer getCreditLv() {
		return creditLv;
	}

	public void setCreditLv(Integer creditLv) {
		this.creditLv = creditLv;
	}

	public Byte getIsHitRiskRule() {
		return isHitRiskRule;
	}

	public void setIsHitRiskRule(Byte isHitRiskRule) {
		this.isHitRiskRule = isHitRiskRule;
	}

	public Byte getAutoRiskCheckStatus() {
		return autoRiskCheckStatus;
	}

	public void setAutoRiskCheckStatus(Byte autoRiskCheckStatus) {
		this.autoRiskCheckStatus = autoRiskCheckStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusName = borrowStatusMap.get(status);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	

	public Integer getBankIscmb() {
		return bankIscmb;
	}

	public void setBankIscmb(Integer bankIscmb) {
		this.bankIscmb = bankIscmb;
	}

	@Override
	public String toString() {
		return "BorrowOrder [apr=" + apr + ", autoRiskCheckStatus="
				+ autoRiskCheckStatus + ", capitalType=" + capitalType
				+ ", createdAt=" + createdAt + ", creditLv=" + creditLv
				+ ", debitCardId=" + debitCardId + ", id=" + id
				+ ", intoMoney=" + intoMoney + ", isHitRiskRule="
				+ isHitRiskRule + ", lateFeeApr=" + lateFeeApr
				+ ", loanEndTime=" + loanEndTime + ", loanInterests="
				+ loanInterests + ", loanMethod=" + loanMethod + ", loanTerm="
				+ loanTerm + ", loanTime=" + loanTime + ", moneyAmount="
				+ moneyAmount + ", operatorName=" + operatorName
				+ ", orderTime=" + orderTime + ", orderType=" + orderType
				+ ", orderTypeName=" + orderTypeName + ", outTradeNo="
				+ outTradeNo + ", reasonRemark=" + reasonRemark
				+ ", receiveCardId=" + receiveCardId + ", remark=" + remark
				+ ", status=" + status + ", statusName=" + statusName
				+ ", updatedAt=" + updatedAt + ", userId="
				+ userId + ", verifyLoanRemark=" + verifyLoanRemark
				+ ", verifyLoanTime=" + verifyLoanTime + ", verifyLoanUser="
				+ verifyLoanUser + ", verifyReviewRemark=" + verifyReviewRemark
				+ ", verifyReviewTime=" + verifyReviewTime
				+ ", verifyReviewUser=" + verifyReviewUser
				+ ", verifyTrialRemark=" + verifyTrialRemark
				+ ", verifyTrialTime=" + verifyTrialTime + ", verifyTrialUser="
				+ verifyTrialUser + "]";
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	

	
	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	
}