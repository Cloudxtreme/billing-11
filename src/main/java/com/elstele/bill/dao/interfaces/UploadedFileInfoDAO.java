package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.domain.common.CommonDomainBean;

import java.util.List;

public interface UploadedFileInfoDAO extends CommonDAO<UploadedFileInfo> {

    public List<UploadedFileInfo> getUploadedFileInfoList();
}
