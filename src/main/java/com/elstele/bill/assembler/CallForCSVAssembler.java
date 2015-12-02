package com.elstele.bill.assembler;

import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class CallForCSVAssembler {

    public CallForCSVForm fromBeanToForm(CallForCSV bean){

        CallForCSVForm form = new CallForCSVForm();
        copyProperties(bean,form);
        return form;
    }

    public CallForCSV fromFormToBean(CallForCSVForm form){
        CallForCSV bean = new CallForCSV();
        copyProperties(form, bean);
        return bean;
    }
}
