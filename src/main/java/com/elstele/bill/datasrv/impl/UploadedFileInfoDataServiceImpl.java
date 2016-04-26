package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.UploadedFileInfoAssembler;
import com.elstele.bill.dao.interfaces.UploadedFileInfoDAO;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.FileStatus;
import com.elstele.bill.utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadedFileInfoDataServiceImpl implements UploadedFileInfoDataService {

    @Autowired
    UploadedFileInfoDAO uploadedFileInfoDAO;

    @Override
    @Transactional
    public List<UploadedFileInfoForm> getUploadedFileInfoList(String fileType) {
        List<UploadedFileInfoForm> result = new ArrayList<UploadedFileInfoForm>();
        UploadedFileInfoAssembler assembler = new UploadedFileInfoAssembler();

        List<UploadedFileInfo> beans = uploadedFileInfoDAO.getUploadedFileInfoList(fileType);
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
    public Integer addUploadedFileInfo(UploadedFileInfoForm uploadedFileInfoForm) {
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo uploadedFileInfo = uploadedFileInfoAssembler.fromFormToBean(uploadedFileInfoForm);
        return uploadedFileInfoDAO.create(uploadedFileInfo);
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
    public void setUploadedFileInfoStatusDelete(Integer id) {
        uploadedFileInfoDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public void updateFile(UploadedFileInfoForm uploadedFileInfoForm) {
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo uploadedFileInfo = uploadedFileInfoAssembler.fromFormToBean(uploadedFileInfoForm);
        uploadedFileInfoDAO.update(uploadedFileInfo);
    }

    @Override
    @Transactional
    public void createOrUpdateFileInfo(UploadedFileInfoForm uploadedFileInfoForm) {
        UploadedFileInfoAssembler uploadedFileInfoAssembler = new UploadedFileInfoAssembler();
        UploadedFileInfo uploadedFileInfo = uploadedFileInfoAssembler.fromFormToBean(uploadedFileInfoForm);
        List<UploadedFileInfo> uploadedFileInfoList = uploadedFileInfoDAO.getFileInfoByFormValues(uploadedFileInfo);
        if (!uploadedFileInfoList.isEmpty()) {
            for (UploadedFileInfo uploadedFileInfo1 : uploadedFileInfoList) {
                uploadedFileInfo1.setStatus(Status.ACTIVE);
                uploadedFileInfoDAO.update(uploadedFileInfo1);
            }
        } else {
            uploadedFileInfoDAO.create(uploadedFileInfo);
        }
    }

    @Override
    @Transactional
    public void setInfoAboutHandledTariffFile(File file, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        UploadedFileInfoForm fileInfo = new UploadedFileInfoForm();
        fileInfo.setFileSize(file.length());
        fileInfo.setFileName(file.getName());
        fileInfo.setPath(file.getName());
        fileInfo.setFileStatus(FileStatus.PROCESSED);
        fileInfo.setHandledBy(user.getUsername());
        createOrUpdateFileInfo(fileInfo);
    }
}
