package com.elstele.bill.controller;


import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("**/accounts")
public class AccountsController {

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private TransactionDataService transactionDataService;

    @Autowired
    private Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = "/accountHome", method = RequestMethod.GET)
    public ModelAndView handleAccountHome() {
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        int totalPages = determineTotalPagesForOutput();
        ModelAndView mav = new ModelAndView("accounts_list");
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }


    @RequestMapping(value = "/accountsList", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountForm> getAccountsList(@RequestParam(value = "rows") int rows,
                                             @RequestParam(value = "page") int page) {
        List<AccountForm> result = accountDataService.getAccountsList(rows, page);
        return result;
    }


    @RequestMapping(value = "/accountsShortList", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountForm> getAccountsShortFormList(@RequestParam(value = "rows") int rows,
                                                      @RequestParam(value = "page") int page) {
        List<AccountForm> result = accountDataService.getAccountsLiteFormList(rows, page);
        return result;
    }


    //getAccount
    @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
    @ResponseBody
    public AccountForm getAccountById(@RequestParam(value = "id") int id) {
        AccountForm result = accountDataService.getAccountById(id);
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public AccountForm addAccountFromForm(@ModelAttribute("accountForm") AccountForm accountForm, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        accountDataService.saveAccount(accountForm, user.getUsername());
        return new AccountForm();
    }

    @RequestMapping(value = "/editAccount", method = RequestMethod.POST)
    public
    @ResponseBody
    AccountForm editAccount(@ModelAttribute AccountForm accountForm, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        accountDataService.updateAccount(accountForm, user.getUsername());
        return new AccountForm();
    }

    @RequestMapping(value = {"/editFull/{id}", "/editFull/{id}/servicehistory"}, method = RequestMethod.GET)
    public ModelAndView editAccountFull(@PathVariable int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("accountFull");
        AccountForm result;
        if (request.getRequestURI().contains("servicehistory")) {
            result = accountDataService.getAccountWithAllServicesById(id);
        } else {
            result = accountDataService.getAccountById(id);
        }
        mav.addObject("accountForm", result);
        mav.addObject("transactionList", transactionDataService.getTransactionList(id, Constants.TRANSACTION_DISPLAY_LIMIT));
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteAccount(@PathVariable int id, RedirectAttributes redirectAttributes, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        accountDataService.softDeleteAccount(id, user.getUsername());
        redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, messagei18nHelper.getMessage("account.success.delete"));
        return "redirect:../accountHome";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveAccountFull(@ModelAttribute AccountForm accountForm, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        ModelAndView mav = new ModelAndView("accounts_list");
        accountDataService.updateAccount(accountForm, user.getUsername());
        int totalPages = determineTotalPagesForOutput();
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        mav.addObject(Constants.SUCCESS_MESSAGE, messagei18nHelper.getMessage("account.success.update"));
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        mav.addObject("pagesTotal", totalPages);

        return mav;
    }

    @RequestMapping(value = "/accountsearch", method = RequestMethod.POST)
    public ModelAndView searchAccount(@RequestParam String searchInput) {
        Set<AccountForm> accountFormList = accountDataService.searchAccounts(searchInput);
        ModelAndView mav = new ModelAndView("accountsearchModel");
        if (!accountFormList.isEmpty()) {
            mav.addObject("accountList", accountFormList);
        } else {
            mav.addObject(Constants.MESSAGE, messagei18nHelper.getMessage("search.message"));
        }
        return mav;
    }

    @RequestMapping(value = "/accountsearch", method = RequestMethod.GET)
    public ModelAndView searchAccountGet() {
        ModelAndView mav = new ModelAndView("accountsearchModel");
        mav.addObject(Constants.MESSAGE, messagei18nHelper.getMessage("search.message"));
        return mav;
    }

    private int determineTotalPagesForOutput() {
        int accounts = accountDataService.getActiveAccountsCount();
        if (accounts % 20 == 0)
            return accounts / 20;
        else
            return (accounts / 20) + 1;
    }
}
