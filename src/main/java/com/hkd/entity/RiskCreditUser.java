package com.hkd.entity;//

import java.math.BigDecimal;
import java.util.Date;

public class RiskCreditUser {
	private Integer id;// 主键ID
	private Integer userId;// 用户主键
	private Integer assetId;// 借款信息申请表主键ID
	private String userName;// 用户真实姓名
	private String cardNum;// 用户身份证号
	private String userPhone;// 用户身份证号
	private Integer age;// 年龄
	private Integer sex;// 性别
	private Integer education;// 学历（1博士、2硕士、3本科、4大专、5中专、6高中、7初中及以下）
	private BigDecimal amountAddsum;// 后台累计增加的额度
	private BigDecimal amountMax;// 机审额度
	private Date jsAmountTime;// 机审额度更新时间
	/*********************** 芝麻相关开始 ************************/
	private BigDecimal zmScore;// 芝麻积分
	private Date zmScoreTime;// 芝麻分上次更新时间
	private Integer zmIndustyBlack;// 芝麻行业关注度黑名单1.是；2否
	private Integer zmOver;// 行业关注度接口中返回的借贷逾期记录数AA001借贷逾期的记录数
	private Integer zmNoPayOver;// 行业关注度接口中返回的逾期未支付记录数，包括AD001 逾期未支付、AE001
	// 逾期未支付的记录总数
	private Date zmIndustyTime;// 行业关注度上次更新时间
	private Integer zmStatus;// 芝麻数据统计状态，1失败；2成功
	private BigDecimal myHb;// 蚂蚁花呗额度
	private Date myHbTime;// 蚂蚁花呗额度更新时间
	/*********************** 芝麻相关结束 ************************/

	/*********************** 同盾相关开始 ************************/
	private String tdReportId;// 同盾贷前审核报告标识
	private Date tdReportTime;// 同盾贷前审核报告上次生成时间
	private BigDecimal tdScore;// 同盾分，分值越高风险越大
	private int tdPhoneBlack;// 同盾手机网贷黑名单个数
	private int tdCardNumBlack;// 同盾身份证号网贷黑名单个数
	private int tdMonth1Borrow;// 同盾1月内多平台申请借款
	private int tdDay7Borrow;// 同盾7天内多平台申请借款
	private int tdMonth1CardNumDeviceBorrow;// 同盾1月内身份证使用多设备申请
	private int tdDay7DeviceCardOrPhoneBorrow;// 同盾7天内设备使用多身份证或手机号申请
	private int tdDay7CardDevice;// 7天内身份证关联设备数
	private int tdMonth3ApplyCard;// 3个月内申请信息关联多个身份证
	private int tdMonth3CardApply;// 3个月内身份证关联多个申请信息
	private Date tdDetailTime;// 同盾详情上次更新时间
	private Integer tdStatus;// 同盾接口状态，默认1失败,成功为2，生成报告接口状态以是否有reportId为准，查询报告以此为准
	/*********************** 同盾相关结束 ************************/

	/*********************** 白骑士相关开始 ************************/
	private Integer bqsBlack;// 白骑士1.通过；2.拒绝（命中黑名单）；3.建议人工审核(命中灰名单)
	private Date bqsBlackTime;// 白骑士更新时间
	private Integer bqsStatus;// 白骑士接口状态，默认1失败；成功是2
	/*********************** 白骑士相关结束 ************************/
	/*********************** 91征信相关开始 ************************/
	private Integer jyLoanNum;// 91征信借款总数
	private Integer jyJdNum;// 91征信拒单记录数
	private BigDecimal jyJdBl;// 91征信拒单记录占比
	private Integer jyOverNum;// 91征信逾期记录数、逾期金额大于0或者状态是逾期
	private BigDecimal jyOverBl;// 91征信逾期记录占比
	private Integer jyFkNum;// 91征信已放款总数
	private Integer jyHkNum;// 91征信已还款总数
	private BigDecimal jyHkBl;// 91征信已还款总数除以放款总数
	private Date jyTime;// 91数据更新时间
	private Integer jyStatus;// 91征信接口状态，默认1失败；成功是2
	/*********************** 91征信相关结束 ************************/
	/*********************** 密罐相关开始 ************************/
	private BigDecimal mgBlackScore;// 密罐黑中介分
	private Integer mgDay7Num;// 蜜罐近7天查询次数
	private Integer mgMonth1Num;// 蜜罐近1个月查询次数
	private Integer mgBlack;// 蜜罐黑名单1是2否；身份证和姓名是否在黑名单或者手机和姓名是否在黑名单
	private Date mgTime;// 密罐更新时间
	private Integer mgStatus;// 密罐接口状态，默认1失败；成功是2
	/*********************** 密罐相关结束 ************************/
	/*********************** 聚信立相关开始 ************************/
	private String jxlToken;// 聚信立开始采集数据时存入token
	private Date jxlTokenTime;// 聚信立token入库时间
	private String jxlDetail;// 聚信立成功采集后返回的数据
	private Date jxlDetailTime;// 聚信立成功采集详情数据的时间
	private Integer jxlZjDkNum;// 聚信立贷款类号码主叫个数
	private Integer jxlBjDkNum;// 聚信立贷款类号码被叫个数
	private BigDecimal jxlYjHf;// 聚信立月均话费
	private Integer jxlLink2Days;// 聚信立通话详单和用户第二联系人最晚联系日期到目前的天数
	private Integer jxlLink1Days;// 聚信立通话详单和用户第一联系人最晚联系日期到目前的天数
	private Integer jxlLink2Num;// 聚信立通话详单和用户第二联系人的通话次数
	private Integer jxlLink1Num;// 聚信立通话详单和用户第一联系人的通话次数
	private Integer jxlLink2Order;// 聚信立第二联系人通话次数排名
	private Integer jxlLink1Order;// 聚信立第一联系人通话次数排名
	private Integer jxlGjTs;// 聚信立关机天数，手机静默情况
	private Integer jxlTotal;// 聚信立统计关机天数的周期（天数）
	private BigDecimal jxlGjBl;// 聚信立关机天数比例
	private Integer jxlLxGjTs;// 聚信立最大连续关机天数
	private Integer jxlHtPhoneNum;// 聚信立互通电话数量
	private Integer jxlAmthNum;// 聚信立澳门通话次数
	private Integer jxlPhoneRegDays;// 聚信立手机开户时间距离当前的天数
	private Date jxlTime;// 聚信立分析数据更新时间

