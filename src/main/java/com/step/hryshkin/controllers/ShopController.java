package com.step.hryshkin.controllers;

import com.step.hryshkin.model.Good;
import com.step.hryshkin.model.Order;
import com.step.hryshkin.model.OrderGood;
import com.step.hryshkin.model.User;
import com.step.hryshkin.service.GoodService;
import com.step.hryshkin.service.OrderGoodService;
import com.step.hryshkin.service.OrderService;
import com.step.hryshkin.service.UserService;
import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Alekx
 */

@Controller
public class ShopController {
    private static final Logger LOGGER = LogManager.getLogger(ShopController.class);
    @Inject
    GoodService goodService;
    @Inject
    OrderGoodService orderGoodService;
    @Inject
    OrderService orderService;
    @Inject
    UserService userService;

    @GetMapping("/shop")
    public String loginPage(Model model, ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        checkUser((HttpServletRequest) servletRequest);
        // TODO добавить чекфлаг. Переписать связанный метод
        checkForNewOrder((HttpServletRequest) servletRequest);
        model.addAttribute("user", ((User) request
                .getSession()
                .getAttribute("user"))
                .getLogin());
        model.addAttribute("goods", goodService.getAll());
        if (request.getSession().getAttribute("goodListForCurrentOrder") != null) {
            model.addAttribute("currentList", request
                    .getSession()
                    .getAttribute("goodListForCurrentOrder"));
            return "shop/shop_second";
        } else return "shop/shop_first";
    }

    private void checkUser(HttpServletRequest request) {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(login, password);
        if (login != null) {
            if (userService.getUserByName(login).isEmpty()) {
                userService.createNewUser(user);
            }
            Optional<User> newUser = userService.getUserByName(login);
            if (newUser.isPresent()) {
                if (request.getSession().getAttribute("user") == null) {
                    UtilsForOnlineShop.setUser(request, newUser.get());
                } else if (!UtilsForOnlineShop.isUsersEquals(request)) {
                    request.getSession().invalidate();
                    UtilsForOnlineShop.setUser(request, newUser.get());
                }
            }
        }
    }

    private void checkForNewOrder(HttpServletRequest request) {
        if (request.getParameter("select") != null) {
            Long userId = ((User) request.getSession().getAttribute("user")).getId();
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

    // TODO переписать!
    private void checkFlag(ServletResponse servletResponse, HttpServletRequest request) {
        if (request.getSession().getAttribute("check") == null) {
            if (request.getParameter("check") != null) {
                UtilsForOnlineShop.setCheckStatus(request, request.getParameter("check"));
            } else {
                String path = "/terms_of_use_error.jsp";
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
                try {
                    requestDispatcher.forward(request, servletResponse);
                } catch (ServletException exception) {
                    LOGGER.error("ServletException in checkFlag " + exception);
                } catch (IOException exception) {
                    LOGGER.error("IOException in checkFlag " + exception);
                }
            }
        }
    }
}
