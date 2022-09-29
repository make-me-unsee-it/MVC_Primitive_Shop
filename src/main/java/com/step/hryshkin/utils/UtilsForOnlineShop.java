package com.step.hryshkin.utils;

import com.step.hryshkin.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UtilsForOnlineShop {

    private UtilsForOnlineShop() {
    }

    public static void setOrder(HttpServletRequest request, Order order) {
        request.getSession().setAttribute("order", order);
    }

    public static Order getOrder(HttpServletRequest request) {
        return ((Order) request.getSession().getAttribute("order"));
    }

    public static void setGoodListForCurrentOrder(HttpServletRequest request, List<String> goodsForCurrentOrder) {
        request.getSession().setAttribute("goodListForCurrentOrder", goodsForCurrentOrder);
    }

    public static void stopShopping(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
