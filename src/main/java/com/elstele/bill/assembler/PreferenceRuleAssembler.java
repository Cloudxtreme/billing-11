package com.elstele.bill.assembler;

import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.form.PreferenceRuleForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.beans.BeanUtils.copyProperties;

public class PreferenceRuleAssembler {
    String[] propToSkip = {"starttime", "finishtime"};
    final static Logger LOGGER = LogManager.getLogger(PreferenceRuleAssembler.class);

    public PreferenceRuleForm fromBeanToForm(PreferenceRule bean){
        PreferenceRuleForm form = new PreferenceRuleForm();
        copyProperties(bean, form, propToSkip);
        return timeToStringParseForUISide(bean, form);
    }

    public PreferenceRule fromFormToBean(PreferenceRuleForm form){
        PreferenceRule bean = new PreferenceRule();
        copyProperties(form, bean, propToSkip);
        return stringToTimeParseFromUISide(bean, form);
    }

    private PreferenceRuleForm timeToStringParseForUISide(PreferenceRule bean, PreferenceRuleForm form){
        Time starttime = (bean.getStarttime() != null ? bean.getStarttime() : null);
        Time finishtime = (bean.getFinishtime() != null ? bean.getFinishtime() : null);
        form.setFinishtime((finishtime != null ? finishtime.toString() : ""));
        form.setStarttime((starttime != null ? starttime.toString() : ""));
        return form;
    }

    private PreferenceRule stringToTimeParseFromUISide(PreferenceRule bean, PreferenceRuleForm form){
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String starttime = form.getStarttime();
        String finishtime = form.getFinishtime();
        try {
            if(!starttime.isEmpty()) {
                Time start = new Time(formatter.parse(starttime).getTime());
                bean.setStarttime(start);
            }if(!finishtime.isEmpty()){
                Time end = new Time(formatter.parse(finishtime).getTime());
                bean.setFinishtime(end);
            }
        }catch(ParseException e){
            LOGGER.error(e.getMessage() , e);
        }
        return bean;
    }

}