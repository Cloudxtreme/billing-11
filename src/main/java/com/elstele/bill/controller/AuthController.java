package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private LocalUserDataService localUserDataService;
    @Autowired
    private Messagei18nHelper messageHelper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginCall(@ModelAttribute("userForm") LocalUserForm localUserForm, HttpSession session,
                                  HttpServletRequest request) {
        String userName = localUserForm.getUsername();
        String userPass = localUserForm.getPassword();
        ModelAndView mav = new ModelAndView("main");
        if (localUserDataService.isCredentialValid(userName, userPass)) {
            LocalUser curUser = localUserDataService.getUserByNameAndPass(userName, userPass);
            session.setMaxInactiveInterval(1200);
            session.setAttribute(Constants.LOCAL_USER, curUser);
            mav.addObject("username", userName);
            return mav;
        }
        ModelAndView login = new ModelAndView("login_page");
        login.addObject(Constants.ERROR_MESSAGE, messageHelper.getMessage("auth.error"));
        return login;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView mainPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("main");
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        mav.addObject("username", user.getUsername());
        return mav;

    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutCall(HttpSession session) {
        session.setAttribute(Constants.LOCAL_USER, null);
        session.invalidate();
        return "redirect: /";
    }

}
