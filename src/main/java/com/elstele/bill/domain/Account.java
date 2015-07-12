package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="Accounts")
public class Account extends CommonDomainBean {

    private String accountName;
    @Enumerated(EnumType.STRING)
    private Constants.AccountType accountType;

    private Float currentBalance;

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
}
