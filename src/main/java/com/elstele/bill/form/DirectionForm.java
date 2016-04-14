
package com.elstele.bill.form;


import java.util.Date;
import java.util.List;

public class DirectionForm {
    private Integer id;
    private String description;
    private String prefix;
    private List<TariffZoneForm> tariffZoneList;
    private String additionalKode;
    private String trunkgroup;
    private int zoneId;
    private Long validFrom;
    private Long validTo;

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

    public List<TariffZoneForm> getTariffZoneList() {
        return tariffZoneList;
    }

    public void setTariffZoneList(List<TariffZoneForm> tariffZoneList) {
        this.tariffZoneList = tariffZoneList;
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

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public Long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }

    public Long getValidTo() {
        return validTo;
    }

    public void setValidTo(Long validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionForm)) return false;

        DirectionForm that = (DirectionForm) o;

        if (zoneId != that.zoneId) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (tariffZoneList != null ? !tariffZoneList.equals(that.tariffZoneList) : that.tariffZoneList != null)
            return false;
        if (trunkgroup != null ? !trunkgroup.equals(that.trunkgroup) : that.trunkgroup != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (tariffZoneList != null ? tariffZoneList.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (trunkgroup != null ? trunkgroup.hashCode() : 0);
        result = 31 * result + zoneId;
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
