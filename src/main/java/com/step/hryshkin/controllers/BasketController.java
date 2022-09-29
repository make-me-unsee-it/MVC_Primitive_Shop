package com.step.hryshkin.controllers;

import com.step.hryshkin.model.Good;
import com.step.hryshkin.model.Order;
import com.step.hryshkin.security.model.CustomUserDetails;
import com.step.hryshkin.service.OrderService;
import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

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

        List<Good> currentGoodList = UtilsForOnlineShop.getOrder(request).getGoods();
        BigDecimal resultBasket = new BigDecimal("0");
        for (Good goodInBasket : currentGoodList) {
            resultBasket = resultBasket.add(goodInBasket.getPrice());
        }
        model.addAttribute("totalPrice", resultBasket.toString());

        orderService.createNewOrder((Order) request.getSession().getAttribute("order"));

        return "basket/basket";
    }
}
