package com.elstele.bill.form;

import com.elstele.bill.utils.Constants;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionForm that = (TransactionForm) o;

        if (!account.getId().equals(that.account.getId())) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (date!=null ? !date.equals(that.date) : that.date != null) return false;
        if (direction != that.direction) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!price.equals(that.price)) return false;
        if (source != that.source) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + account.getId().hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
