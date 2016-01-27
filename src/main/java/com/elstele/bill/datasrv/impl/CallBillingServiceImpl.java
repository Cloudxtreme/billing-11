package com.elstele.bill.datasrv.impl;


import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.dao.interfaces.CallBillingDAO;
import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.exceptions.DirectionCallException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class CallBillingServiceImpl implements CallBillingService {

    @Autowired
    private CallDAO callDAO;

    @Autowired
    private CallBillingDAO billingDAO;

    final static Logger log = LogManager.getLogger(CallBillingServiceImpl.class);

    @Transactional
    public void updateCallWithItCost(Integer callId) throws DirectionCallException {

        Call currentCall = callDAO.getById(callId);

        CallDirection direction =  billingDAO.getCallDirection(currentCall.getNumberB());
        if (direction.getPref_profile() != null) {
            List<CallBillRule> billRules = billingDAO.getCallBillingRule(direction.getPref_profile());
            recalculateCallDurationForSomePrefixes(currentCall);
            calculateCallCost(currentCall, billRules);
            correctCostByVatAndUsdRate(currentCall, direction);
            callDAO.update(currentCall);
        } else {
            throw new DirectionCallException("there is no direction for call with ID:" + currentCall.getId() + " with numB:" + currentCall.getNumberB());
        }
    }

    private void correctCostByVatAndUsdRate(Call currentCall, CallDirection direction) {
        currentCall.setCallDirectionId(direction.getId());
        if (direction.isDollar()){
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentCall.getStartTime());
            /*int year = cal.get(Calendar.YEAR);
            int callStartMonth = cal.get(Calendar.MONTH);
            int callStartDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            String strDate = year + "-" + callStartMonth + "-" + callStartDayOfMonth;*/
            float usdRate = billingDAO.getUsdRateForCall(cal.getTime());
            float curCost = currentCall.getCostTotal();
            currentCall.setCostTotal((float) (curCost*1.2*usdRate));
        } else {
            float curCost = currentCall.getCostTotal();
            currentCall.setCostTotal((float) (curCost*1.2));
        }
    }

    private void recalculateCallDurationForSomePrefixes(Call currentCall) {
        Integer newDuration = currentCall.getDuration();
        if (currentCall.getNumberB().startsWith("0900")){
            Integer curDuration = currentCall.getDuration();
            newDuration = (Integer)(curDuration/12);
        }
        if (currentCall.getNumberB().startsWith("0703")){
            Integer curDuration = currentCall.getDuration();
            newDuration = (Integer)(curDuration/12);
        }
        currentCall.setDuration(newDuration);
    }

    private void calculateCallCost(Call currentCall, List<CallBillRule> billRules) {
        float callCostAbs = calculateCallCostAbs(currentCall, billRules);

        currentCall.setSecRegular(currentCall.getDuration().intValue());
        currentCall.setSecPref(0);
        currentCall.setCostRegular(callCostAbs);
        currentCall.setCostPref(0f);
        currentCall.setCostTotal(callCostAbs);

    }

    private float calculateCallCostAbs(Call currentCall, List<CallBillRule> billRules) {
        float callCostAbs = 0f;

        //Some values here
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentCall.getStartTime());
        int year = cal.get(Calendar.YEAR);
        int callStartMonth = cal.get(Calendar.MONTH);
        int callStartDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int callStartDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int curYear = Calendar.getInstance().get(Calendar.YEAR);

        long unixCallStartTime = cal.getTimeInMillis()/1000;
        long unixCallFinishTime = unixCallStartTime + currentCall.getDuration();


        for (CallBillRule curRule : billRules){
            int ruleDayOfmonth = calculateRuleDayOfMonth(curRule);
            int ruleMonth = calculateRuleMonth(curRule);
            int ruleDayOfWeek = calculateRuleDayOfWeek(curRule);

            // target is day of month
            if (ruleDayOfmonth == callStartDayOfMonth && ruleMonth == callStartMonth){
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,ruleMonth,ruleDayOfmonth, 0, 0, 0);
                long dayStartTimestamp = calendar.getTimeInMillis()/1000;
                calendar.set(year,ruleMonth,ruleDayOfmonth, 23, 59, 59);
                long dayFinishTimestamp = calendar.getTimeInMillis()/1000;


                if ((unixCallStartTime >= dayStartTimestamp) && (unixCallFinishTime <= dayFinishTimestamp)){
                    long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                    callCostAbs = preferenceSeconds * curRule.getTarif();
                    break;
                }

            }

            // target day of week (analyse call start)
            if (ruleDayOfWeek != 0 && ruleDayOfWeek == callStartDayOfMonth){
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,callStartMonth,callStartDayOfMonth, 0, 0, 0);
                long dayStartTimestamp = calendar.getTimeInMillis()/1000;
                calendar.set(year,callStartMonth,callStartDayOfMonth, 23, 59, 59);
                long dayFinishTimestamp = calendar.getTimeInMillis()/1000;

                if ((unixCallFinishTime > dayStartTimestamp) && (unixCallStartTime < dayFinishTimestamp)){
                    if ((unixCallStartTime >= dayStartTimestamp) && (unixCallStartTime <= dayFinishTimestamp)){

                        long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                        callCostAbs = preferenceSeconds * curRule.getTarif();
                        break; // we find all period
                    }
                }
            }

            // no day of week
            if (ruleDayOfWeek == 0){
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,callStartMonth,callStartDayOfMonth, 0, 0, 0);
                long dayStartTimestamp = calendar.getTimeInMillis()/1000;
                calendar.set(year,callStartMonth,callStartDayOfMonth, 23, 59, 59);
                long dayFinishTimestamp = calendar.getTimeInMillis()/1000;

                if ((unixCallFinishTime > dayStartTimestamp) && (unixCallStartTime < dayFinishTimestamp)){
                    if ((unixCallStartTime >= dayStartTimestamp) && (unixCallStartTime <= dayFinishTimestamp)){

                        long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                        callCostAbs = preferenceSeconds * curRule.getTarif();
                        break; // we find all period
                    }
                }
            }
        }

        return callCostAbs;
    }

    private int calculateRuleDayOfWeek(CallBillRule curRule) {
        if (curRule.getDayofweek() == null || curRule.getDayofweek().isEmpty()){
            return 0;
        }
        return Integer.parseInt(curRule.getDayofweek());
    }

    private int calculateRuleMonth(CallBillRule curRule) {
        if (curRule.getMonth() == null || curRule.getMonth().isEmpty()){
            return 0;
        }
        return Integer.parseInt(curRule.getMonth());
    }

    private int calculateRuleDayOfMonth(CallBillRule curRule) {
        if (curRule.getDayOfMonth() == null || curRule.getDayOfMonth().isEmpty()){
            return 0;
        }
        return Integer.parseInt(curRule.getDayOfMonth());
    }


}
