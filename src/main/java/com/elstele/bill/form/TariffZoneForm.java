package com.elstele.bill.form;

public class TariffZoneForm {
    private int id;
    private int zoneId;
    private String zoneName;
    private String additionalKode;
    private boolean dollar;
    private int prefProfile;
    private float tarif;
    private Float tarifPref;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAdditionalKode() {
        return additionalKode;
    }

    public void setAdditionalKode(String additionalKode) {
        this.additionalKode = additionalKode;
    }

    public boolean isDollar() {
        return dollar;
    }

    public void setDollar(boolean dollar) {
        this.dollar = dollar;
    }

    public int getPrefProfile() {
        return prefProfile;
    }

    public void setPrefProfile(int prefProfile) {
        this.prefProfile = prefProfile;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public Float getTarifPref() {
        return tarifPref;
    }

    public void setTarifPref(Float tarifPref) {
        this.tarifPref = tarifPref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TariffZoneForm)) return false;

        TariffZoneForm that = (TariffZoneForm) o;

        if (id != that.id) return false;
        if (zoneId != that.zoneId) return false;
        if (dollar != that.dollar) return false;
        if (prefProfile != that.prefProfile) return false;
        if (Float.compare(that.tarif, tarif) != 0) return false;
        if (zoneName != null ? !zoneName.equals(that.zoneName) : that.zoneName != null) return false;
        if (additionalKode != null ? !additionalKode.equals(that.additionalKode) : that.additionalKode != null)
            return false;
        return !(tarifPref != null ? !tarifPref.equals(that.tarifPref) : that.tarifPref != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + zoneId;
        result = 31 * result + (zoneName != null ? zoneName.hashCode() : 0);
        result = 31 * result + (additionalKode != null ? additionalKode.hashCode() : 0);
        result = 31 * result + (dollar ? 1 : 0);
        result = 31 * result + prefProfile;
        result = 31 * result + (tarif != +0.0f ? Float.floatToIntBits(tarif) : 0);
        result = 31 * result + (tarifPref != null ? tarifPref.hashCode() : 0);
        return result;
    }
}
