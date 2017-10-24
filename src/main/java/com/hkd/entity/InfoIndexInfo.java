package com.hkd.entity;

import javax.persistence.Table;

@Table(name = "info_index_info")
public class InfoIndexInfo {
	private Integer userId;
	private String cardAmount;
	private String amountMin;
	private String rate;
	private Integer authInfo;
	private Integer authContacts;
	private Integer authBank;
	private Integer authSesame;
	private Integer authMobile;
	private Integer authCount;
	private Integer authSum;
	private String borrowStatus;
	private String bankNo;
	private String status;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getAmountMin() {
		return amountMin;
	}
	public void setAmountMin(String amountMin) {
		this.amountMin = amountMin;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public Integer getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(Integer authInfo) {
		this.authInfo = authInfo;
	}
	public Integer getAuthContacts() {
		return authContacts;
	}
	public void setAuthContacts(Integer authContacts) {
		this.authContacts = authContacts;
	}
	public Integer getAuthBank() {
		return authBank;
	}
	public void setAuthBank(Integer authBank) {
		this.authBank = authBank;
	}
	public Integer getAuthSesame() {
		return authSesame;
	}
	public void setAuthSesame(Integer authSesame) {
		this.authSesame = authSesame;
	}
	public Integer getAuthMobile() {
		return authMobile;
	}
	public void setAuthMobile(Integer authMobile) {
		this.authMobile = authMobile;
	}
	public Integer getAuthCount() {
		return authCount;
	}
	public void setAuthCount(Integer authCount) {
		this.authCount = authCount;
	}
	public Integer getAuthSum() {
		return authSum;
	}
	public void setAuthSum(Integer authSum) {
		this.authSum = authSum;
	}
	public String getBorrowStatus() {
		return borrowStatus;
	}
	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "InfoIndexInfo [amountMin=" + amountMin + ", authBank="
				+ authBank + ", authContacts=" + authContacts + ", authCount="
				+ authCount + ", authInfo=" + authInfo + ", authMobile="
				+ authMobile + ", authSesame=" + authSesame + ", authSum="
				+ authSum + ", bankNo=" + bankNo + ", borrowStatus="
				+ borrowStatus + ", cardAmount=" + cardAmount + ", rate="
				+ rate + ", status=" + status + ", userId=" + userId + "]";
	}
	
	

}
