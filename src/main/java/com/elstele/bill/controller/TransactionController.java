package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ModelAndView accountServiceModify(@ModelAttribute("transactionForm") @Valid TransactionForm form, BindingResult result) {
        transactionValidator.validate(form, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/transactionForm");
            mav.addObject("accountList", accountDataService.getAccountsList());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = transactionDataService.saveTransaction(form);
            ModelAndView mav = new ModelAndView("transactionCatalog");
            mav.addObject("transactionList", transactionDataService.getTransactionList(0));
            mav.addObject("successMessage", message);
            return mav;
        }
    }

    @RequestMapping(value = "/transaction/{accountId}/form", method = RequestMethod.GET)
    public String transactionFormView(@PathVariable("accountId") Integer accountId, HttpSession session, Map<String, Object> map) {
        List<Constants.TransactionSource> transactionSource = new ArrayList<Constants.TransactionSource>(Arrays.asList(Constants.TransactionSource.values()));
        List<Constants.TransactionDirection> transactionDirection = new ArrayList<Constants.TransactionDirection>(Arrays.asList(Constants.TransactionDirection.values()));
        TransactionForm form = transactionDataService.getTransactionForm(accountId);
        map.put("transactionForm", form);
        map.put("transactionSourceList", transactionSource);
        map.put("transactionDirectionList", transactionDirection);
        map.put("accountList", accountDataService.getAccountsList());
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