	/*********************** 聚信立相关结束 ************************/
	/*********************** 宜信相关开始 ************************/
	private Integer yxFxNum;// 宜信命中风险名单的记录数
	private Integer yxMonth3Over;// 宜信三个月以上逾期发生的次数
	private Integer yxLessMonth3Over;// 宜信三个月以内逾期发生的次数，历史逾期次数减三个月的逾期次数
	private Integer yxStatus;// 宜信接口状态，默认1失败；成功是2
	private Date yxTime;// 宜信入库时间
	/*********************** 宜信相关结束 ************************/
	private Integer riskStatus;// 当前风控运算状态1.待机审；2.机审通过(接口成功)；3机审不通过(接口成功)4，机审建议复审(接口成功)
	private String riskRemark;// 机审备注
	private Integer historyOverNum;// 历史逾期总记录数，逾期并还款也纳为逾期记录
	private Integer lastOverDays;// 最近一次逾期总天数，逾期并还款也算
	private Integer csjy;// 1通过；2拒绝；3无建议
	private Integer customerType;// 是否是老用户：0、新用户；1；老用户
	private Integer userContactSize;// 用户通讯录的联系人数量
	private Date lastFail;// 最近一次被拒的时间,非数据库字段
	private Integer lastDays;// 最近一次被拒的时间到当前间隔的天数
	/*********************** 反欺诈相关开始 ************************/
	private String presentAddress;// 当前住址
	private String companyAddress;// 公司地址
	private String companyName;// 公司名称
	private String companyPhone;// 公司电话
	private String equipmentNumber;// 注册设备号
	private Integer presentAddNum;// 常用地址重复次数
	private Integer companyAddNum;// 公司地址重复次数
	private Integer manyDeviceLoginNum;// 多设备登录同一个账号次数
	private Integer oneDeviceRegManyPhoneNum;// 一个设备注册多个用户次数
	private Integer companyNameNum;// 单位名称出现次数
	private Integer companyPhoneDiffNameOrAdd;// 同一个单位电话关联不同单位名称或地址的次数
	private Integer companyAddDiffNameOrPhone;// 同一个单位地址关联不同单位号码或者名称
	private Integer companyNameDiffAddOrPhone;// 同一个单位名称关联不同地址或电话
	private Integer concatApplyNum;// 联系人申请借款数

	/*********************** 反欺诈相关结束 ************************/
	private BigDecimal moneyAmount;
	private Date addTime;

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

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public BigDecimal getAmountAddsum() {
		return amountAddsum;
	}

	public void setAmountAddsum(BigDecimal amountAddsum) {
		this.amountAddsum = amountAddsum;
	}

	public BigDecimal getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(BigDecimal amountMax) {
		this.amountMax = amountMax;
	}

	public Date getJsAmountTime() {
		return jsAmountTime;
	}

	public void setJsAmountTime(Date jsAmountTime) {
		this.jsAmountTime = jsAmountTime;
	}

	public BigDecimal getZmScore() {
		return zmScore;
	}

	public void setZmScore(BigDecimal zmScore) {
		this.zmScore = zmScore;
	}

	public Date getZmScoreTime() {
		return zmScoreTime;
	}

	public void setZmScoreTime(Date zmScoreTime) {
		this.zmScoreTime = zmScoreTime;
	}

	public Integer getZmIndustyBlack() {
		return zmIndustyBlack;
	}

	public void setZmIndustyBlack(Integer zmIndustyBlack) {
		this.zmIndustyBlack = zmIndustyBlack;
	}

