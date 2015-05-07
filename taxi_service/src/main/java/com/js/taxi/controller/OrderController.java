package com.js.taxi.controller;

import com.js.taxi.domain.Client;
import com.js.taxi.domain.Operator;
import com.js.taxi.exception.OrderException;
import com.js.taxi.service.ClientService;
import com.js.taxi.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jura on 05.04.2015.
 */
@Controller
@SessionAttributes("operator")
public class OrderController {

    public static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private ClientService clientService;

    public OrderController() {
    }

    @RequestMapping(value = "/order.html", method = RequestMethod.GET)
     public String order(@ModelAttribute Operator operator) {
        log.info("/order.html controller");
        if (operator.getId() != null) {
            return "order";
        }
        return "index";
    }

    @RequestMapping(value = "/createOrder.html", method = RequestMethod.POST)
    public String createOrder(@RequestParam String orderId, @RequestParam String clientPhoneNo,
                              @RequestParam String orderSum, @RequestParam String orderAddressFrom,
                              @RequestParam String orderAddressTo, @ModelAttribute Operator operator, Model model) {
        log.info("/createOrder.html controller");
        if (operator == null || operator.getId() == null) {
            return "index";
        }
        Long id = 0L;
        try{
            id = Long.parseLong(orderId);
        } catch (NumberFormatException e) {
            model.addAttribute("orderMessage", e.getMessage());
            return "order";
        }
        Client client = clientService.readClientByPhoneNo(clientPhoneNo);
        if (client == null) {
            model.addAttribute("orderMessage", "There is no client with such phone number");
            return "order";
        }
        try {
            orderService.createOrder(id, client, orderSum, orderAddressFrom, orderAddressTo);
        } catch (OrderException e) {
            model.addAttribute("orderMessage", e.getMessage());
            return "order";
        }
        model.addAttribute("orderMessage", "Order created");
        return "order";
    }

    @RequestMapping(value = "/orders.html", method = RequestMethod.GET)
     public String orders(@ModelAttribute Operator operator, @RequestParam Integer firstResult, Model model) {
        log.info("/order.html controller");
        if (operator.getId() != null) {
            model.addAttribute("firstIndex", firstResult);
            model.addAttribute("orderSize", orderService.getOrderRowCount());
            model.addAttribute("orderList", orderService.showOrdersByPortions(firstResult, 5));
            return "orders";
        }
        return "index";
    }

    @RequestMapping(value = "/orders.html/bySum", method = RequestMethod.GET)
    public String ordersBySum(@ModelAttribute Operator operator, @RequestParam String sumFrom,
                              @RequestParam String sumTo, Model model) {
        log.info("/orders.html/bySum controller");
        Integer sumF = 0;
        Integer sumT = 0;

        if (operator.getId() != null) {
            try {
                sumF = Integer.parseInt(sumFrom);
                sumT = Integer.parseInt(sumTo);
            } catch (NumberFormatException e) {
                model.addAttribute("orderMessage", "Incorrect data input");
                return "orders";
            }
            model.addAttribute("firstIndex", -1);
            model.addAttribute("orderList", orderService.showOrdersBySum(sumF, sumT));
            return "orders";
        }
        return "index";
    }

    @RequestMapping(value = "/orders.html/edit", method = RequestMethod.GET)
    public String orderEdit(@ModelAttribute Operator operator, @RequestParam Long orderId, Model model) {
        log.info("/order.html/edit controller");
        if (operator.getId() != null) {
            model.addAttribute("orderForEdit", orderService.readOrder(orderId));
            return "orderEdit";
        }
        return "index";
    }

    @RequestMapping(value = "/orders.html/editSubmit", method = RequestMethod.POST)
    public String orderEditSubmit(@RequestParam Long orderId, @RequestParam String clientPhoneNo,
                                  @RequestParam String orderSum, @RequestParam String orderAddressFrom,
                                  @RequestParam String orderAddressTo, @ModelAttribute Operator operator, Model model) {
        log.info("/order.html/editSubmit controller");
        if (operator == null || operator.getId() == null) {
            return "index";
        }
        Client client = clientService.readClientByPhoneNo(clientPhoneNo);
        if (client == null) {
            model.addAttribute("editOrderMessage", "There is no client with such phone number");
            return "orderEdit";
        }
        orderService.editOrder(orderId, client, orderSum, orderAddressFrom, orderAddressTo);
        model.addAttribute("editOrderMessage", "Saved successfully");
        model.addAttribute("orderForEdit", orderService.readOrder(orderId));
        return "orderEdit";
    }
}
