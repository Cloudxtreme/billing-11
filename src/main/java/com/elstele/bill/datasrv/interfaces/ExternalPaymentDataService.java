package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.ExternalPaymentForm;

import java.util.List;

/**
 * Created by ivan on 15/12/27.
 */
public interface ExternalPaymentDataService {
    List<ExternalPaymentForm> getExtPaymentList();

}
