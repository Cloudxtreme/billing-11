package com.elstele.bill.to;


import com.elstele.bill.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TransactionTO {
    private Integer id;
    private Integer accountId;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

        TransactionTO that = (TransactionTO) o;

        if (!accountId.equals(that.accountId)) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (direction != that.direction) return false;
        if (!id.equals(that.id)) return false;
        if (!price.equals(that.price)) return false;
        if (source != that.source) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + accountId.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + price.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
