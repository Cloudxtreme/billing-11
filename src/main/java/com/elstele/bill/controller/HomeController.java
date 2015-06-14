package com.elstele.bill.controller;

import com.elstele.bill.form.LocalUserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by X240 on 03/06/2015.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String homeEmptyIndex(HttpSession session, Map<String, Object> map)
    {
        map.put("userForm", new LocalUserForm());
        return "login_page";
    }


}
