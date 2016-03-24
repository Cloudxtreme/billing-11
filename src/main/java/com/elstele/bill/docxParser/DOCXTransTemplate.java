package com.elstele.bill.docxParser;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DOCXTransTemplate {
    private String directionName;
    private String prefMainPart;
    private List<String> prefEnder;
    private String tariff;
    private Date validateFrom;

    public DOCXTransTemplate(XWPFTableRow row, Date validateFrom) {
        this.directionName = row.getCell(0).getText();
        this.prefMainPart = row.getCell(1).getText();
        this.tariff = row.getCell(3).getText().replaceAll(",",".");
        this.validateFrom = validateFrom;
        parsePrefixEndPart(row);
    }

    private void parsePrefixEndPart(XWPFTableRow row) {
        prefEnder = new ArrayList<>();
        String[] prefArr = row.getCell(2).getText().split(",");
        for (String string : prefArr) {
            if (!string.contains("-")) {
                prefEnder.add(string.replaceAll("(^\\h*)|(\\h*$)",""));
            } else {
                String[] prefEndDiapasons = string.split("-");
                int startDiapason = Integer.parseInt(prefEndDiapasons[0].replaceAll("\\s+", ""));
                int endDiapason = Integer.parseInt(prefEndDiapasons[1].replaceAll("\\s+", ""));
                for (int i = startDiapason; i <= endDiapason; i++) {
                    prefEnder.add(Integer.toString(i));
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

    public String getPrefMainPart() {
        return prefMainPart;
    }

    public void setPrefMainPart(String prefMainPart) {
        this.prefMainPart = prefMainPart;
    }

    public List<String> getPrefEnder() {
        return prefEnder;
    }

    public void setPrefEnder(List<String> prefEnder) {
        this.prefEnder = prefEnder;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public Date getValidateFrom() {
        return validateFrom;
    }

    public void setValidateFrom(Date validateFrom) {
        this.validateFrom = validateFrom;
    }
}
