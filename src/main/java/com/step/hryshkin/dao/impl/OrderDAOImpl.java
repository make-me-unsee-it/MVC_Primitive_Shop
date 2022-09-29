package com.step.hryshkin.dao.impl;

import com.step.hryshkin.dao.OrderDAO;
import com.step.hryshkin.model.Order;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final SessionFactory sessionFactory;

    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createNewOrder(Order order) {
        sessionFactory.getCurrentSession().save(order);
    }
}


