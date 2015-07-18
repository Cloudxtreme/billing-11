package com.elstele.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by оо on 15.07.2015.
 */

@Controller
public class InventoryListController {

    @RequestMapping(value="/inventory_list", method = RequestMethod.GET)
    public String getListOfInventory(HttpSession session)
    {

        return "inventory_list";
    }
}
