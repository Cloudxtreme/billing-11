package com.elstele.bill.domain.common;

import com.elstele.bill.utils.Status;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class CommonDomainBean implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Status status;


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
