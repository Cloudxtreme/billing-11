package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.UploadedFileInfoDAO;
import com.elstele.bill.domain.UploadedFileInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadedFileInfoDAOImpl extends CommonDAOImpl<UploadedFileInfo> implements UploadedFileInfoDAO {

    @Override
    public List<UploadedFileInfo> getUploadedFileInfoList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from UploadedFileInfo");
        return (List<UploadedFileInfo>) query.list();
    }

    @Override
    public List<UploadedFileInfo> getFileInfoByFormValues(UploadedFileInfo uploadedFileInfo) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from UploadedFileInfo u where " +
                "u.fileName = :fileName and u.fileSize = :fileSize and u.path = :path")
                .setParameter("fileName", uploadedFileInfo.getFileName())
                .setParameter("fileSize", uploadedFileInfo.getFileSize())
                .setParameter("path", uploadedFileInfo.getPath());
        return (List<UploadedFileInfo>) query.list();
    }
}
