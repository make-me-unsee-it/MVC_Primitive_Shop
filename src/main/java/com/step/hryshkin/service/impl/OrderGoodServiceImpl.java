package com.step.hryshkin.service.impl;

import com.step.hryshkin.dao.OrderGoodDAO;
import com.step.hryshkin.service.OrderGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderGoodServiceImpl implements OrderGoodService {

    private final OrderGoodDAO orderGoodDAO;

    @Autowired
    public OrderGoodServiceImpl(OrderGoodDAO orderGoodDAO) {
        this.orderGoodDAO = orderGoodDAO;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createNewOrderGoodDAO() {
        orderGoodDAO.createNewOrderGoodDAO();
    }
}
