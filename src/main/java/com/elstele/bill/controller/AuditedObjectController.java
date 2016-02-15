package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuditedObjectController {

    @Autowired
    AuditedObjectDataService auditedObjectDataService;

    @RequestMapping(value = "**/objectinfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getObjectInfo(@PathVariable("id") int id, @RequestParam(value = "type") String objClassName) {
        List<AuditedObjectForm> auditedObjectFormList = auditedObjectDataService.getAuditedObject(id, objClassName);
        ModelAndView mav = new ModelAndView("auditedmodel");
        mav.addObject("auditedList", auditedObjectFormList);
        mav.addObject("createdBy", auditedObjectDataService.getCreatedBy(auditedObjectFormList));
        mav.addObject("createdDate", auditedObjectDataService.getCreatedDate(auditedObjectFormList));
        return mav;
    }
}
