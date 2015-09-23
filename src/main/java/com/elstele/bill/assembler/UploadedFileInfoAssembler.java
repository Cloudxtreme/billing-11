package com.elstele.bill.assembler;

import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import static org.springframework.beans.BeanUtils.copyProperties;


public class UploadedFileInfoAssembler {
    public UploadedFileInfoForm fromBeanToForm(UploadedFileInfo bean){
        UploadedFileInfoForm form = new UploadedFileInfoForm();

        copyProperties(bean,form);
        return form;
    }



    public UploadedFileInfo fromFormToBean(UploadedFileInfoForm form){
        UploadedFileInfo bean = new UploadedFileInfo();
        copyProperties(form, bean);
        return bean;
    }
}
