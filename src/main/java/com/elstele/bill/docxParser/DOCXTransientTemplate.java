package com.elstele.bill.docxParser;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DOCXTransientTemplate {
    private String directionName;
    private String countryPrefix;
    private List<String> networkPrefixesList;
    private Float tariff;
    private Date validateFrom;

    public DOCXTransientTemplate(XWPFTableRow row, Date validateFrom) {
        this.directionName = row.getCell(0).getText();
        this.countryPrefix = row.getCell(1).getText();
        this.tariff = Float.parseFloat(row.getCell(3).getText().replaceAll(",",".")) / 60;
        this.validateFrom = validateFrom;
        parsePrefixEndPart(row);
    }

    private void parsePrefixEndPart(XWPFTableRow row) {
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

    public Date getValidateFrom() {
        return validateFrom;
    }

    public void setValidateFrom(Date validateFrom) {
        this.validateFrom = validateFrom;
    }
}
