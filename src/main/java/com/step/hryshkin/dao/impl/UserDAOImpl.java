package com.step.hryshkin.dao.impl;

import com.step.hryshkin.dao.UserDAO;
import com.step.hryshkin.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //TODO уже исправлен! Наверное
    @Override
    public void createNewUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    //TODO уже исправлен!
    @Override
    public Optional<User> getUserByName(String name) {
        Optional<User> user;
        Session session = sessionFactory.getCurrentSession();
        user = session.createQuery("FROM User WHERE userName =:userName", User.class)
                .setParameter("userName", name).uniqueResultOptional();
        return user;
    }
}
