package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StreetController {
    @Autowired
    StreetDataService streetDataService;

    @RequestMapping(value="**/getListOfStreets", method = RequestMethod.POST)
    @ResponseBody
    public List<Street> getListOfStreets(@RequestParam(value = "query") String query) {
        return streetDataService.getListOfStreets(query);
    }
}
