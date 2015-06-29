package com.elstele.bill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static com.elstele.bill.utils.Constants.LOCAL_USER;

@Controller
public class AccountsController {

    @RequestMapping(value="/accounts_list", method = RequestMethod.GET)
    public String getListOfAccounts(HttpSession session)
    {

        return "accounts_list";
    }

}
