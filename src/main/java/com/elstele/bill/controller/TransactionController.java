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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

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
            mav.addObject("returnPage", returnPage);
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = transactionDataService.saveTransaction(form);
            return new ModelAndView("redirect:" + "../" + returnPage + "?returnPage=" + returnPage);
/*          -- To Fix: Can't redirect success message --
            ModelAndView mav = new ModelAndView("redirect:" + "../" + returnPage + "?returnPage=" + returnPage);
            return mav.addObject("successMessageTrans", message);
*/
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

    @RequestMapping(value = "/transaction/buildTransactionTable", method = RequestMethod.GET)
    @ResponseBody
    public List<TransactionForm> getTransactionList(HttpServletRequest request,
                                                    @RequestParam(value = "accountId") Integer accountId,
                                                    @RequestParam(value = "limit") Integer limit) {
        return transactionDataService.getTransactionList(accountId,limit);
    }

    @RequestMapping(value = "/transaction/searchTransaction", method = RequestMethod.GET)
    @ResponseBody
    public List<TransactionForm> searchTransactionList(HttpServletRequest request,
                                                     @RequestParam(value = "account") String account,
                                                     @RequestParam(value = "searchDate") String searchDate) throws ParseException  {
        Date dateStart = null;
        Date dateEnd = null;
        String[] dateParts = null;
        if (searchDate.contains(" - ")) {
            dateParts = searchDate.split(" - ");
            if(dateParts.length == 2){
                DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                dateStart = format.parse(dateParts[0]);
                dateEnd = format.parse(dateParts[1]);
            }
        }

        return transactionDataService.searchTransactionList(account, dateStart, dateEnd);
    }

    @RequestMapping(value="/transaction/{accountId}/catalog/", method = RequestMethod.GET)
    public String transactionCatalog(@PathVariable("accountId") Integer accountId, HttpSession session, Map<String, Object> map)
    {
        map.put("transactionList", transactionDataService.getTransactionList(accountId,Constants.TRANSACTION_DISPLAY_LIMIT));
        map.put("accountList", accountDataService.getAccountsList());
        map.put("selectedAccount", accountId);
        return "transactionCatalog";
    }

}
