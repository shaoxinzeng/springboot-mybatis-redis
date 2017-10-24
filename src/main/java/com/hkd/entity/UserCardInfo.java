package com.hkd.entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_card_info")
public class UserCardInfo implements Serializable{
	private Integer id;
	private Integer bank_id;//银行卡编号
	private Integer userId;//用户编号
	private String bankName;//银行名称
	private String card_no;//银行卡号
	private String phone;//预留手机号码
	private String creditAmount;//信用额度
	private String validPeriod;//有效期
	private Integer status;//状态 (0:未生效   1:已生效)
	private Integer mainCard;//是否为主卡  0是 1 不是',
	private String openName;// 开户名称
	private String bankAddress;//开户地址
	private Integer type;//开类型 银行卡类型(1:信用卡   2:借记卡,3:对公账号)
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	public final static Integer STATUS_DEFAULT=0;//初始值
	public final static Integer STATUS_SUCCESS=1;//生效
	public final static Integer TYPE_DEBIT=2;//借记卡
	public final static Integer TYPE_CREDIT=1;//1:信用卡
	public final static Integer MAINCARD_Z=0;//主卡
	public final static Integer MAINCARD_F=1;//副卡
	public final static String BANK_RIDS="BANK_";
	public Integer getId() {
		return id;
	}
	private String agreeno;
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBank_id() {
		return bank_id;
	}
	public void setBank_id(Integer bankId) {
		bank_id = bankId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String cardNo) {
		card_no = cardNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getValidPeriod() {
		return validPeriod;
	}
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMainCard() {
		return mainCard;
	}
	public void setMainCard(Integer mainCard) {
		this.mainCard = mainCard;
	}
	public String getOpenName() {
		return openName;
	}
	public void setOpenName(String openName) {
		this.openName = openName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAgreeno() {
		return agreeno;
	}

	public void setAgreeno(String agreeno) {
		this.agreeno = agreeno;
	}
}
