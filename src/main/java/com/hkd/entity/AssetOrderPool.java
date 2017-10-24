package com.hkd.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(name ="asset_order_pool")
public class AssetOrderPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "第三方订单流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "订单来源：1、现金侠...")
    private Integer orderType;

    @ApiModelProperty(value = "金额，单位为分")
    private Integer moneyAmount;

    @ApiModelProperty(value = "借款服务费利率(万分之一)")
    private Integer apr;

    @ApiModelProperty(value = "借款服务费")
    private Integer loanInterests;

    @ApiModelProperty(value = "实际到账金额，单位为分")
    private Integer intoMoney;

    @ApiModelProperty(value = "0:按天，1,：按月，2：按年")
    private Integer loanMethod;

    @ApiModelProperty(value = "根据loan_method确定，几天、几月、几年")
    private Integer loanTerm;

    @ApiModelProperty(value = "利率，单位为万分之几")
    @Column(name = "loan_rate")
    private Integer loanRate;

    @ApiModelProperty(value = "操作人")
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

    @ApiModelProperty(value = "下单时间")
    private Date orderTime;

    @ApiModelProperty(value = "放款时间，用于计算利息的起止时间")
    private String loanTime;

    @ApiModelProperty(value = "需要还款时间")
    private String loanEndTime;

    @ApiModelProperty(value = "滞纳金利率，单位为万分之几")
    private Integer lateFeeApr;

    @ApiModelProperty(value = "打款银行卡ID")
    private Integer receiveCardId;

    @ApiModelProperty(value = "扣款银行卡ID")
    private Integer debitCardId;

    @ApiModelProperty(value = "初审时间")
    private Date verifyTrialTime;

    @ApiModelProperty(value = "初审备注")
    private String verifyTrialRemark;

    private String verifyTrialUser;

    private Date verifyReviewTime;

    private String verifyReviewRemark;

    private String verifyReviewUser;

    private Date verifyLoanTime;

    private String verifyLoanRemark;

    private String verifyLoanUser;

    private Integer capitalType;

    private String reasonRemark;

    private Integer creditLv;

    private Byte isHitRiskRule;

    private Byte autoRiskCheckStatus;

    private Integer customerType;

    private String yurref;

    private String serialNo;

    private String idNumber;

    private String userPhone;

    private String realname;

    private String cardNo;

    private String bankNumber;

    private Integer bankIscmb;

    private Integer status;

    private String payRemark;

    private String payStatus;

    private String remark;

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

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime == null ? null : loanTime.trim();
    }

    public String getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(String loanEndTime) {
        this.loanEndTime = loanEndTime == null ? null : loanEndTime.trim();
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
        this.verifyTrialRemark = verifyTrialRemark == null ? null : verifyTrialRemark.trim();
    }

    public String getVerifyTrialUser() {
        return verifyTrialUser;
    }

    public void setVerifyTrialUser(String verifyTrialUser) {
        this.verifyTrialUser = verifyTrialUser == null ? null : verifyTrialUser.trim();
    }

    public Date getVerifyReviewTime() {
        return verifyReviewTime;
    }

    public void setVerifyReviewTime(Date verifyReviewTime) {
        this.verifyReviewTime = verifyReviewTime == null ? null : verifyReviewTime;
    }

    public String getVerifyReviewRemark() {
        return verifyReviewRemark;
    }

    public void setVerifyReviewRemark(String verifyReviewRemark) {
        this.verifyReviewRemark = verifyReviewRemark == null ? null : verifyReviewRemark.trim();
    }

    public String getVerifyReviewUser() {
        return verifyReviewUser;
    }

    public void setVerifyReviewUser(String verifyReviewUser) {
        this.verifyReviewUser = verifyReviewUser == null ? null : verifyReviewUser.trim();
    }

    public Date getVerifyLoanTime() {
        return verifyLoanTime;
    }

    public void setVerifyLoanTime(Date verifyLoanTime) {
        this.verifyLoanTime = verifyLoanTime;
    }

    public String getVerifyLoanRemark() {
        return verifyLoanRemark;
    }

    public void setVerifyLoanRemark(String verifyLoanRemark) {
        this.verifyLoanRemark = verifyLoanRemark == null ? null : verifyLoanRemark.trim();
    }

    public String getVerifyLoanUser() {
        return verifyLoanUser;
    }

    public void setVerifyLoanUser(String verifyLoanUser) {
        this.verifyLoanUser = verifyLoanUser == null ? null : verifyLoanUser.trim();
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

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getYurref() {
        return yurref;
    }

    public void setYurref(String yurref) {
        this.yurref = yurref == null ? null : yurref.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    public Integer getBankIscmb() {
        return bankIscmb;
    }

    public void setBankIscmb(Integer bankIscmb) {
        this.bankIscmb = bankIscmb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark == null ? null : payRemark.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Integer loanRate) {
        this.loanRate = loanRate;
    }

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

    /**机审或人工审核借款信息的标识*/
    public static final String REVIEW_BORROW = "REVIEW_BORROW_";


    public static final Map<Integer, String> loanMethed = new HashMap<Integer, String>();

    public static final Map<Integer, String> orderTypes = new HashMap<Integer, String>();

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


    /**
     * 审核失败
     */
    public static final Map<Integer, String> borrowStatusMap_shenheFail = new HashMap<Integer, String>();

    static {
        //审核中
        borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_DCS, shenhezhong);
        borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_CSTG, shenhezhong);
        borrowStatusMap_shenhezhong.put(BorrowOrder.STATUS_FSTG, shenhezhong);
        borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_CSBH, shenheFail);
        borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FSBH, shenheFail);
        borrowStatusMap_shenheFail.put(BorrowOrder.STATUS_FKBH, shenheFail);

    }

}