package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name = "directions", schema = "public", catalog = "billing")
public class Direction {
    private int id;
    private String description;
    private String prefix;
    private String additionalKode;
    private String trunkgroup;
    private String status;
    private TariffZone tariffZone;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "prefix")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "additional_kode")
    public String getAdditionalKode() {
        return additionalKode;
    }

    public void setAdditionalKode(String additionalKode) {
        this.additionalKode = additionalKode;
    }

    @Basic
    @Column(name = "trunkgroup")
    public String getTrunkgroup() {
        return trunkgroup;
    }

    public void setTrunkgroup(String trunkgroup) {
        this.trunkgroup = trunkgroup;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direction that = (Direction) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        if (trunkgroup != null ? !trunkgroup.equals(that.trunkgroup) : that.trunkgroup != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (trunkgroup != null ? trunkgroup.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "tarif_zone", referencedColumnName = "zone_id")
    public TariffZone getTariffZone() {
        return tariffZone;
    }

    public void setTariffZone(TariffZone tarifZone) {
        this.tariffZone = tarifZone;
    }
}
