package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.ExternalPaymentTransaction;

import java.util.List;

/**
 * Created by ivan on 15/12/27.
 */
public interface ExternalPaymentDAO extends CommonDAO<ExternalPaymentTransaction>{
    public List<ExternalPaymentTransaction> getExtPaymentList();
    public List<ExternalPaymentTransaction> getLastNOfExtPaymentList(Integer num);
}
