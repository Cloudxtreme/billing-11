package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "directions", schema = "public")
public class Direction extends CommonDomainBean {
    @Basic
    @Column(name = "description", length = 100)
    private String description;
    @Basic
    @Column(name = "prefix", length = 20)
    private String prefix;
    @Basic
    @Column(name = "tarif_zone")
    private Integer tarifZone;
    @Basic
    @Column(name = "additional_kode")
    private String additionalKode;
    @Basic
    @Column(name = "trunkgroup")
    private String trunkgroup;

    private Date validFrom;
    private Date validTo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public Integer getTarifZone() {
        return tarifZone;
    }

    public void setTarifZone(Integer tarifZone) {
        this.tarifZone = tarifZone;
    }


    public String getAdditionalKode() {
        return additionalKode;
    }

    public void setAdditionalKode(String additionalKode) {
        this.additionalKode = additionalKode;
    }


    public String getTrunkgroup() {
        return trunkgroup;
    }

    public void setTrunkgroup(String trunkgroup) {
        this.trunkgroup = trunkgroup;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direction)) return false;

        Direction direction = (Direction) o;

        if (description != null ? !description.equals(direction.description) : direction.description != null)
            return false;
        if (prefix != null ? !prefix.equals(direction.prefix) : direction.prefix != null) return false;
        if (tarifZone != null ? !tarifZone.equals(direction.tarifZone) : direction.tarifZone != null) return false;
        if (additionalKode != null ? !additionalKode.equals(direction.additionalKode) : direction.additionalKode != null)
            return false;
        if (trunkgroup != null ? !trunkgroup.equals(direction.trunkgroup) : direction.trunkgroup != null) return false;
        if (validFrom != null ? !validFrom.equals(direction.validFrom) : direction.validFrom != null) return false;
        return !(validTo != null ? !validTo.equals(direction.validTo) : direction.validTo != null);

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (tarifZone != null ? tarifZone.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (trunkgroup != null ? trunkgroup.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }

}

