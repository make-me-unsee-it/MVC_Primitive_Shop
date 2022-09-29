package com.step.hryshkin.dao.impl;

import com.step.hryshkin.dao.GoodDAO;
import com.step.hryshkin.model.Good;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GoodDAOImpl implements GoodDAO {
    private static final Logger LOGGER = LogManager.getLogger(GoodDAOImpl.class);

    private final SessionFactory sessionFactory;

    public GoodDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Good> getAll() {
        Optional<List<Good>> goodList;
        goodList = Optional.of(sessionFactory.getCurrentSession()
                .createQuery("FROM Good").list());
        if (goodList.isPresent()) return goodList.get();
        else {
            LOGGER.error("HibernateException at getAll: goodList is null");
            return null;
        }
    }

    @Override
    public Optional<Good> getById(long id) {
        Optional<Good> good;
        good = sessionFactory.getCurrentSession()
                .createQuery("FROM Good WHERE id =:id", Good.class)
                .setParameter("id", id)
                .uniqueResultOptional();
        return good;
    }
}