	public Integer getZmOver() {
		return zmOver;
	}

	public void setZmOver(Integer zmOver) {
		this.zmOver = zmOver;
	}

	public Integer getZmNoPayOver() {
		return zmNoPayOver;
	}

	public void setZmNoPayOver(Integer zmNoPayOver) {
		this.zmNoPayOver = zmNoPayOver;
	}

	public Date getZmIndustyTime() {
		return zmIndustyTime;
	}

	public void setZmIndustyTime(Date zmIndustyTime) {
		this.zmIndustyTime = zmIndustyTime;
	}

	public BigDecimal getMyHb() {
		return myHb;
	}

	public void setMyHb(BigDecimal myHb) {
		this.myHb = myHb;
	}

	public Date getMyHbTime() {
		return myHbTime;
	}

	public void setMyHbTime(Date myHbTime) {
		this.myHbTime = myHbTime;
	}

	public Integer getZmStatus() {
		return zmStatus;
	}

	public void setZmStatus(Integer zmStatus) {
		this.zmStatus = zmStatus;
	}

	public String getTdReportId() {
		return tdReportId;
	}

	public void setTdReportId(String tdReportId) {
		this.tdReportId = tdReportId;
	}

	public Date getTdReportTime() {
		return tdReportTime;
	}

	public void setTdReportTime(Date tdReportTime) {
		this.tdReportTime = tdReportTime;
	}

	public BigDecimal getTdScore() {
		return tdScore;
	}

	public void setTdScore(BigDecimal tdScore) {
		this.tdScore = tdScore;
	}

	public int getTdPhoneBlack() {
		return tdPhoneBlack;
	}

	public void setTdPhoneBlack(int tdPhoneBlack) {
		this.tdPhoneBlack = tdPhoneBlack;
	}

	public int getTdCardNumBlack() {
		return tdCardNumBlack;
	}

	public void setTdCardNumBlack(int tdCardNumBlack) {
		this.tdCardNumBlack = tdCardNumBlack;
	}

	public int getTdMonth1Borrow() {
		return tdMonth1Borrow;
	}

	public void setTdMonth1Borrow(int tdMonth1Borrow) {
		this.tdMonth1Borrow = tdMonth1Borrow;
	}

	public int getTdDay7Borrow() {
		return tdDay7Borrow;
	}

	public void setTdDay7Borrow(int tdDay7Borrow) {
		this.tdDay7Borrow = tdDay7Borrow;
	}

	public int getTdMonth1CardNumDeviceBorrow() {
		return tdMonth1CardNumDeviceBorrow;
	}

	public void setTdMonth1CardNumDeviceBorrow(int tdMonth1CardNumDeviceBorrow) {
		this.tdMonth1CardNumDeviceBorrow = tdMonth1CardNumDeviceBorrow;
	}

	public int getTdDay7DeviceCardOrPhoneBorrow() {
		return tdDay7DeviceCardOrPhoneBorrow;
	}

	public void setTdDay7DeviceCardOrPhoneBorrow(
			int tdDay7DeviceCardOrPhoneBorrow) {
		this.tdDay7DeviceCardOrPhoneBorrow = tdDay7DeviceCardOrPhoneBorrow;
	}

	public int getTdDay7CardDevice() {
		return tdDay7CardDevice;
	}

	public void setTdDay7CardDevice(int tdDay7CardDevice) {
		this.tdDay7CardDevice = tdDay7CardDevice;
	}

	public int getTdMonth3ApplyCard() {
		return tdMonth3ApplyCard;
	}

	public void setTdMonth3ApplyCard(int tdMonth3ApplyCard) {
		this.tdMonth3ApplyCard = tdMonth3ApplyCard;
	}

	public int getTdMonth3CardApply() {
		return tdMonth3CardApply;
	}

	public void setTdMonth3CardApply(int tdMonth3CardApply) {
		this.tdMonth3CardApply = tdMonth3CardApply;
	}

	public Date getTdDetailTime() {
		return tdDetailTime;
	}

	public void setTdDetailTime(Date tdDetailTime) {
		this.tdDetailTime = tdDetailTime;
	}

	public Integer getTdStatus() {
		return tdStatus;
	}

	public void setTdStatus(Integer tdStatus) {
		this.tdStatus = tdStatus;
	}

	public Integer getBqsBlack() {
		return bqsBlack;
	}

	public void setBqsBlack(Integer bqsBlack) {
		this.bqsBlack = bqsBlack;
	}

	public Date getBqsBlackTime() {
		return bqsBlackTime;
	}

	public void setBqsBlackTime(Date bqsBlackTime) {
		this.bqsBlackTime = bqsBlackTime;
	}

	public Integer getBqsStatus() {
		return bqsStatus;
	}

	public void setBqsStatus(Integer bqsStatus) {
		this.bqsStatus = bqsStatus;
	}

	public Integer getJyLoanNum() {
		return jyLoanNum;
	}

