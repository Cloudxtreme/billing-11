package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.validator.TransactionValidator;
import org.omg.Dynamic.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    private TransactionDataService transactionDataService;
    @Autowired
    private AccountDataService accountDataService;
    @Autowired
    private TransactionValidator transactionValidator;

    @RequestMapping(value="/transaction/modifyForm", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("transactionForm") @Valid TransactionForm form, BindingResult result,
                                             @RequestParam(value = "returnPage") String returnPage) {
        transactionValidator.validate(form, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/transactionForm");
            mav.addObject("accountList", accountDataService.getAccountsList());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = transactionDataService.saveTransaction(form);
            ModelAndView mav = new ModelAndView(returnPage);
            List<TransactionForm> transFormList = returnPage.equals("accountFull") ?
                    transactionDataService.getTransactionList(form.getAccount().getId(),Constants.TRANSACTION_DISPLAY_LIMIT) :
                    transactionDataService.getTransactionList(form.getAccount().getId());
            mav.addObject("accountForm", accountDataService.getAccountById(form.getAccount().getId()));
            mav.addObject("transactionList", transFormList);
            mav.addObject("successMessageTrans", message);
            return mav;
        }
    }

    @RequestMapping(value = "/transaction/{accountId}/form", method = RequestMethod.GET)
    public String transactionFormView(@PathVariable("accountId") Integer accountId, Map<String, Object> map,
                                      @RequestParam(value = "returnPage") String returnPage) {
        List<Constants.TransactionSource> transactionSource = new ArrayList<Constants.TransactionSource>(Arrays.asList(Constants.TransactionSource.values()));
        List<Constants.TransactionDirection> transactionDirection = new ArrayList<Constants.TransactionDirection>(Arrays.asList(Constants.TransactionDirection.values()));
        TransactionForm form = transactionDataService.getTransactionForm(accountId);
        map.put("transactionForm", form);
        map.put("transactionSourceList", transactionSource);
        map.put("transactionDirectionList", transactionDirection);
        map.put("accountList", accountDataService.getAccountsList());
        map.put("returnPage", returnPage);
        return "transactionForm";
    }

    @RequestMapping(value="/transaction/{accountId}/catalog/", method = RequestMethod.GET)
    public String transactionCatalog(@PathVariable("accountId") Integer accountId, HttpSession session, Map<String, Object> map)
    {
        map.put("transactionList", transactionDataService.getTransactionList(accountId));
        map.put("accountList", accountDataService.getAccountsList());
        map.put("selectedAccount", accountId);
        return "transactionCatalog";
    }

}
