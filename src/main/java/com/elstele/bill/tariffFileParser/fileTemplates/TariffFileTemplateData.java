package com.elstele.bill.tariffFileParser.fileTemplates;

import java.util.Date;
import java.util.List;

public class TariffFileTemplateData {
    private String directionName;
    private String countryPrefix;
    private List<String> networkPrefixesList;
    private Float tariff;
    private Date validFrom;
    private Date validTo;
    private int profileId;
    private int zoneId;

    public TariffFileTemplateData(String[] lineFromFile, Date validFrom, Date validTo, List<String> networkPrefixesList) {
        this.directionName = lineFromFile[0];
        this.countryPrefix = lineFromFile[1];
        this.tariff = Float.parseFloat(lineFromFile[3].replaceAll(",", ".")) / 60;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.networkPrefixesList = networkPrefixesList;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getCountryPrefix() {
        return countryPrefix;
    }

    public void setCountryPrefix(String countryPrefix) {
        this.countryPrefix = countryPrefix;
    }

    public List<String> getNetworkPrefixesList() {
        return networkPrefixesList;
    }

    public void setNetworkPrefixesList(List<String> networkPrefixesList) {
        this.networkPrefixesList = networkPrefixesList;
    }

    public Float getTariff() {
        return tariff;
    }

    public void setTariff(Float tariff) {
        this.tariff = tariff;
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

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }
}
