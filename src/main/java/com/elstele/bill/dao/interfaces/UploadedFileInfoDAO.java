package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.UploadedFileInfo;

import java.util.List;

public interface UploadedFileInfoDAO extends CommonDAO<UploadedFileInfo> {

    public List<UploadedFileInfo> getUploadedFileInfoList(String fileType);

    public List<UploadedFileInfo> getFileInfoByFormValues(UploadedFileInfo uploadedFileInfo);
}
