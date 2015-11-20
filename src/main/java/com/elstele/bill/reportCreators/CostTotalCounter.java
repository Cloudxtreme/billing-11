package com.elstele.bill.reportCreators;

import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CostTotalCounter {
    private Double costTotalForPeriod = 0.0;
    private Double totalLocalCallsCost = 0.0;
    final public static Logger log = LogManager.getLogger(CostTotalCounter.class);


    public Double countForTO(List<CallTO> callsListByNumberA) {
        try {
            for (CallTO callTO : callsListByNumberA) {
                Double costTotal = (double) callTO.getCosttotal();
                costTotalForPeriod += costTotal;
            }
            return costTotalForPeriod;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForPeriod;
        }
    }

    public Double countForCSV(List<CallForCSV> callForCSVByNumberA) {
        try {
            for (CallForCSV callForCSV : callForCSVByNumberA) {
                Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
                costTotalForPeriod += costTotal;
            }
            return costTotalForPeriod;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForPeriod;
        }
    }

    public Double countForCall(List<Call> callForCSVByNumberA) {
        try {
            for (Call call : callForCSVByNumberA) {
                costTotalForPeriod += call.getDuration();
            }
            return costTotalForPeriod;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForPeriod;
        }
    }

    public Double countLocalForCall(List<Call> callForCSVByNumberA) {
        try {
            for (Call call : callForCSVByNumberA) {
                costTotalForPeriod += call.getDuration();
            }
            if (costTotalForPeriod > 36000) {
                totalLocalCallsCost = (costTotalForPeriod - 36000) * 0.0009;
            }
            return totalLocalCallsCost;
        } catch (RuntimeException e) {
            log.error(e + " Method = countForTO");
            return costTotalForPeriod;
        }
    }



}
