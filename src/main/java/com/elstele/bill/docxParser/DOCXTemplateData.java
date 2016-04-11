package com.elstele.bill.docxParser;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DOCXTemplateData {
    private String directionName;
    private String countryPrefix;
    private List<String> networkPrefixesList;
    private Float tariff;
    private Date validFrom;
    private Date validTo;
    private int profileId;
    private int zoneId;

    public DOCXTemplateData(XWPFTableRow row, Date validFrom, Date validTo) {
        this.directionName = row.getCell(0).getText();
        this.countryPrefix = row.getCell(1).getText();
        this.tariff = Float.parseFloat(row.getCell(3).getText().replaceAll(",", ".")) / 60;
        this.validFrom = validFrom;
        this.validTo = validTo;
        parseNetworkPrefixes(row);
    }

    private void parseNetworkPrefixes(XWPFTableRow row) {
        networkPrefixesList = new ArrayList<>();
        String[] prefArr = row.getCell(2).getText().split(",");
        for (String string : prefArr) {
            if (!string.contains("-")) {
                networkPrefixesList.add(string.replaceAll("(^\\h*)|(\\h*$)", ""));
            } else {
                String[] prefEndDiapasons = string.split("-");
                int startDiapason = Integer.parseInt(prefEndDiapasons[0].replaceAll("\\s+", ""));
                int endDiapason = Integer.parseInt(prefEndDiapasons[1].replaceAll("\\s+", ""));
                for (int i = startDiapason; i <= endDiapason; i++) {
                    networkPrefixesList.add(Integer.toString(i));
                }
            }
        }
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
