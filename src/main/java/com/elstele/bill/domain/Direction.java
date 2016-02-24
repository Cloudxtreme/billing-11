package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;

@Entity
@Table(name = "directions", schema = "public", catalog = "billing")
public class Direction extends CommonDomainBean {
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "prefix")
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




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direction that = (Direction) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (tarifZone != null ? !tarifZone.equals(that.tarifZone) : that.tarifZone != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        if (trunkgroup != null ? !trunkgroup.equals(that.trunkgroup) : that.trunkgroup != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (tarifZone != null ? tarifZone.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (trunkgroup != null ? trunkgroup.hashCode() : 0);
        return result;
    }
}
