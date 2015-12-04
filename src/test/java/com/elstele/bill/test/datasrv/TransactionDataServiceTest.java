package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.TransactionDAOImpl;
import com.elstele.bill.datasrv.impl.TransactionDataServiceImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.test.builder.bean.TransactionBuilder;
import com.elstele.bill.test.builder.form.AccountFormBuilder;
import com.elstele.bill.test.builder.form.TransactionFormBuilder;
import com.elstele.bill.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDataServiceTest {
    private List<Transaction> transactionListSample = new ArrayList<Transaction>();
    private Date currentDate = getTimestamp();

    @Mock
    private TransactionDAOImpl transactionDAO;
    @InjectMocks
    private TransactionDataServiceImpl transactionDataService;

    @Before
    public void setUp() {
        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withId(1).withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).getRes();

        TransactionBuilder tb = new TransactionBuilder();
        Transaction trans1 = tb.build().withComment("Comment1").withDate(currentDate).withDirection(Constants.TransactionDirection.CREDIT).withPrice(22F).withSource(Constants.TransactionSource.BANK).withAccount(account).getRes();
        Transaction trans2 = tb.build().withComment("Comment2").withDate(currentDate).withDirection(Constants.TransactionDirection.DEBIT).withPrice(110F).withSource(Constants.TransactionSource.HANDMADE).withAccount(account).getRes();

        transactionListSample.add(trans1);
        transactionListSample.add(trans2);
    }

    @After
    public void tearDown() {
        transactionDAO = null;
        transactionDataService = null;
    }

    @Test
    public void a_getTransactionList(){
        when(transactionDAO.getTransactionList(1)).thenReturn(transactionListSample);
        AccountFormBuilder afb = new AccountFormBuilder();
        AccountForm accountForm = afb.build().withId(1).withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).getRes();

        TransactionFormBuilder tfb = new TransactionFormBuilder();
        TransactionForm transForm1 = tfb.build().withComment("Comment1").withDate(currentDate).withDirection(Constants.TransactionDirection.CREDIT).withPrice(22F).withSource(Constants.TransactionSource.BANK).withAccount(accountForm).getRes();
        TransactionForm transForm2 = tfb.build().withComment("Comment2").withDate(currentDate).withDirection(Constants.TransactionDirection.DEBIT).withPrice(110F).withSource(Constants.TransactionSource.HANDMADE).withAccount(accountForm).getRes();

        List<TransactionForm> transactionFormList = transactionDataService.getTransactionList(1);
        assertTrue(transactionFormList.contains(transForm1));
        assertTrue(transactionFormList.contains(transForm2));
        assertTrue(transactionFormList.size()==2);
    }

    @Test
    public void b_getTransactionFormByAccountId(){
        TransactionForm transForm = transactionDataService.getTransactionForm(1);
        assertTrue(transForm.getAccount().getId().equals(1));
    }

    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
