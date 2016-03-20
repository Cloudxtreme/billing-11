package com.elstele.bill.ws;

import com.elstele.bill.dao.impl.TransactionDAOImpl;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.to.TransactionTO;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Service("transactionServ")
@WebService(endpointInterface = "com.elstele.bill.ws.TransactionWS")
public class TransactionWSImpl implements TransactionWS {

    @Autowired
    private TransactionDataService transactionDataService;

    public List<TransactionTO> getTransactionsByAccount(int accountId) {
        List<TransactionTO> result = transactionDataService.getTransactionForAccount(accountId);
        return result;

    }

}
