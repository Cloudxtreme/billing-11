package com.elstele.bill.reportCreators;

import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CostTotalCounter {
    private Double costTotalForThisNumber = 0.0;
    final public static Logger log = LogManager.getLogger(CostTotalCounter.class);


    public Double countForTO(List<CallTO> callsListByNumberA) {
        try {
            for (CallTO callTO : callsListByNumberA) {
                Double costTotal = (double) callTO.getCosttotal();
                costTotalForThisNumber += costTotal;
            }
            return costTotalForThisNumber;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForThisNumber;
        }
    }

    public Double countForCSV(List<CallForCSV> callForCSVByNumberA) {
        try {
            for (CallForCSV callForCSV : callForCSVByNumberA) {
                Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
                costTotalForThisNumber += costTotal;
            }
            return costTotalForThisNumber;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForThisNumber;
        }
    }


    public Double costTotalForThisNumberOperation(List<CallForCSV> callListByNumberA) {
        try {
            for (CallForCSV callForCSV : callListByNumberA) {
                Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
                costTotalForThisNumber += costTotal;
            }
            return costTotalForThisNumber;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForThisNumber;
        }
    }
}
