package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.SaldoResultForm;

import java.util.Date;

/**
 * Created by ivan on 16/03/26.
 */
public interface SaldoDataService {
    public SaldoResultForm generateSaldoResult(Date from, Date to);
}
