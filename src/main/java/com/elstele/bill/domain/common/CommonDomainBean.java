package com.elstele.bill.domain.common;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import com.elstele.bill.utils.Enums.Status;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.ShallowReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@ShallowReference
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

    //TODO uncomment this fields when DB will be ready
    /*@Column(length = 40)
    private String updatedBy;

    @Column(length = 40)
    private String createdBy;

    private Date createDate;

    private Date updateDate;
*/
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


    //TODO uncomment this when DB tables will be ready
    /*public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    */
}
