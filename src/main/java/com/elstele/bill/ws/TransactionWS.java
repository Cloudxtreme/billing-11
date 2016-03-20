package com.elstele.bill.ws;

import com.elstele.bill.to.TransactionTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;


@WebService
public interface TransactionWS {
    @WebMethod(operationName = "getTransactionsByAccount")
    @WebResult(name = "transaction")
    public List<TransactionTO> getTransactionsByAccount(@WebParam(name = "accountId") int accountId);
}
