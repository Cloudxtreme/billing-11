package com.elstele.bill.test.builder.bean;

import com.elstele.bill.reportCreators.CallsRequestParamTO;

public class CallRequestParamTOBuilder implements TestObjectCreator<CallRequestParamTOBuilder, CallsRequestParamTO>{
    private CallsRequestParamTO callsRequestParamTO;

    @Override
    public CallRequestParamTOBuilder build() {
        callsRequestParamTO = new CallsRequestParamTO();
        return this;
    }

    public CallRequestParamTOBuilder withNumberA(String numberA){
        callsRequestParamTO.setCallNumberA(numberA);
        return this;
    }

    public CallRequestParamTOBuilder withNumberB(String numberB){
        callsRequestParamTO.setCallNumberB(numberB);
        return this;
    }

    public CallRequestParamTOBuilder withPageResult(int pageResult){
        callsRequestParamTO.setPageResults(pageResult);
        return this;
    }

    public CallRequestParamTOBuilder withStartDate(String date){
        callsRequestParamTO.setStartDate(date);
        return this;
    }

    public CallRequestParamTOBuilder withEndDate(String date){
        callsRequestParamTO.setEndDate(date);
        return this;
    }

    public CallRequestParamTOBuilder withRows(int rows){
        callsRequestParamTO.setRows(rows);
        return this;
    }

    public CallRequestParamTOBuilder withPage(int page){
        callsRequestParamTO.setPage(page);
        return this;
    }

    public CallRequestParamTOBuilder withSelectedTime(String selectedTime){
        callsRequestParamTO.setSelectedTime(selectedTime);
        return this;
    }

    public CallRequestParamTOBuilder withOffset(int offset){
        callsRequestParamTO.setOffset(offset);
        return this;
    }

    @Override
    public CallsRequestParamTO getRes() {
        if(callsRequestParamTO == null){
            this.build();

        }
        return callsRequestParamTO;
    }
}
