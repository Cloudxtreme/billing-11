package com.elstele.bill.form;

import com.elstele.bill.utils.Enums.Status;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExternalPaymentForm)) return false;

        ExternalPaymentForm that = (ExternalPaymentForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
        if (payAccount != null ? !payAccount.equals(that.payAccount) : that.payAccount != null) return false;
        if (payAmount != null ? !payAmount.equals(that.payAmount) : that.payAmount != null) return false;
        if (receiptNum != null ? !receiptNum.equals(that.receiptNum) : that.receiptNum != null) return false;
        if (payId != null ? !payId.equals(that.payId) : that.payId != null) return false;
        if (tradepoint != null ? !tradepoint.equals(that.tradepoint) : that.tradepoint != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        return !(check != null ? !check.equals(that.check) : that.check != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (payAccount != null ? payAccount.hashCode() : 0);
        result = 31 * result + (payAmount != null ? payAmount.hashCode() : 0);
        result = 31 * result + (receiptNum != null ? receiptNum.hashCode() : 0);
        result = 31 * result + (payId != null ? payId.hashCode() : 0);
        result = 31 * result + (tradepoint != null ? tradepoint.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (check != null ? check.hashCode() : 0);
        return result;
    }
}
