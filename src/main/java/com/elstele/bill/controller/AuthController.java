package com.elstele.bill.controller;

import com.elstele.bill.dao.interfaces.USDRateDAO;
import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import static com.elstele.bill.utils.Constants.LOCAL_USER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class AuthController {
    @Autowired
    USDRateDataService usdRateDataService;

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void getUSDRate(){
        usdRateDataService.setRate();
    }

    @Autowired
    private LocalUserDataService localUserDataService;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView loginCall(@ModelAttribute("userForm") LocalUserForm localUserForm, HttpSession session,
                            HttpServletRequest request){
        String userName = localUserForm.getUsername();
        String userPass = localUserForm.getPassword();
        ModelAndView mav = new ModelAndView("main");
        if (localUserDataService.isCredentialValid(userName, userPass)){
            LocalUser curUser = localUserDataService.getUserByNameAndPass(userName, userPass);
            session.setMaxInactiveInterval(1200);
            session.setAttribute(LOCAL_USER, curUser);
            mav.addObject("username", userName);
            return mav;
        }
        ModelAndView login = new ModelAndView("login_page");
        login.addObject("errorMessage", "Your input is incorrect, please try again!");
        return login;
    }



    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutCall(HttpSession session)
    {
        session.setAttribute(LOCAL_USER, null);
        session.invalidate();
        return "login_page";
    }

}
