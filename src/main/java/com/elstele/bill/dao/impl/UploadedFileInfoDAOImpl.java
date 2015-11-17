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
        return (List<UploadedFileInfo>)query.list();
    }
}
