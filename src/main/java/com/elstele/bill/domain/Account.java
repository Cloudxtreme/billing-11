package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDef(name="showActive", parameters={@ParamDef( name="exclude", type="string" )})
@Table(name="Accounts")
public class Account extends CommonDomainBean {

    private String accountName;
    @Enumerated(EnumType.STRING)
    private Constants.AccountType accountType;
    private Float currentBalance;
    @Column(name = "user_fio")
    private String fio;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address phyAddress;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address legalAddress;


    @OneToMany(mappedBy="account")
    @LazyCollection(LazyCollectionOption.TRUE)
    @Filter(name="showActive", condition="status != :exclude")
    private Set<Service> accountServices = new HashSet<Service>(0);



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

    public Address getPhyAddress() {
        return phyAddress;
    }

    public void setPhyAddress(Address phyAddress) {
        this.phyAddress = phyAddress;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(Address legalAddress) {
        this.legalAddress = legalAddress;
    }

    public Set<Service> getAccountServices() {
        return accountServices;
    }

    public void setAccountServices(Set<Service> accountServices) {
        this.accountServices = accountServices;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        if (getStatus() != account.getStatus()) return false;
        if (accountName != null ? !accountName.equals(account.accountName) : account.accountName != null) return false;
        if (accountType != account.accountType) return false;
        if (currentBalance != null ? !currentBalance.equals(account.currentBalance) : account.currentBalance != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountName != null ? accountName.hashCode() : 0;
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (currentBalance != null ? currentBalance.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);


        return result;
    }
}