	public void setJyLoanNum(Integer jyLoanNum) {
		this.jyLoanNum = jyLoanNum;
	}

	public Integer getJyJdNum() {
		return jyJdNum;
	}

	public void setJyJdNum(Integer jyJdNum) {
		this.jyJdNum = jyJdNum;
	}

	public BigDecimal getJyJdBl() {
		return jyJdBl;
	}

	public void setJyJdBl(BigDecimal jyJdBl) {
		this.jyJdBl = jyJdBl;
	}

	public Integer getJyOverNum() {
		return jyOverNum;
	}

	public void setJyOverNum(Integer jyOverNum) {
		this.jyOverNum = jyOverNum;
	}

	public BigDecimal getJyOverBl() {
		return jyOverBl;
	}

	public void setJyOverBl(BigDecimal jyOverBl) {
		this.jyOverBl = jyOverBl;
	}

	public Integer getJyFkNum() {
		return jyFkNum;
	}

	public void setJyFkNum(Integer jyFkNum) {
		this.jyFkNum = jyFkNum;
	}

	public Integer getJyHkNum() {
		return jyHkNum;
	}

	public void setJyHkNum(Integer jyHkNum) {
		this.jyHkNum = jyHkNum;
	}

	public BigDecimal getJyHkBl() {
		return jyHkBl;
	}

	public void setJyHkBl(BigDecimal jyHkBl) {
		this.jyHkBl = jyHkBl;
	}

	public Date getJyTime() {
		return jyTime;
	}

	public void setJyTime(Date jyTime) {
		this.jyTime = jyTime;
	}

	public Integer getJyStatus() {
		return jyStatus;
	}

	public void setJyStatus(Integer jyStatus) {
		this.jyStatus = jyStatus;
	}

	public BigDecimal getMgBlackScore() {
		return mgBlackScore;
	}

	public void setMgBlackScore(BigDecimal mgBlackScore) {
		this.mgBlackScore = mgBlackScore;
	}

	public Integer getMgDay7Num() {
		return mgDay7Num;
	}

	public void setMgDay7Num(Integer mgDay7Num) {
		this.mgDay7Num = mgDay7Num;
	}

	public Integer getMgMonth1Num() {
		return mgMonth1Num;
	}

	public void setMgMonth1Num(Integer mgMonth1Num) {
		this.mgMonth1Num = mgMonth1Num;
	}

	public Integer getMgBlack() {
		return mgBlack;
	}

	public void setMgBlack(Integer mgBlack) {
		this.mgBlack = mgBlack;
	}

	public Date getMgTime() {
		return mgTime;
	}

	public void setMgTime(Date mgTime) {
		this.mgTime = mgTime;
	}

	public Integer getMgStatus() {
		return mgStatus;
	}

	public void setMgStatus(Integer mgStatus) {
		this.mgStatus = mgStatus;
	}

	public String getJxlToken() {
		return jxlToken;
	}

	public void setJxlToken(String jxlToken) {
		this.jxlToken = jxlToken;
	}

	public Date getJxlTokenTime() {
		return jxlTokenTime;
	}

	public void setJxlTokenTime(Date jxlTokenTime) {
		this.jxlTokenTime = jxlTokenTime;
	}

	public String getJxlDetail() {
		return jxlDetail;
	}

	public void setJxlDetail(String jxlDetail) {
		this.jxlDetail = jxlDetail;
	}

	public Date getJxlDetailTime() {
		return jxlDetailTime;
	}

	public void setJxlDetailTime(Date jxlDetailTime) {
		this.jxlDetailTime = jxlDetailTime;
	}

	public Integer getJxlZjDkNum() {
		return jxlZjDkNum;
	}

	public void setJxlZjDkNum(Integer jxlZjDkNum) {
		this.jxlZjDkNum = jxlZjDkNum;
	}

	public Integer getJxlBjDkNum() {
		return jxlBjDkNum;
	}

	public void setJxlBjDkNum(Integer jxlBjDkNum) {
		this.jxlBjDkNum = jxlBjDkNum;
	}

	public BigDecimal getJxlYjHf() {
		return jxlYjHf;
	}

	public void setJxlYjHf(BigDecimal jxlYjHf) {
		this.jxlYjHf = jxlYjHf;
	}

	public Integer getJxlLink2Days() {
		return jxlLink2Days;
	}

	public void setJxlLink2Days(Integer jxlLink2Days) {
		this.jxlLink2Days = jxlLink2Days;
	}

	public Integer getJxlLink1Days() {
		return jxlLink1Days;
	}

	public void setJxlLink1Days(Integer jxlLink1Days) {
		this.jxlLink1Days = jxlLink1Days;
	}

	public Integer getJxlLink2Num() {
		return jxlLink2Num;
	}

	public void setJxlLink2Num(Integer jxlLink2Num) {
		this.jxlLink2Num = jxlLink2Num;
	}

	public Integer getJxlLink1Num() {
		return jxlLink1Num;
	}

	public void setJxlLink1Num(Integer jxlLink1Num) {
		this.jxlLink1Num = jxlLink1Num;
	}

