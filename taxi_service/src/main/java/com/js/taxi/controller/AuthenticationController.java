package com.js.taxi.controller;

import com.js.taxi.domain.Operator;
import com.js.taxi.domain.UserTries;
import com.js.taxi.exception.AuthenticationException;
import com.js.taxi.service.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jura on 01.04.2015.
 */
@Controller
@SessionAttributes({"userTries", "operator"})
public class AuthenticationController {

    public static final Logger log = Logger.getLogger(AuthenticationController.class);

    @Value("${tries}")
    private Integer tries;

    @Autowired(required = true)
    private AuthenticationService authenticationService;

    public AuthenticationController() {
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model) {
        log.info("/index controller");
        if (!model.containsAttribute("userTries")) {
            model.addAttribute("userTries", new UserTries(tries));
        }
        if(!model.containsAttribute("operator")) {
            model.addAttribute("operator", new Operator());
        }
        return "index";
    }

    @RequestMapping(value = "/dashboard.html", method = RequestMethod.POST)
    public String authentication(@RequestParam String login, @RequestParam String password,
                                 @ModelAttribute UserTries userTries, @ModelAttribute Operator operator, Model model) {
        log.info("/dashboard controller" + operator.toString());
        if(operator.getLogin()==null || operator.getLogin().equals("")) {
            userTries.setTries(userTries.getTries() - 1);
        } else if(!operator.getLogin().equals(login) || !operator.getPassword().equals(password)){
            userTries.setTries(userTries.getTries() - 1);
            model.addAttribute("operator", new Operator());
        }
        if (userTries.getTries() < 1) {
            model.addAttribute("message", "No more tries");
            return "index";
        }

        try {
            if (authenticationService.authenticate(login, password)) {
                if (authenticationService.isExpiredPass(login)) {
                    model.addAttribute("operatorLog", login);
                    return "newpass";
                }
                model.addAttribute("operator", authenticationService.readOperatorByLogin(login));
                userTries.setTries(tries);
                return "dashboard";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("message", e.getMessage());
            return "index";
        }
        model.addAttribute("message", "Invalid login");
        return "index";
    }

    @RequestMapping(value = "/newpass.html", method = RequestMethod.POST)
    public String newPass(@RequestParam String operatorLogin, @RequestParam String oldPassword,
                          @RequestParam String newPassword, @RequestParam String confirmNewPassword,
                          @ModelAttribute UserTries userTries, Model model) {
        try {
            if (authenticationService.updateOperatorPassword(operatorLogin, oldPassword, newPassword, confirmNewPassword)) {
                model.addAttribute("message", "Password changed");
                userTries.setTries(tries);
                return "index";
            } else {
                model.addAttribute("newPassMessage", "Error");
                return "newpass";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("newPassMessage", "Error");
            return "newpass";
        }
    }
}
