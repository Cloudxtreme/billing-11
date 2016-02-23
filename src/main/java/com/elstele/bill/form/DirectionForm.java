package com.elstele.bill.form;

import com.elstele.bill.domain.TariffZone;

public class DirectionForm {
    private Integer id;
    private String description;
    private String prefix;
    private TariffZoneForm tariffZone;
    private String additionalKode;
    private String trunkgroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public TariffZoneForm getTariffZone() {
        return tariffZone;
    }

    public void setTariffZone(TariffZoneForm tariffZone) {
        this.tariffZone = tariffZone;
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
        if (!(o instanceof DirectionForm)) return false;

        DirectionForm that = (DirectionForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tariffZone != null ? !tariffZone.equals(that.tariffZone) : that.tariffZone != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        return !(trunkgroup != null ? !trunkgroup.equals(that.trunkgroup) : that.trunkgroup != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (tariffZone != null ? tariffZone.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (trunkgroup != null ? trunkgroup.hashCode() : 0);
        return result;
    }
}
