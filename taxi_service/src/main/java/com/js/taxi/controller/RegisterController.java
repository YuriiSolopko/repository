package com.js.taxi.controller;

import com.js.taxi.exception.AuthorizationException;
import com.js.taxi.service.AuthorizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jura on 02.04.2015.
 */
@Controller
@SessionAttributes("operator")
public class RegisterController {

    public static final Logger log = Logger.getLogger(RegisterController.class);

    @Autowired(required = true)
    private AuthorizationService authorizationService;

    public RegisterController() {
    }

    @RequestMapping(value = "/register.html", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String register() {
        log.info("/register controller");
        return "register";
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public String registration(@RequestParam String login, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String id, Model model) {
        log.info("/registration controller");
        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Password confirm failed");
            return "register";
        }
        try {
            authorizationService.register(login, id, password);
        } catch (AuthorizationException e) {
            model.addAttribute("message", e.getMessage());
            return "register";
        }
        model.addAttribute("message", "registration successful");
        return "index";
    }
}
