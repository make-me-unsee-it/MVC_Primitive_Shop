package com.step.hryshkin.controllers;

import com.step.hryshkin.model.Good;
import com.step.hryshkin.model.Order;
import com.step.hryshkin.model.User;
import com.step.hryshkin.security.model.CustomUserDetails;
import com.step.hryshkin.service.GoodService;
import com.step.hryshkin.service.UserService;
import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alekx
 */

@Controller
public class ShopController {
    @Inject
    private GoodService goodService;

    @Inject
    private UserService userService;

    @GetMapping("/shop")
    public String loginPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            Model model, ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        checkForNewOrder((HttpServletRequest) servletRequest, customUserDetails.getUsername());
        model.addAttribute("user", customUserDetails.getUsername());
        model.addAttribute("goods", goodService.getAll());
        if (request.getSession().getAttribute("goodListForCurrentOrder") != null) {
            model.addAttribute("currentList", request
                    .getSession()
                    .getAttribute("goodListForCurrentOrder"));
            return "shop/shop_second";
        } else return "shop/shop_first";
    }

    private void checkForNewOrder(HttpServletRequest request, String username) {
        if (request.getParameter("select") != null) {
            User user = userService.getUserByName(username).get();
            long goodId = Long.parseLong(request.getParameter("select"));
            Optional<Good> currentGood = goodService.getById(goodId);
            if (currentGood.isPresent()) {

                if (request.getSession().getAttribute("order") != null) {
                    Order order = UtilsForOnlineShop.getOrder(request);
                    order.addGood(currentGood.get());

                    order.setTotalPrice(order.getTotalPrice().add(currentGood.get().getPrice()));

                    UtilsForOnlineShop.setOrder(request, order);
                }

                if (request.getSession().getAttribute("order") == null) {
                    Order order = new Order(user, currentGood.get().getPrice());
                    order.addGood(currentGood.get());
                    UtilsForOnlineShop.setOrder(request, order);
                }
            }

            List<Good> currentGoodList = UtilsForOnlineShop.getOrder(request).getGoods();
            List<String> resultBasket = new ArrayList<>();
            for (Good goodInBasket: currentGoodList) {
                resultBasket.add(goodInBasket.getTitle() + " " + goodInBasket.getPrice() + "$");
            }
            UtilsForOnlineShop.setGoodListForCurrentOrder(request, resultBasket);
        }
    }

}