	public Integer getJxlLink2Order() {
		return jxlLink2Order;
	}

	public void setJxlLink2Order(Integer jxlLink2Order) {
		this.jxlLink2Order = jxlLink2Order;
	}

	public Integer getJxlLink1Order() {
		return jxlLink1Order;
	}

	public void setJxlLink1Order(Integer jxlLink1Order) {
		this.jxlLink1Order = jxlLink1Order;
	}

	public Integer getJxlGjTs() {
		return jxlGjTs;
	}

	public void setJxlGjTs(Integer jxlGjTs) {
		this.jxlGjTs = jxlGjTs;
	}

	public Integer getJxlTotal() {
		return jxlTotal;
	}

	public void setJxlTotal(Integer jxlTotal) {
		this.jxlTotal = jxlTotal;
	}

	public BigDecimal getJxlGjBl() {
		return jxlGjBl;
	}

	public void setJxlGjBl(BigDecimal jxlGjBl) {
		this.jxlGjBl = jxlGjBl;
	}

	public Integer getJxlLxGjTs() {
		return jxlLxGjTs;
	}

	public void setJxlLxGjTs(Integer jxlLxGjTs) {
		this.jxlLxGjTs = jxlLxGjTs;
	}

	public Integer getJxlHtPhoneNum() {
		return jxlHtPhoneNum;
	}

	public void setJxlHtPhoneNum(Integer jxlHtPhoneNum) {
		this.jxlHtPhoneNum = jxlHtPhoneNum;
	}

	public Integer getJxlAmthNum() {
		return jxlAmthNum;
	}

	public void setJxlAmthNum(Integer jxlAmthNum) {
		this.jxlAmthNum = jxlAmthNum;
	}

	public Integer getJxlPhoneRegDays() {
		return jxlPhoneRegDays;
	}

	public void setJxlPhoneRegDays(Integer jxlPhoneRegDays) {
		this.jxlPhoneRegDays = jxlPhoneRegDays;
	}

	public Date getJxlTime() {
		return jxlTime;
	}

	public void setJxlTime(Date jxlTime) {
		this.jxlTime = jxlTime;
	}

