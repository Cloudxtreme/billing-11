package com.elstele.bill.controller;


import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.AccountForm;

import com.elstele.bill.utils.Constants.Constants;
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
        int totalPages = determineTotalPagesForOutput();
        ModelAndView mav = new ModelAndView("accounts_list");
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }


    @RequestMapping(value="/accountsList", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountForm> getAccountsList(HttpServletRequest request,
                                             @RequestParam(value = "rows") int rows,
                                             @RequestParam(value = "page") int page){
        List<AccountForm> result = accountDataService.getAccountsList(rows, page);
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
    public @ResponseBody AccountForm addAccountFromForm(@ModelAttribute("accountForm") AccountForm accountForm, HttpServletRequest request) {
        accountDataService.saveAccount(accountForm);
        return new AccountForm();
    }

    @RequestMapping(value="/editAccount", method = RequestMethod.POST)
    public @ResponseBody AccountForm editAccount(@ModelAttribute AccountForm accountForm, HttpServletRequest request) {
        accountDataService.updateAccount(accountForm);
        return new AccountForm();
    }

    @RequestMapping(value="/editFull/{id}", method = RequestMethod.GET)
    public ModelAndView editAccountFull(@PathVariable int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("accountFull");
        AccountForm result = accountDataService.getAccountById(id);
        mav.addObject("accountForm", result);
        return mav;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteAccount(@PathVariable int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:../accountHome");
        accountDataService.softDeleteAccount(id);
        return mav;
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ModelAndView saveAccountFull(@ModelAttribute AccountForm accountForm, HttpServletRequest request) {
        accountDataService.updateAccount(accountForm);
        int totalPages = determineTotalPagesForOutput();
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        ModelAndView mav = new ModelAndView("accounts_list");
        mav.addObject("accountForm", new AccountForm());
        mav.addObject("accountTypeList", types);
        mav.addObject("pagesTotal", totalPages);

        return mav;
    }

    private int determineTotalPagesForOutput() {
        int accounts = accountDataService.getActiveAccountsCount();
        if (accounts%10 == 0)
            return accounts/10;
        else
            return (accounts/10)+1;
    }


    //getListOfStreets
    @RequestMapping(value="/getListOfStreets", method = RequestMethod.GET)
    @ResponseBody
    public List<Street> getListOfStreets(@RequestParam(value = "query") String query, HttpServletRequest request) {
        List<String> result = new ArrayList<>();
        result.add("Армейская");
        result.add("Абрикосовая");
        result.add("Ананасовая");
        result.add("Пущкинская");
        result.add(query);
        return accountDataService.getStreets(query);
    }
}
