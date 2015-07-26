package com.elstele.bill.form;


import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Status;

public class AccountForm {
    private Integer id;
    private Status status;
    private String accountName;
    private Constants.AccountType accountType;
    private Float currentBalance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Constants.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(Constants.AccountType accountType) {
        this.accountType = accountType;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountForm)) return false;

        AccountForm that = (AccountForm) o;

        if (!accountName.equals(that.accountName)) return false;
        if (accountType != that.accountType) return false;
        if (!currentBalance.equals(that.currentBalance)) return false;
        if (!id.equals(that.id)) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + accountName.hashCode();
        result = 31 * result + accountType.hashCode();
        result = 31 * result + currentBalance.hashCode();
        return result;
    }
}
