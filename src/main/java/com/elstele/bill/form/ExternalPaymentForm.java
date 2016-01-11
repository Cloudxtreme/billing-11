package com.elstele.bill.form;

import java.util.Date;

/**
 * Created by ivan on 15/12/27.
 */
public class ExternalPaymentForm {
    private Integer id;
    private String serviceId;
    private String payAccount;
    private Float payAmount;
    private String receiptNum;
    private String payId;
    private String tradepoint;
    private Date timestamp;
    private Boolean check;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Float getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Float payAmount) {
        this.payAmount = payAmount;
    }

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getTradepoint() {
        return tradepoint;
    }

    public void setTradepoint(String tradepoint) {
        this.tradepoint = tradepoint;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
