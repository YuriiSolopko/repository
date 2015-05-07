package com.js.taxi.controller;

import com.js.taxi.domain.Operator;
import com.js.taxi.exception.ClientException;
import com.js.taxi.service.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jura on 03.04.2015.
 */
@Controller
@SessionAttributes("operator")
public class ClientController {

    public static final Logger log = Logger.getLogger(ClientController.class);

    @Autowired(required = true)
    private ClientService clientService;

    public ClientController() {
    }

    @RequestMapping(value = "/registerClient.html", method = RequestMethod.GET)
    public String register(@ModelAttribute Operator operator) {
        log.info("/registerClient controller");
        if (operator.getId() != null) {
            return "registerClient";
        }
        return "index";
    }

    @RequestMapping(value = "/registrationClient.html", method = RequestMethod.POST)
    public String registration(@RequestParam String clientName, @RequestParam String clientSurname,
                               @RequestParam String clientPhone, @RequestParam String clientAddress, Model model) {
        log.info("/registrationClient.html");
        try {
            clientService.createClient(clientName, clientSurname, clientPhone, clientAddress);
        } catch (ClientException e) {
            model.addAttribute("clientMessage", e.getMessage());
            return "registerClient";
        }
        model.addAttribute("clientMessage", "Registration successful");
        return "registerClient";
    }

    @RequestMapping(value = "/dashboard.html/showAll", method = RequestMethod.GET)
    public String showAll(@ModelAttribute Operator operator, @RequestParam Integer firstResult, Model model) {
        log.info("/dashboard.html/showAll controller");
        if (operator.getId() != null) {
            model.addAttribute("clientList", clientService.showClientsByPortions(firstResult, 10));
            model.addAttribute("firstIndex", firstResult);
            model.addAttribute("clientSize", clientService.clientRowCount());
            return "dashboard";
        }
        return "index";
    }

    @RequestMapping(value = "/dashboard.html/showLastMonth", method = RequestMethod.GET)
    public String showLastMonth(@ModelAttribute Operator operator, Model model) {
        log.info("/dashboard.html/showLastMonth controller");
        if (operator.getId() != null) {
            model.addAttribute("clientList", clientService.showClientsLastMonth());
            model.addAttribute("firstIndex", -1);
            return "dashboard";
        }
        return "index";
    }

    @RequestMapping(value = "/dashboard.html/showGtSum", method = RequestMethod.GET)
    public String showGtSum(@ModelAttribute Operator operator, @RequestParam String clientSum, Model model) {
        log.info("/dashboard.html/showGtSum controller");
        log.info("clientSum = " + clientSum);
        if (operator.getId() != null) {
            Integer sum = 0;
            try {
                sum = Integer.parseInt(clientSum);
            } catch (NumberFormatException e) {
                model.addAttribute("clientList", null);
                model.addAttribute("firstIndex", -1);
                return "dashboard";
            }
            model.addAttribute("clientList", clientService.showClientsGtSum(sum));
            model.addAttribute("firstIndex", -1);
            return "dashboard";
        }
        return "index";
    }
}
