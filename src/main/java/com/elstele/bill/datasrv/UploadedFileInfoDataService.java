package com.elstele.bill.datasrv;

import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;

import java.util.List;

public interface UploadedFileInfoDataService {
    public List<UploadedFileInfoForm> getUploadedFileInfoList();
    public void addUploadedFileInfo(UploadedFileInfoForm uploadedFileInfoForm);
    public UploadedFileInfoForm getById(Integer id);
    public void deleteUploadedFileInfo(Integer id);
    public void setUploadedFileInfoStatus(Integer id);
    public void setFileStatus(UploadedFileInfoForm uploadedFileInfoForm);
}
