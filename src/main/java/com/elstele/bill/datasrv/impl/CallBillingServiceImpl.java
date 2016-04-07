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

    final static Logger LOGGER = LogManager.getLogger(CallBillingServiceImpl.class);

    @Transactional
    public void updateCallWithItCost(Integer callId) throws DirectionCallException {
        Call currentCall = callDAO.getById(callId);
        CallDirection direction = billingDAO.getCallDirection(currentCall.getNumberB(), currentCall.getStartTime());

        if (direction.getPref_profile() != null) {
            List<CallBillRule> billRules = billingDAO.getCallBillingRule(direction.getPref_profile(), currentCall.getStartTime());
            recalculateCallDurationForSomePrefixes(currentCall);
            calculateCallCost(currentCall, billRules);
            correctCostByVatAndUsdRate(currentCall, direction);
            backOriginalCallDuration(currentCall);
            callDAO.update(currentCall);
        } else {
            throw new DirectionCallException("there is no direction for call with ID:" + currentCall.getId() + " with numB:" + currentCall.getNumberB());
        }
    }

    private void backOriginalCallDuration(Call currentCall) {
        if (currentCall.getOriginalDuration() != null) {
            currentCall.setDuration(currentCall.getOriginalDuration());
        }
    }

    private void correctCostByVatAndUsdRate(Call currentCall, CallDirection direction) {
        currentCall.setCallDirectionId(direction.getId());
        if (direction.isDollar()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentCall.getStartTime());
            float usdRate = billingDAO.getUsdRateForCall(cal.getTime());
            float curCost = currentCall.getCostTotal();
            currentCall.setCostTotal((float) (curCost * 1.2 * usdRate));
        } else {
            float curCost = currentCall.getCostTotal();
            currentCall.setCostTotal((float) (curCost * 1.2));
        }
    }

    private void recalculateCallDurationForSomePrefixes(Call currentCall) {
        Integer newDuration = currentCall.getDuration();
        Integer originalDuration = currentCall.getDuration();
        if (currentCall.getNumberB().startsWith("0900")) {
            newDuration = (Integer) (originalDuration / 12);
        }
        if (currentCall.getNumberB().startsWith("0703")) {
            newDuration = (Integer) (originalDuration / 12);
        }
        currentCall.setOriginalDuration(originalDuration);
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
        int callStartDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;  //in DB profiles days of week starts from 0
        int curYear = Calendar.getInstance().get(Calendar.YEAR);

        long unixCallStartTime = cal.getTimeInMillis() / 1000;
        long unixCallFinishTime = unixCallStartTime + currentCall.getDuration();


        for (CallBillRule curRule : billRules) {
            int ruleDayOfmonth = calculateRuleDayOfMonth(curRule);
            int ruleMonth = calculateRuleMonth(curRule);
            int ruleDayOfWeek = calculateRuleDayOfWeek(curRule);

            int[] ruleStartTime = calculateRuleStartTime(curRule);
            int[] ruleFinishTime = calculateRuleFinishTime(curRule);

            // target is day of month
            if (ruleDayOfmonth == callStartDayOfMonth && ruleMonth == callStartMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, ruleMonth, ruleDayOfmonth, 0, 0, 0);
                long dayStartTimestamp = calendar.getTimeInMillis() / 1000;
                calendar.set(year, ruleMonth, ruleDayOfmonth, 23, 59, 59);
                long dayFinishTimestamp = calendar.getTimeInMillis() / 1000;


                if ((unixCallStartTime >= dayStartTimestamp) && (unixCallFinishTime <= dayFinishTimestamp)) {
                    long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                    if (curRule.getTarif() != null) {
                        callCostAbs = preferenceSeconds * curRule.getTarif();
                    } else {
                        LOGGER.error("COST is 0. Tariff is NULL for call with id:" + currentCall.getId());
                    }
                    break;
                }

            }

            // target day of week (analyse call start)
            if (ruleDayOfWeek != -1 && ruleDayOfWeek == callStartDayOfWeek) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, callStartMonth, callStartDayOfMonth, ruleStartTime[0], ruleStartTime[1], ruleStartTime[2]);
                long dayStartTimestamp = calendar.getTimeInMillis() / 1000;
                calendar.set(year, callStartMonth, callStartDayOfMonth, ruleFinishTime[0], ruleFinishTime[1], ruleFinishTime[2]);
                long dayFinishTimestamp = calendar.getTimeInMillis() / 1000;

                if ((unixCallFinishTime > dayStartTimestamp) && (unixCallStartTime < dayFinishTimestamp)) {
                    if ((unixCallStartTime >= dayStartTimestamp) && (unixCallStartTime <= dayFinishTimestamp)) {

                        long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                        if (curRule.getTarif() != null) {
                            callCostAbs = preferenceSeconds * curRule.getTarif();
                        } else {
                            LOGGER.error("COST is 0. Tariff is NULL for call with id:" + currentCall.getId());
                        }
                        break; // we find all period
                    }
                }
            }

            // no day of week
            if (ruleDayOfWeek == -1) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, callStartMonth, callStartDayOfMonth, ruleStartTime[0], ruleStartTime[1], ruleStartTime[2]);
                long dayStartTimestamp = calendar.getTimeInMillis() / 1000;
                calendar.set(year, callStartMonth, callStartDayOfMonth, ruleFinishTime[0], ruleFinishTime[1], ruleFinishTime[2]);
                long dayFinishTimestamp = calendar.getTimeInMillis() / 1000;

                if ((unixCallFinishTime > dayStartTimestamp) && (unixCallStartTime < dayFinishTimestamp)) {
                    if ((unixCallStartTime >= dayStartTimestamp) && (unixCallStartTime <= dayFinishTimestamp)) {

                        long preferenceSeconds = unixCallFinishTime - unixCallStartTime;
                        if (curRule.getTarif() != null) {
                            callCostAbs = preferenceSeconds * curRule.getTarif();
                        } else {
                            LOGGER.error("COST is 0. Tariff is NULL for call with id:" + currentCall.getId());
                        }
                        break; // we find all period
                    }
                }
            }
        }

        return callCostAbs;
    }

    private int[] calculateRuleStartTime(CallBillRule curRule) {
        int[] res = new int[]{0, 0, 0};
        if (curRule.getStarttime() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(curRule.getStarttime());
            res = new int[]{(cal.get(Calendar.HOUR_OF_DAY)), (cal.get(Calendar.MINUTE)), (cal.get(Calendar.SECOND))};
        }
        return res;
    }

    private int[] calculateRuleFinishTime(CallBillRule curRule) {
        int[] res = new int[]{23, 59, 59};
        if (curRule.getFinishtime() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(curRule.getFinishtime());
            res = new int[]{(cal.get(Calendar.HOUR_OF_DAY)), (cal.get(Calendar.MINUTE)), (cal.get(Calendar.SECOND))};
        }
        return res;
    }

    private int calculateRuleDayOfWeek(CallBillRule curRule) {
        if (curRule.getDayofweek() == null || curRule.getDayofweek().isEmpty()) {
            return -1;
        }
        return Integer.parseInt(curRule.getDayofweek());
    }

    private int calculateRuleMonth(CallBillRule curRule) {
        if (curRule.getMonth() == null || curRule.getMonth().isEmpty()) {
            return -1;
        }
        return Integer.parseInt(curRule.getMonth());
    }

    private int calculateRuleDayOfMonth(CallBillRule curRule) {
        if (curRule.getDayOfMonth() == null || curRule.getDayOfMonth().isEmpty()) {
            return -1;
        }
        return Integer.parseInt(curRule.getDayOfMonth());
    }


}
