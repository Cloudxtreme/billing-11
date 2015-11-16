package com.elstele.bill.reportCreators;

import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTransformerDir;

import java.util.List;

public class CostTotalCounter {
    private Double costTotalForThisNumber = 0.0;
    private List<CallTransformerDir> callListByNumberA;

    public CostTotalCounter(List<CallTransformerDir> callListByNumberA) {
        this.callListByNumberA = callListByNumberA;
    }

    public Double count() {
        for (CallTransformerDir callTransformerDir : callListByNumberA) {
            Double costTotal = (double) callTransformerDir.getCosttotal();
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

    public Double costTotalForThisCallNumberOperation(List<CallTransformerDir> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallTransformerDir call : callListByNumberA) {
            Double costTotal = (double) call.getCosttotal();
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }
}
