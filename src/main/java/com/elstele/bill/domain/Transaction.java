package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Transaction")
public class Transaction extends CommonDomainBean {
    @OneToOne(fetch = FetchType.EAGER)
    private Account account;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    @Enumerated(EnumType.STRING)
    private Constants.TransactionDirection direction;

    @Enumerated(EnumType.STRING)
    private Constants.TransactionSource source;

    private Float price;
    private String comment;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Constants.TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(Constants.TransactionDirection direction) {
        this.direction = direction;
    }

    public Constants.TransactionSource getSource() {
        return source;
    }

    public void setSource(Constants.TransactionSource source) {
        this.source = source;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
