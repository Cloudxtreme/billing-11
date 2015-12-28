package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView loginCall(@ModelAttribute("userForm") LocalUserForm localUserForm, HttpSession session,
                            HttpServletRequest request){
        String userName = localUserForm.getUsername();
        String userPass = localUserForm.getPassword();
        ModelAndView mav = new ModelAndView("main");
        String  language = LocaleContextHolder.getLocale().getLanguage();

        if (localUserDataService.isCredentialValid(userName, userPass)){
            LocalUser curUser = localUserDataService.getUserByNameAndPass(userName, userPass);
            session.setMaxInactiveInterval(1200);
            session.setAttribute(Constants.LOCAL_USER, curUser);
            mav.addObject("username", userName);
            return mav;
        }
        ModelAndView login = new ModelAndView("login_page");
        if(language.equals("en")) {
            login.addObject("errorMessage", "Your input is incorrect, please try again!");
        }else{
            login.addObject("errorMessage", "Данные неверны. Пожалуйста попробуйте ещё раз!");
        }
        return login;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView mainPage(){
        ModelAndView mav = new ModelAndView("main");
        return mav;
    }



    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutCall(HttpSession session)
    {
        session.setAttribute(Constants.LOCAL_USER, null);
        session.invalidate();
        return "login_page";
    }

}
