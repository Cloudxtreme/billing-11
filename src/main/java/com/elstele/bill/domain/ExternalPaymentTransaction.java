package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by ivan on 15/12/26.
 */
@Entity
@Table(name = "payments")
public class ExternalPaymentTransaction extends CommonDomainBean {
    @Column(name = "service_id")
    private String serviceId;
    @Column(name = "pay_account")
    private String payAccount;
    @Column(name = "pay_amount")
    private Float payAmount;
    @Column(name = "receipt_num")
    private String receiptNum;
    @Column(name = "pay_id")
    private String payId;
    private String tradepoint;
    private Date timestamp;
    @Column(name = "`check`")
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
}
