package com.step.hryshkin.controllers;

import com.step.hryshkin.model.Good;
import com.step.hryshkin.model.Order;
import com.step.hryshkin.model.OrderGood;
import com.step.hryshkin.model.security.CustomUserDetails;
import com.step.hryshkin.service.GoodService;
import com.step.hryshkin.service.OrderGoodService;
import com.step.hryshkin.service.OrderService;
import com.step.hryshkin.service.UserService;
import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Alekx
 */

@Controller
public class ShopController {
    @Inject
    GoodService goodService;
    @Inject
    OrderGoodService orderGoodService;
    @Inject
    OrderService orderService;
    @Inject
    UserService userService;

    @GetMapping("/super")
    public String deleteThisMethod(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return "shop/super";
    }

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
            Long userId = userService.getUserByName(username).get().getId();
            long goodId = Long.parseLong(request.getParameter("select"));
            Optional<Good> currentGood = goodService.getById(goodId);
            if (currentGood.isPresent()) {
                if (request.getSession().getAttribute("order") != null) {
                    long currentOrderId = ((Order) request.getSession().getAttribute("order")).getId();
                    BigDecimal currentOrderTotalPrice =
                            ((Order) request.getSession().getAttribute("order")).getTotalPrice();
                    currentOrderTotalPrice = currentOrderTotalPrice.add(currentGood.get().getPrice());
                    Order order = new Order(currentOrderId, userId, currentOrderTotalPrice);
                    orderService.updateOrder(order);
                    UtilsForOnlineShop.setOrder(request, order);
                    OrderGood orderGood = new OrderGood(currentOrderId, goodId);
                    orderGoodService.createNewOrderGoodDAO(orderGood);
                }

                if (request.getSession().getAttribute("order") == null) {
                    Order order = new Order(userId, currentGood.get().getPrice());
                    orderService.createNewOrder(order);
                    Optional<Order> newestOrder = orderService.getLastOrder();
                    if (newestOrder.isPresent()) {
                        UtilsForOnlineShop.setOrder(request, newestOrder.get());
                        long orderId = newestOrder.get().getId();
                        OrderGood orderGood = new OrderGood(orderId, goodId);
                        orderGoodService.createNewOrderGoodDAO(orderGood);
                    }
                }
            }
            long id = ((Order) request.getSession().getAttribute("order")).getId();
            UtilsForOnlineShop.setGoodListForCurrentOrder(request, goodService.getGoodListByOrderId(id));
        }
    }

}
