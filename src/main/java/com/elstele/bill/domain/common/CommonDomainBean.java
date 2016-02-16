package com.elstele.bill.domain.common;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import com.elstele.bill.utils.Enums.Status;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class CommonDomainBean implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id", columnDefinition = "serial")
    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DiffIgnore
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    public CommonDomainBean(){
        setStatus(Status.ACTIVE);
    }

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
