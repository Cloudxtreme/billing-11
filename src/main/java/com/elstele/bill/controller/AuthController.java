package com.elstele.bill.controller;

import com.elstele.bill.datasrv.LocalUserDataService;
import com.elstele.bill.form.LocalUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class AuthController {

    @Autowired
    private LocalUserDataService localUserDataService;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView loginCall(@ModelAttribute("userForm") LocalUserForm localUserForm, HttpSession session,
                            HttpServletRequest request){
        String userName = localUserForm.getUserName();
        String userPass = localUserForm.getUserPass();
        ModelAndView mav = new ModelAndView("main");
        if (localUserDataService.isCredentialValid(userName, userPass)){
            return mav;
        }
        return new ModelAndView("login_page");
    }


    //uncomment after test will be ready
    /*@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutCall(HttpSession session)
    {
        session.invalidate();
        //map.put("userForm", new LocalUserForm());
        return "main";
    }*/

}
