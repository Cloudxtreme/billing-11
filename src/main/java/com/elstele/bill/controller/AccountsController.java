package com.elstele.bill.controller;


import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountDataService accountDataService;

    @RequestMapping(value="/accountHome", method = RequestMethod.GET)
    public ModelAndView handleAccountHome(HttpSession session)
    {
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        ModelAndView mav = new ModelAndView("accounts_list");
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        return mav;
    }


    @RequestMapping(value="/accountsList", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountForm> getAccountsList(HttpServletRequest request,
                                             @RequestParam(value = "rows") int rows,
                                             @RequestParam(value = "page") int page){
        List<AccountForm> result = accountDataService.getAccountsList();
        return result;
    }

    //getAccount
    @RequestMapping(value="/getAccount", method = RequestMethod.GET)
    @ResponseBody
    public AccountForm getAccountById(HttpServletRequest request, @RequestParam(value = "id") int id){
        AccountForm result = accountDataService.getAccountById(id);
        return result;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public @ResponseBody AccountForm addAccountFromForm(@RequestBody AccountForm accountForm, HttpServletRequest request) {
        accountDataService.saveAccount(accountForm);
        return new AccountForm();
    }

    @RequestMapping(value="/editAccount", method = RequestMethod.POST)
    public @ResponseBody AccountForm editAccount(@RequestBody AccountForm accountForm, HttpServletRequest request) {
        accountDataService.updateAccount(accountForm);
        return new AccountForm();
    }
}
