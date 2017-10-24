package com.hkd.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;


/**
 * 贷款详情需要用到的字段
 * Created by 1 on 2017-08-22.
 */

public class LoanDetails {

    /**
     * 总订单id
     */
    @ApiModelProperty(value = "总订单id" ,notes = "总订单id")
    private String poolId;

    /**
     * 分期订单id
     */
    @ApiModelProperty(value = "分期订单id" ,notes = "分期订单id")
    private String orderId;

    @ApiModelProperty(value = "用户id" ,notes = "用户id")
    private String userId;

    /**
     * 贷款总金额
     */
    @ApiModelProperty(value = "贷款总金额" ,notes = "贷款总金额")
    private String moneyAmountSum;

    /**
     * 待还总金额
     */
    @ApiModelProperty(value = "待还总金额" ,notes = "待还总金额")
    private String RepaymentSum;

    /**
     * 本期待还金额
     */
    @ApiModelProperty(value = "本期待还金额" ,notes = "本期待还金额")
    private String moneyAmount;

    /**
     * 每期的滞纳金
     */
    @ApiModelProperty(value = "每期的滞纳金" ,notes = "每期的滞纳金")
     private String lateFee;

    /**
     * 总订单的滞纳金
     */
    @ApiModelProperty(value = "总订单的滞纳金" ,notes = "总订单的滞纳金")
    private String lateFeeSum;

    /**
     * 应还日期
     */
    @ApiModelProperty(value = "应还日期" ,notes = "应还日期")
    private String loanEndTime;

    /**
     * 订单状态(0:待初审(待机审);-3:初审驳回;1:初审通过;-4:复审驳回;20:复审通过,待放款;-5:放款驳回;22:放款中;-10:放款失败;21已放款，还款中;23:部分还款;30:已还款;-11:已逾期;-20:已坏账，34逾期已还款；)
     */
    @ApiModelProperty(value = "订单状态(0:待初审(待机审);-3:初审驳回;1:初审通过;-4:复审驳回;20:复审通过,待放款;-5:放款驳回;22:放款中;-10:放款失败;21已放款，还款中;23:部分还款;30:已还款;-11:已逾期;-20:已坏账，34逾期已还款；)" ,notes = "订单状态")
    private String status;

    /**
     * 总订单应还时间
     */
    @ApiModelProperty(value = "总订单应还时间" ,notes = "总订单应还时间")
    private String sumLoanEndTime;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号" ,notes = "订单号")
    private String outTradeNo;

    /**
     * 排序字段对应的期数
     */
    @ApiModelProperty(value = "排序字段对应的期数" ,notes = "排序字段对应的期数")
    private String sort;

    /**
     * 实际还款日期
     */
    @ApiModelProperty(value = "实际还款日期" ,notes = "实际还款日期")
    private String repaymentRealTime;

    @ApiModelProperty(value = "总订单转态" ,notes = "总订单状态")
    private String allStatus;

    @ApiModelProperty(value = "借款合同url" ,notes = "借款合同url")
    private String contract;

    @ApiModelProperty(value = "借款合同png" ,notes = "借款合同png")
    private String contractImg;

    public String getMoneyAmountSum() {
        return moneyAmountSum;
    }

    public void setMoneyAmountSum(String moneyAmountSum) {
        this.moneyAmountSum = moneyAmountSum;
    }

    public String getRepaymentSum() {
        return RepaymentSum;
    }

    public void setRepaymentSum(String repaymentSum) {
        RepaymentSum = repaymentSum;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
    }

    public String getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(String loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSumLoanEndTime() {
        return sumLoanEndTime;
    }

    public void setSumLoanEndTime(String sumLoanEndTime) {
        this.sumLoanEndTime = sumLoanEndTime;
    }

    public String getLateFeeSum() {
        return lateFeeSum;
    }

    public void setLateFeeSum(String lateFeeSum) {
        this.lateFeeSum = lateFeeSum;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRepaymentRealTime() {
        return repaymentRealTime;
    }

    public void setRepaymentRealTime(String repaymentRealTime) {
        this.repaymentRealTime = repaymentRealTime;
    }

    public String getAllStatus() {
        return allStatus;
    }

    public void setAllStatus(String allStatus) {
        this.allStatus = allStatus;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContractImg() {
        return contractImg;
    }

    public void setContractImg(String contractImg) {
        this.contractImg = contractImg;
    }
}
