package com.elstele.bill.controller;


import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ModelAndView getListOfAccounts(HttpSession session)
    {
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        ModelAndView mav = new ModelAndView("accounts_list");
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        return mav;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public @ResponseBody AccountForm addAccountFromForm(@RequestBody AccountForm accountForm, HttpServletRequest request) {

        accountDataService.saveAccount(accountForm);

        return new AccountForm();
    }

}
