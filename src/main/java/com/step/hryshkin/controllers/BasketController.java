package com.step.hryshkin.controllers;

import com.step.hryshkin.model.Order;
import com.step.hryshkin.security.model.CustomUserDetails;
import com.step.hryshkin.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alekx
 */

@Controller
public class BasketController {
    @Inject
    private OrderService orderService;

    @GetMapping("/basket")
    public String basketPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model, ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        model.addAttribute("user", customUserDetails.getUsername());
        model.addAttribute("currentList", request
                .getSession()
                .getAttribute("goodListForCurrentOrder"));
        model.addAttribute("totalPrice", orderService
                .printTotalPriceForOrder(((Order) request
                        .getSession()
                        .getAttribute("order"))
                        .getId()));
        return "basket/basket";
    }
}