	public Integer getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(Integer riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getRiskRemark() {
		return riskRemark;
	}

	public void setRiskRemark(String riskRemark) {
		this.riskRemark = riskRemark;
	}

	public Integer getHistoryOverNum() {
		return historyOverNum;
	}

	public void setHistoryOverNum(Integer historyOverNum) {
		this.historyOverNum = historyOverNum;
	}

	public Integer getLastOverDays() {
		return lastOverDays;
	}

	public void setLastOverDays(Integer lastOverDays) {
		this.lastOverDays = lastOverDays;
	}

	public Integer getCsjy() {
		return csjy;
	}

	public void setCsjy(Integer csjy) {
		this.csjy = csjy;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getUserContactSize() {
		return userContactSize;
	}

	public void setUserContactSize(Integer userContactSize) {
		this.userContactSize = userContactSize;
	}

	public Date getLastFail() {
		return lastFail;
	}

	public void setLastFail(Date lastFail) {
		this.lastFail = lastFail;
	}

	public Integer getLastDays() {
		return lastDays;
	}

	public void setLastDays(Integer lastDays) {
		this.lastDays = lastDays;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public Integer getPresentAddNum() {
		return presentAddNum;
	}

	public void setPresentAddNum(Integer presentAddNum) {
		this.presentAddNum = presentAddNum;
	}

	public Integer getCompanyAddNum() {
		return companyAddNum;
	}

	public void setCompanyAddNum(Integer companyAddNum) {
		this.companyAddNum = companyAddNum;
	}

	public Integer getManyDeviceLoginNum() {
		return manyDeviceLoginNum;
	}

	public void setManyDeviceLoginNum(Integer manyDeviceLoginNum) {
		this.manyDeviceLoginNum = manyDeviceLoginNum;
	}

	public Integer getOneDeviceRegManyPhoneNum() {
		return oneDeviceRegManyPhoneNum;
	}

	public void setOneDeviceRegManyPhoneNum(Integer oneDeviceRegManyPhoneNum) {
		this.oneDeviceRegManyPhoneNum = oneDeviceRegManyPhoneNum;
	}

	public Integer getCompanyNameNum() {
		return companyNameNum;
	}

	public void setCompanyNameNum(Integer companyNameNum) {
		this.companyNameNum = companyNameNum;
	}

	public Integer getCompanyPhoneDiffNameOrAdd() {
		return companyPhoneDiffNameOrAdd;
	}

	public void setCompanyPhoneDiffNameOrAdd(Integer companyPhoneDiffNameOrAdd) {
		this.companyPhoneDiffNameOrAdd = companyPhoneDiffNameOrAdd;
	}

	public Integer getCompanyAddDiffNameOrPhone() {
		return companyAddDiffNameOrPhone;
	}

	public void setCompanyAddDiffNameOrPhone(Integer companyAddDiffNameOrPhone) {
		this.companyAddDiffNameOrPhone = companyAddDiffNameOrPhone;
	}

	public Integer getCompanyNameDiffAddOrPhone() {
		return companyNameDiffAddOrPhone;
	}

	public void setCompanyNameDiffAddOrPhone(Integer companyNameDiffAddOrPhone) {
		this.companyNameDiffAddOrPhone = companyNameDiffAddOrPhone;
	}

	public Integer getConcatApplyNum() {
		return concatApplyNum;
	}

	public void setConcatApplyNum(Integer concatApplyNum) {
		this.concatApplyNum = concatApplyNum;
	}

	public Integer getYxMonth3Over() {
		return yxMonth3Over;
	}

	public void setYxMonth3Over(Integer yxMonth3Over) {
		this.yxMonth3Over = yxMonth3Over;
	}

	public Integer getYxStatus() {
		return yxStatus;
	}

	public void setYxStatus(Integer yxStatus) {
		this.yxStatus = yxStatus;
	}

	public Date getYxTime() {
		return yxTime;
	}

	public void setYxTime(Date yxTime) {
		this.yxTime = yxTime;
	}

	public Integer getYxFxNum() {
		return yxFxNum;
	}

	public void setYxFxNum(Integer yxFxNum) {
		this.yxFxNum = yxFxNum;
	}

	public Integer getYxLessMonth3Over() {
		return yxLessMonth3Over;
	}

	public void setYxLessMonth3Over(Integer yxLessMonth3Over) {
		this.yxLessMonth3Over = yxLessMonth3Over;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public RiskCreditUser() {
		super();
	}

	public RiskCreditUser(Integer userId) {
		super();
		this.userId = userId;
	}

	/**
	 * 更新用户表芝麻分专用
	 * 
	 * @param userId
	 *            用户主键
	 * @param zmScore
	 *            芝麻分
	 */
	public RiskCreditUser(Integer userId, BigDecimal zmScore) {
		super();
		this.userId = userId;
		this.zmScore = zmScore;
	}

	/**
	 * 更新用户表行业关注度专用
	 * 
	 * @param userId
	 *            用户主键
	 * @param zmIndustyBlack
	 *            行业关注度黑名单1是2否
	 * @param zmOver
	 *            行业关注度接口中返回的借贷逾期记录数AA001借贷逾期的记录数
	 * @param zmNoPayOver
	 *            行业关注度接口中返回的逾期未支付记录数，包括AD001 逾期未支付、AE001 逾期未支付的记录总数
	 */
	public RiskCreditUser(Integer userId, Integer zmIndustyBlack,
                          Integer zmOver, Integer zmNoPayOver) {
		super();
		this.userId = userId;
		this.zmIndustyBlack = zmIndustyBlack;
		this.zmOver = zmOver;
		this.zmNoPayOver = zmNoPayOver;
	}

	/**
	 * 更新同盾详情专用
	 * 
	 * @param id
	 *            征信主键
	 * @param tdScore
	 *            同盾分，分值越高风险越大
	 * @param tdPhoneBlack
	 *            同盾手机网贷黑名单个数
	 * @param tdCardNumBlack
	 *            同盾身份证号网贷黑名单个数
	 * @param tdMonth1Borrow
	 *            同盾1月内多平台申请借款
	 * @param tdDay7Borrow
	 *            同盾7天内多平台申请借款
	 * @param tdMonth1CardNumDeviceBorrow
	 *            同盾1月内身份证使用多设备申请
	 * @param tdDay7DeviceCardOrPhoneBorrow
	 *            同盾1月内设备使用多身份证或手机号申请
	 * @param tdDay7CardDevice
	 *            7天内身份证关联设备数
	 * @param tdMonth3ApplyCard
	 *            3个月内申请信息关联多个身份证
	 * @param tdMonth3CardApply
	 *            3个月内身份证关联多个申请信息
	 * @param tdReportId
	 *            同盾报告ID
	 */
	public RiskCreditUser(Integer id, BigDecimal tdScore, int tdPhoneBlack,
                          int tdCardNumBlack, int tdMonth1Borrow, int tdDay7Borrow,
                          int tdMonth1CardNumDeviceBorrow, int tdDay7DeviceCardOrPhoneBorrow,
                          int tdDay7CardDevice, int tdMonth3ApplyCard, int tdMonth3CardApply,
                          String tdReportId) {
		super();
		this.id = id;
		this.tdScore = tdScore;
		this.tdPhoneBlack = tdPhoneBlack;
		this.tdCardNumBlack = tdCardNumBlack;
		this.tdMonth1Borrow = tdMonth1Borrow;
		this.tdDay7Borrow = tdDay7Borrow;
		this.tdMonth1CardNumDeviceBorrow = tdMonth1CardNumDeviceBorrow;
		this.tdDay7DeviceCardOrPhoneBorrow = tdDay7DeviceCardOrPhoneBorrow;
		this.tdDay7CardDevice = tdDay7CardDevice;
		this.tdMonth3ApplyCard = tdMonth3ApplyCard;
		this.tdMonth3CardApply = tdMonth3CardApply;
		this.tdReportId = tdReportId;
	}

	/**
	 * 白骑士更新专用
	 * 
	 * @param id
	 *            征信表主键
	 * @param bqsBlack
	 *            是否是黑名单 1.通过；2.拒绝（命中黑名单）；3.建议人工审核(命中灰名单)
	 */
	public RiskCreditUser(Integer id, Integer bqsBlack) {
		super();
		this.id = id;
		this.bqsBlack = bqsBlack;
	}

	/**
	 * 91征信专用
	 * 
	 * @param id
	 *            主键
	 * @param jyLoanNum
	 *            借款信息总数
	 * @param jyJdNum
	 *            拒单数
	 * @param jyJdBl
	 *            拒单比例
	 * @param jyOverNum
	 *            逾期数，逾期金额大于0或者状态是逾期
	 * @param jyOverBl
	 *            逾期比例
	 * @param jyFkNum
	 *            91征信已放款总数
	 * @param jyHkNum
	 *            91征信已还款总数
	 * @param jyHkBl
	 *            91征信还款比例
	 */
	public RiskCreditUser(Integer id, Integer jyLoanNum, Integer jyJdNum,
                          BigDecimal jyJdBl, Integer jyOverNum, BigDecimal jyOverBl,
                          Integer jyFkNum, Integer jyHkNum, BigDecimal jyHkBl) {
		super();
		this.id = id;
		this.jyLoanNum = jyLoanNum;
		this.jyJdNum = jyJdNum;
		this.jyJdBl = jyJdBl;
		this.jyOverNum = jyOverNum;
		this.jyOverBl = jyOverBl;
		this.jyFkNum = jyFkNum;
		this.jyHkNum = jyHkNum;
		this.jyHkBl = jyHkBl;
	}

	/**
	 * 密罐专用
	 * 
	 * @param id
	 *            征信主键
	 * @param mgBlackScore
	 *            密罐黑中介分
	 * @param mgDay7Num
	 *            密罐七天内被查询次数
	 * @param mgMonth1Num
	 *            密罐一个月内被查询次数
	 * @param mgBlack
	 *            密罐黑名单 ,1是2否；身份证和姓名是否在黑名单或者手机和姓名是否在黑名单
	 */
	public RiskCreditUser(Integer id, BigDecimal mgBlackScore,
                          Integer mgDay7Num, Integer mgMonth1Num, Integer mgBlack) {
		super();
		this.id = id;
		this.mgBlackScore = mgBlackScore;
		this.mgDay7Num = mgDay7Num;
		this.mgMonth1Num = mgMonth1Num;
		this.mgBlack = mgBlack;
	}

	/**
	 * 用户表聚信立分析数据专用
	 * 
	 * @param userId
	 *            用户主键
	 * @param jxlZjDkNum
	 *            聚信立贷款类号码主叫个数
	 * @param jxlBjDkNum
	 *            聚信立贷款类号码被叫个数
	 * @param jxlYjHf
	 *            聚信立月均话费
	 * @param jxlLink2Days
	 *            聚信立通话详单和用户第二联系人最晚联系日期到目前的天数
	 * @param jxlLink1Days
	 *            聚信立通话详单和用户第一联系人最晚联系日期到目前的天数
	 * @param jxlLink2Num
	 *            聚信立通话详单和用户第二联系人的通话次数
	 * @param jxlLink1Num
	 *            聚信立通话详单和用户第一联系人的通话次数
	 * @param jxlLink2Order
	 *            聚信立第二联系人通话次数排名
	 * @param jxlLink1Order
	 *            聚信立第二联系人通话次数排名
	 * @param jxlGjTs
	 *            聚信立关机天数，手机静默情况
	 * @param jxlHtPhoneNum
	 *            聚信立互通电话数量（结合电话详单和手机通讯录）
	 * @param jxlAmthNum
	 *            聚信立澳门通话次数
	 * @param jxlPhoneRegDays
	 *            手机号开户日期到当前时间的天数
	 * @param jxlDetail
	 *            聚信立详情
	 * @param jxlTotal
	 *            聚信立关机天数统计周期(天数)
	 * @param jxlGjBl
	 *            聚信立关机比例
	 * @param jxlLxGjTs
	 *            聚信立最大连续关机天数
	 */
	public RiskCreditUser(Integer userId, Integer jxlZjDkNum,
                          Integer jxlBjDkNum, BigDecimal jxlYjHf, Integer jxlLink2Days,
                          Integer jxlLink1Days, Integer jxlLink2Num, Integer jxlLink1Num,
                          Integer jxlLink2Order, Integer jxlLink1Order, Integer jxlGjTs,
                          Integer jxlHtPhoneNum, Integer jxlAmthNum, Integer jxlPhoneRegDays,
                          String jxlDetail, Integer jxlTotal, BigDecimal jxlGjBl,
                          Integer jxlLxGjTs) {
		super();
		this.userId = userId;
		this.jxlZjDkNum = jxlZjDkNum;
		this.jxlBjDkNum = jxlBjDkNum;
		this.jxlYjHf = jxlYjHf;
		this.jxlLink2Days = jxlLink2Days;
		this.jxlLink1Days = jxlLink1Days;
		this.jxlLink2Num = jxlLink2Num;
		this.jxlLink1Num = jxlLink1Num;
		this.jxlLink2Order = jxlLink2Order;
		this.jxlLink1Order = jxlLink1Order;
		this.jxlGjTs = jxlGjTs;
		this.jxlHtPhoneNum = jxlHtPhoneNum;
		this.jxlAmthNum = jxlAmthNum;
		this.jxlPhoneRegDays = jxlPhoneRegDays;
		this.jxlDetail = jxlDetail;
		this.jxlTotal = jxlTotal;
		this.jxlGjBl = jxlGjBl;
		this.jxlLxGjTs = jxlLxGjTs;
	}

	/**
	 * 更新接口状态专用，宜信免费，每次都认为是成功，即使每天次数超限，也返回成功，此处不需要更新
	 * 
	 * @param id
	 * @param zmStatus
	 * @param tdReportId
	 * @param tdStatus
	 * @param bqsStatus
	 * @param jyStatus
	 * @param mgStatus
	 */
	public RiskCreditUser(Integer id, Integer zmStatus, String tdReportId,
                          Integer tdStatus, Integer bqsStatus, Integer jyStatus,
                          Integer mgStatus) {
		super();
		this.id = id;
		this.zmStatus = zmStatus;
		this.tdReportId = tdReportId;
		this.tdStatus = tdStatus;
		this.bqsStatus = bqsStatus;
		this.jyStatus = jyStatus;
		this.mgStatus = mgStatus;
	}

	/**
	 * 更新机审结果专用
	 * 
	 * @param id
	 *            主键ID
	 * @param riskStatus
	 *            机审状态
	 * @param riskRemark
	 *            机审备注
	 */
	public RiskCreditUser(Integer id, Integer riskStatus, String riskRemark) {
		super();
		this.id = id;
		this.riskStatus = riskStatus;
		this.riskRemark = riskRemark;
	}

	/**
	 * 反欺诈专用
	 * 
	 * @param id
	 *            主键
	 * @param presentAddNum
	 *            常用地址重复次数
	 * @param companyAddNum
	 *            公司地址重复次数
	 * @param manyDeviceLoginNum
	 *            多设备登录同一个账号次数
	 * @param oneDeviceRegManyPhoneNum
	 *            一个设备注册多个用户次数
	 * @param companyNameNum
	 *            单位名称出现次数
	 * @param companyPhoneDiffNameOrAdd
	 *            同一个单位电话关联不同单位名称或地址的次数
	 * @param companyAddDiffNameOrPhone
	 *            同一个单位地址关联不同单位号码或者名称
	 * @param companyNameDiffAddOrPhone
	 *            同一个单位名称关联不同地址或电话
	 * @param concatApplyNum
	 *            联系人申请借款数
	 */
	public RiskCreditUser(Integer id, Integer presentAddNum,
                          Integer companyAddNum, Integer manyDeviceLoginNum,
                          Integer oneDeviceRegManyPhoneNum, Integer companyNameNum,
                          Integer companyPhoneDiffNameOrAdd,
                          Integer companyAddDiffNameOrPhone,
                          Integer companyNameDiffAddOrPhone, Integer concatApplyNum) {
		super();
		this.id = id;
		this.presentAddNum = presentAddNum;
		this.companyAddNum = companyAddNum;
		this.manyDeviceLoginNum = manyDeviceLoginNum;
		this.oneDeviceRegManyPhoneNum = oneDeviceRegManyPhoneNum;
		this.companyNameNum = companyNameNum;
		this.companyPhoneDiffNameOrAdd = companyPhoneDiffNameOrAdd;
		this.companyAddDiffNameOrPhone = companyAddDiffNameOrPhone;
		this.companyNameDiffAddOrPhone = companyNameDiffAddOrPhone;
		this.concatApplyNum = concatApplyNum;
	}

	/**
	 * 宜信专用
	 * 
	 * @param userId
	 *            用户主键
	 * @param yxFxNum
	 *            风险记录数
	 * @param yxMonth3Over
	 *            逾期超过三个月的次数
	 * @param yxLessMonth3Over
	 *            逾期未超过三个月次数
	 * @param yxStatus
	 *            宜信接口状态
	 */
	public RiskCreditUser(Integer userId, Integer yxFxNum,
                          Integer yxMonth3Over, Integer yxLessMonth3Over, Integer yxStatus) {
		super();
		this.userId = userId;
		this.yxFxNum = yxFxNum;
		this.yxMonth3Over = yxMonth3Over;
		this.yxLessMonth3Over = yxLessMonth3Over;
		this.yxStatus = yxStatus;
	}

}
