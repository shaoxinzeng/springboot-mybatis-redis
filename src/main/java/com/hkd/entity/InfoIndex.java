package com.hkd.entity;

import javax.persistence.Table;
import java.util.List;

@Table(name = "info_index")
public class InfoIndex {

    private Integer id;//--
    private String card_title;//标题--
    private String card_amount;//借款金额--
    private String card_verify_step;//认证状态
    private String periods;//借款期数
    private List<String> interests;//到账、佣金金额
    private List<String> amounts;//金额列表
    private List<InfoNotice> user_loan_log_list;//公告
    private String today_last_amount;//奖池--

    private String auth_max;
    private String auth_min;
    private String amount_max;
    private String amount_min;
    private String notice_size;
    private String amount;//--
    private String service_amount;//--
    private String rate;//--
    private String message_url;//--
    private String bonus_url;//--
    private String borrow_url;//--
    private String status;//--

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCard_title() {
        return card_title;
    }

    public void setCard_title(String cardTitle) {
        card_title = cardTitle;
    }

    public String getCard_amount() {
        return card_amount;
    }

    public void setCard_amount(String cardAmount) {
        card_amount = cardAmount;
    }

    public String getCard_verify_step() {
        return card_verify_step;
    }

    public void setCard_verify_step(String cardVerifyStep) {
        card_verify_step = cardVerifyStep;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<String> amounts) {
        this.amounts = amounts;
    }

    public List<InfoNotice> getUser_loan_log_list() {
        return user_loan_log_list;
    }

    public void setUser_loan_log_list(List<InfoNotice> userLoanLogList) {
        user_loan_log_list = userLoanLogList;
    }

    public String getToday_last_amount() {
        return today_last_amount;
    }

    public void setToday_last_amount(String todayLastAmount) {
        today_last_amount = todayLastAmount;
    }

    public String getAmount_min() {
        return amount_min;
    }

    public void setAmount_min(String amountMin) {
        amount_min = amountMin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getService_amount() {
        return service_amount;
    }

    public void setService_amount(String serviceAmount) {
        service_amount = serviceAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMessage_url() {
        return message_url;
    }

    public void setMessage_url(String messageUrl) {
        message_url = messageUrl;
    }

    public String getBonus_url() {
        return bonus_url;
    }

    public void setBonus_url(String bonusUrl) {
        bonus_url = bonusUrl;
    }

    public String getBorrow_url() {
        return borrow_url;
    }

    public void setBorrow_url(String borrowUrl) {
        borrow_url = borrowUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount_max() {
        return amount_max;
    }

    public void setAmount_max(String amountMax) {
        amount_max = amountMax;
    }

    public String getAuth_max() {
        return auth_max;
    }

    public void setAuth_max(String authMax) {
        auth_max = authMax;
    }

    public String getAuth_min() {
        return auth_min;
    }

    public void setAuth_min(String authMin) {
        auth_min = authMin;
    }

    public String getNotice_size() {
        return notice_size;
    }

    public void setNotice_size(String noticeSize) {
        notice_size = noticeSize;
    }

    @Override
    public String toString() {
        return "InfoIndex [amount=" + amount + ", amount_min=" + amount_min
                + ", amounts=" + amounts + ", bonus_url=" + bonus_url
                + ", borrow_url=" + borrow_url + ", card_amount=" + card_amount
                + ", card_title=" + card_title + ", card_verify_step="
                + card_verify_step
                + ", id=" + id + ", interests=" + interests + ", message_url="
                + message_url + ", rate=" + rate + ", service_amount="
                + service_amount + ", status=" + status
                + ", today_last_amount=" + today_last_amount
                + ", user_loan_log_list=" + user_loan_log_list + "]";
    }

}
