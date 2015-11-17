package com.elstele.bill.reportCreators;

import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTO;

import java.util.List;

public class CostTotalCounter {
    private  Double costTotalForThisNumber = 0.0;

    public  Double countForTO(List<CallTO> callsListByNumberA) {
        for (CallTO callTO : callsListByNumberA) {
            Double costTotal = (double)callTO.getCosttotal();
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public  Double countForCSV(List<CallForCSV> callForCSVByNumberA) {
        for (CallForCSV callForCSV : callForCSVByNumberA) {
            Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }


    public Double costTotalForThisNumberOperation(List<CallForCSV> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallForCSV callForCSV : callListByNumberA) {
            Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

}
