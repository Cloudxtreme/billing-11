package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.ExternalPaymentDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.form.ExternalPaymentForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.validator.TransactionValidator;
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
import java.util.*;

@Controller
public class ExternalPaymentController {
    @Autowired
    private ExternalPaymentDataService externalPaymentDataService;

    private static Integer DEFAULT_NUM_OF_RESULTS = 20;


    @RequestMapping(value = "/extpayments/listlast", method = RequestMethod.GET)
    public ModelAndView transactionFormView() {
        ModelAndView mav = new ModelAndView("extpayments");
        List<ExternalPaymentForm> extPayments = externalPaymentDataService.getLastNOfExtPaymentList(DEFAULT_NUM_OF_RESULTS);
        mav.addObject("payments", extPayments);
        return mav;
    }


}
