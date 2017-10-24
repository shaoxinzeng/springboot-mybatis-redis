package com.hkd.entity;

import java.util.Date;

public class Repayment {
    private Integer id;

    private Integer userId;

    private Integer assetOrderId;

    private Long repaymentAmount;

    private Long repaymentedAmount;

    private Long repaymentPrincipal;

    private Long repaymentInterest;

    private Integer planLateFee;

    private Integer trueLateFee;

    private Integer lateFeeApr;

    private Date creditRepaymentTime;

    private Integer period;

    private Date repaymentTime;

    private Date repaymentRealTime;

    private Date lateFeeStartTime;

    private Date interestUpdateTime;

    private Integer lateDay;

    private Date createdAt;

    private Date updatedAt;

    private Integer autoDebitFailTimes;

    private Integer renewalCount;

    private Integer status;

    private String remark;

    private Integer collection;

    private Integer customerType;

    private String repaymentNo;

    // 查询扩展字段
    private Integer loanTerm;
    // 催收传过来的代扣ID
    private String withholdId;


    // 逾期调用催收，传用户基本信息、联系人、银行卡信息、借款信息、还款信息、还款详情
    public static final int OVERDUE_COLLECTION = 1;
    // 续期调用催收，传借款详情、还款信息
    public static final int RENEWAL_COLLECTION = 2;
    // 部分还款调用催收，传还款信息、还款详情
    public static final int BREPAY_COLLECTION = 3;
    // 全部还款调用催收，传借款详情、还款信息、还款详情
    public static final int REPAY_COLLECTION = 4;

    public static final int COLLECTION_WITHHOLD = 1; // 催收代扣
    public static final int TASK_WITHHOLD = 0; // 定时代扣


    public static final int COLLECTION_YES = 1; //
    public static final int COLLECTION_NO = 0;


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

    public Integer getAssetOrderId() {
        return assetOrderId;
    }

    public void setAssetOrderId(Integer assetOrderId) {
        this.assetOrderId = assetOrderId;
    }

    public Long getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Long repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Long getRepaymentedAmount() {
        return repaymentedAmount;
    }

    public void setRepaymentedAmount(Long repaymentedAmount) {
        this.repaymentedAmount = repaymentedAmount;
    }

    public Long getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(Long repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public Long getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(Long repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public Integer getPlanLateFee() {
        return planLateFee;
    }

    public void setPlanLateFee(Integer planLateFee) {
        this.planLateFee = planLateFee;
    }

    public Integer getTrueLateFee() {
        return trueLateFee;
    }

    public void setTrueLateFee(Integer trueLateFee) {
        this.trueLateFee = trueLateFee;
    }

    public Integer getLateFeeApr() {
        return lateFeeApr;
    }

    public void setLateFeeApr(Integer lateFeeApr) {
        this.lateFeeApr = lateFeeApr;
    }

    public Date getCreditRepaymentTime() {
        return creditRepaymentTime;
    }

    public void setCreditRepaymentTime(Date creditRepaymentTime) {
        this.creditRepaymentTime = creditRepaymentTime;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getRepaymentRealTime() {
        return repaymentRealTime;
    }

    public void setRepaymentRealTime(Date repaymentRealTime) {
        this.repaymentRealTime = repaymentRealTime;
    }

    public Date getLateFeeStartTime() {
        return lateFeeStartTime;
    }

    public void setLateFeeStartTime(Date lateFeeStartTime) {
        this.lateFeeStartTime = lateFeeStartTime;
    }

    public Date getInterestUpdateTime() {
        return interestUpdateTime;
    }

    public void setInterestUpdateTime(Date interestUpdateTime) {
        this.interestUpdateTime = interestUpdateTime;
    }

    public Integer getLateDay() {
        return lateDay;
    }

    public void setLateDay(Integer lateDay) {
        this.lateDay = lateDay;
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

    public Integer getAutoDebitFailTimes() {
        return autoDebitFailTimes;
    }

    public void setAutoDebitFailTimes(Integer autoDebitFailTimes) {
        this.autoDebitFailTimes = autoDebitFailTimes;
    }

    public Integer getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(Integer renewalCount) {
        this.renewalCount = renewalCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getRepaymentNo() {
        return repaymentNo;
    }

    public void setRepaymentNo(String repaymentNo) {
        this.repaymentNo = repaymentNo;
    }

    public String getWithholdId() {
        return withholdId;
    }

    public void setWithholdId(String withholdId) {
        this.withholdId = withholdId;
    }
}
