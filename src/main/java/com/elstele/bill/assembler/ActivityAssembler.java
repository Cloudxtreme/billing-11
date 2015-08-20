package com.elstele.bill.assembler;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ActivityAssembler {
    public ActivityForm fromBeanToForm(Activity bean){
        ActivityForm form = new ActivityForm();
        copyProperties(bean,form);
        return form;
    }

    public Activity fromFormToBean(ActivityForm form){
        Activity bean = new Activity();
        copyProperties(form, bean);
        return bean;
    }

}
