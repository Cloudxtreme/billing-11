package com.elstele.bill.controller;


import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.form.AccountForm;

import com.elstele.bill.utils.Constants;
import org.hibernate.HibernateException;
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


    @RequestMapping(value="/accountsShortList", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountForm> getAccountsShortFormList(HttpServletRequest request,
                                             @RequestParam(value = "rows") int rows,
                                             @RequestParam(value = "page") int page){
        List<AccountForm> result = accountDataService.getAccountsLiteFormList(rows, page);
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
    public String saveAccountFull(@ModelAttribute AccountForm accountForm, RedirectAttributes redirectAttributes) {
        try {
            accountDataService.updateAccount(accountForm);
            redirectAttributes.addFlashAttribute("successMessage", "Account updated successfully");
        }catch(HibernateException e ){
            redirectAttributes.addFlashAttribute("errorMessage", "Account updating error. Selected street is not added into the DB. Please select your street from list");
        }
        int totalPages = determineTotalPagesForOutput();
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        redirectAttributes.addFlashAttribute("accountForm", new AccountForm());
        redirectAttributes.addFlashAttribute("accountTypeList", types);
        redirectAttributes.addFlashAttribute("pagesTotal", totalPages);

        return "redirect: /accountHome.html";
    }

    private int determineTotalPagesForOutput() {
        int accounts = accountDataService.getActiveAccountsCount();
        if (accounts%10 == 0)
            return accounts/10;
        else
            return (accounts/10)+1;
    }
}
