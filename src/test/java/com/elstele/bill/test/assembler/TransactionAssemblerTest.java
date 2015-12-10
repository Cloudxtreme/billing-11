package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.TransactionAssembler;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.test.builder.bean.TransactionBuilder;
import com.elstele.bill.test.builder.form.AccountFormBuilder;
import com.elstele.bill.test.builder.form.TransactionFormBuilder;
import com.elstele.bill.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class TransactionAssemblerTest {

    private TransactionAssembler assembler;
    private Transaction bean;
    private TransactionForm form;

    @Before
    public void setUp(){
        assembler = new TransactionAssembler();

        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withId(1).withAccName("Name").getRes();
        AccountFormBuilder afb = new AccountFormBuilder();
        AccountForm accountForm = afb.build().withId(1).withAccName("Name").getRes();

        TransactionBuilder tb = new TransactionBuilder();
        bean = tb.build().withId(33).withSource(Constants.TransactionSource.BANK).withAccount(account).withDirection(Constants.TransactionDirection.CREDIT).withPrice(22F).getRes();

        TransactionFormBuilder tfb = new TransactionFormBuilder();
        form = tfb.build().withId(33).withSource(Constants.TransactionSource.BANK).withAccount(accountForm).withDirection(Constants.TransactionDirection.CREDIT).withPrice(22F).getRes();
    }

    @Test
    public void a_fromFormToBean(){
        Transaction beanGet = assembler.fromFormToBean(form);
        bean.setDate(beanGet.getDate());
        assertTrue(beanGet.equals(bean));
    }

    @Test
    public  void b_fromBeanToForm(){
        TransactionForm formGet = assembler.fromBeanToForm(bean);
        assertTrue(formGet.equals(form));
    }
}
