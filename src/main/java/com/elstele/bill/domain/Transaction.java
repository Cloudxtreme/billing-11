package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Transaction")
public class Transaction extends CommonDomainBean {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!account.equals(that.account)) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (!date.equals(that.date)) return false;
        if (direction != that.direction) return false;
        if (!price.equals(that.price)) return false;
        if (source != that.source) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
