package com.elstele.bill.form;

import com.elstele.bill.utils.Constants.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import java.util.Date;

public class TransactionForm {
    private Integer id;
    private AccountForm account;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    private Constants.TransactionDirection direction;
    private Constants.TransactionSource source;
    private Float price;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountForm getAccount() {
        return account;
    }

    public void setAccount(AccountForm account) {
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
