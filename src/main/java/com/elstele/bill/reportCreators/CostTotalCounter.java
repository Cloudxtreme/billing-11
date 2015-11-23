package com.elstele.bill.reportCreators;

import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CostTotalCounter {
    final public static Logger log = LogManager.getLogger(CostTotalCounter.class);

    public static Float countForTO(List<CallTO> callsListByNumberA) {
        Float costTotalForPeriod = 0.0f;
        for (CallTO callTO : callsListByNumberA) {
            costTotalForPeriod += callTO.getCosttotal();
        }
        return costTotalForPeriod;
    }

    public static Float countForCSV(List<CallForCSV> callForCSVByNumberA) {
        Float costTotalForPeriod = 0.0f;
        for (CallForCSV callForCSV : callForCSVByNumberA) {
            costTotalForPeriod += Float.parseFloat(callForCSV.getCostCallTotal());
        }
        return costTotalForPeriod;
    }

    public static Float countDurationForCall(List<Call> callForCSVByNumberA) {
        Float costTotalForPeriod = 0.0f;
        for (Call call : callForCSVByNumberA) {
            costTotalForPeriod += call.getDuration();
        }
        return costTotalForPeriod;
    }

    public static Float countLocalForCall(List<Call> callForCSVByNumberA) {
        Float costTotalForPeriod = 0.0f;
        Float totalLocalCallsCost = 0.0f;
        for (Call call : callForCSVByNumberA) {
            costTotalForPeriod += call.getDuration();
        }
        if (costTotalForPeriod > 36000) {
            totalLocalCallsCost = (costTotalForPeriod - 36000f) * 0.0009f;
        }
        return totalLocalCallsCost;
    }


}
