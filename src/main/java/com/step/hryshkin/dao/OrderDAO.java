package com.step.hryshkin.dao;

import com.step.hryshkin.model.Order;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderDAO {

    void createNewOrder(Order order);

    /*TODO ВЫПИЛИТЬ. УЖЕ НЕ ИСПОЛЬЗУЕТСЯ
    void updateOrder(Order order);
    BigDecimal getTotalPriceByOrderId(long id);
    Optional<Order> getLastOrder();
     */
}
