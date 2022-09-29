package com.step.hryshkin.service.impl;

import com.step.hryshkin.dao.OrderDAO;
import com.step.hryshkin.model.Order;
import com.step.hryshkin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    //НУЖЕН. НО ПЕРЕДЕЛАТЬ
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createNewOrder(Order order) {
        orderDAO.createNewOrder(order);
    }
}
