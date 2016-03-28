package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.SaldoDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.SaldoForm;
import com.elstele.bill.form.SaldoResultForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SaldoDataServiceImpl implements SaldoDataService {

    @Autowired
    private AccountDataService accountDataService;
    @Autowired
    private TransactionDataService transactionDataService;

    @Transactional
    public SaldoResultForm generateSaldoResult(Date from, Date to) {
        SaldoResultForm result = new SaldoResultForm();

        List<Account> accounts = accountDataService.getAccountBeansList();
        for (Account account : accounts){
            SaldoForm saldoForm = generateSaldoForAccount(account, from, to);
            addCurrentSaldoToResultForm(saldoForm, result);
        }

        return result;
    }

    private void addCurrentSaldoToResultForm(SaldoForm saldoForm, SaldoResultForm result) {
        result.addSaldoForm(saldoForm);
        result.addTotalAvans(saldoForm.getAvans());
        result.addTotalAvansPrev(saldoForm.getAvansPrev());
        result.addTotalDebet(saldoForm.getDebet());
        result.addTotalFinishDebet(saldoForm.getFinishDebet());
        result.addTotalFinishKredit(saldoForm.getFinishKredit());
        result.addTotalKredit24(saldoForm.getKredit24());
        result.addTotalKreditKassa(saldoForm.getKreditKassa());
        result.addTotalKreditPidv(saldoForm.getKreditPivd());
        result.addTotalKreditYsb(saldoForm.getKreditYsb());
        result.addTotalNds(saldoForm.getTotalNds());
        result.addTotalStartDebet(saldoForm.getStartDebet());
        result.addTotalStartKredit(saldoForm.getStartKredit());
        result.addTotalSumNachisl(saldoForm.getSummAllNachisl());
        result.addTotalSumOplaty(saldoForm.getSumOplat());
    }

    private SaldoForm generateSaldoForAccount(Account account, Date from, Date to) {
        SaldoForm result = new SaldoForm();
        result.setAccountName(account.getAccountName());
        result.setFio(account.getFio());
        result.setBalance(account.getCurrentBalance());

        //balance on start period
        Float startHistBalance = transactionDataService.getBalanceOnDateForAccount(account.getId(), from);
        result.setStartDebet(0f);
        result.setStartKredit(0f);
        if (startHistBalance >= 0f) {
            result.setStartKredit(startHistBalance);
        } else {
            result.setStartDebet(Math.abs(startHistBalance));
        }


        //debet
        Float debet = transactionDataService.calcSumOfDebetAccountTransactionForPeriod(account.getId(), from, to);
        result.setDebet(debet);
        result.setSummAllNachisl(debet);

        //kredit (oplaty))
        List<Transaction> kreditTransactions = transactionDataService.getKreditAccountTransactionForPeriod(account.getId(), from, to);
        Float kreditPivd = calcSumBySource(kreditTransactions, Constants.TransactionSource.BANK_PIVD);
        result.setKreditPivd(kreditPivd);
        Float kreditUsb = calcSumBySource(kreditTransactions, Constants.TransactionSource.BANK_USB);
        result.setKreditYsb(kreditUsb);
        Float kredit24 = calcSumBySource(kreditTransactions, Constants.TransactionSource.NONSTOP24);
        result.setKredit24(kredit24);
        Float kreditKassa = calcSumBySource(kreditTransactions, Constants.TransactionSource.KASSA);
        result.setKreditKassa(kreditKassa);

        result.setSumOplat(kreditPivd + kreditUsb + kredit24 + kreditKassa);

        //finish debet and kredit
        result.setFinishDebet(0f);
        result.setFinishKredit(0f);
        Float finishResult = result.getStartDebet() + result.getDebet() - result.getStartKredit() - result.getSumOplat();
        if (finishResult >= 0){
            result.setFinishDebet(finishResult);
        } else {
            result.setFinishKredit(Math.abs(finishResult));
        }

        //avans
        Float avansPrev = result.getStartKredit() - result.getSummAllNachisl();
        result.setAvansPrev(0f);
        if (avansPrev < 0){
            result.setAvansPrev(0f);
        } else {
            result.setAvansPrev(avansPrev);
        }

        result.setAvans(0f);
        Float avans = result.getFinishKredit() - result.getAvansPrev();
        if (avans < 0){
            result.setAvans(0f);
        } else {
            result.setAvans(avans);
        }

        Float totalNds = result.getSummAllNachisl() - result.getStartKredit() + result.getFinishKredit();
        result.setTotalNds(totalNds);

        return result;
    }

    private Float calcSumBySource(List<Transaction> transactions, Constants.TransactionSource source) {
        Float sum = 0f;
        for (Transaction curTransaction: transactions){
            if (source.equals(curTransaction.getSource())){
                sum = sum + curTransaction.getPrice();
            }
        }
        return sum;
    }
}
