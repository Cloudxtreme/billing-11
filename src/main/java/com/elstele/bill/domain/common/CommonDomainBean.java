package com.elstele.bill.domain.common;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class CommonDomainBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Status status;

    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
