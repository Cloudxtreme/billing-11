package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.UploadedFileInfoAssembler;
import com.elstele.bill.dao.UploadedFileInfoDAO;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UploadedFileInfoDataServiceImpl implements UploadedFileInfoDataService {

    @Autowired
    UploadedFileInfoDAO uploadedFileInfoDAO;

    @Override
    @Transactional
    public List<UploadedFileInfoForm> getUploadedFileInfoList() {
        List<UploadedFileInfoForm> result = new ArrayList<UploadedFileInfoForm>();
        UploadedFileInfoAssembler assembler = new UploadedFileInfoAssembler();

        List<UploadedFileInfo> beans = uploadedFileInfoDAO.getUploadedFileInfoList();
        for (UploadedFileInfo curBean : beans) {
            if (curBean.getStatus() != Status.DELETED) {
                UploadedFileInfoForm curForm = assembler.fromBeanToForm(curBean);
                result.add(curForm);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void addUploadedFileInfo(UploadedFileInfoForm uploadedFileInfoForm){
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo uploadedFileInfo = uploadedFileInfoAssembler.fromFormToBean(uploadedFileInfoForm);
        uploadedFileInfoDAO.create(uploadedFileInfo);
    }

    @Override
    @Transactional
    public UploadedFileInfoForm getById(Integer id) {
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo bean = uploadedFileInfoDAO.getById(id);
        UploadedFileInfoForm result = uploadedFileInfoAssembler.fromBeanToForm(bean);
        return result;
    }


    @Override
    @Transactional
    public void deleteUploadedFileInfo(Integer id) {
        uploadedFileInfoDAO.delete(id);
    }

    @Override
    @Transactional
    public void setUploadedFileInfoStatus(Integer id) {
        uploadedFileInfoDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public void setFileStatus(UploadedFileInfoForm uploadedFileInfoForm) {
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo uploadedFileInfo = uploadedFileInfoAssembler.fromFormToBean(uploadedFileInfoForm);
        uploadedFileInfoDAO.update(uploadedFileInfo);
    }



}
