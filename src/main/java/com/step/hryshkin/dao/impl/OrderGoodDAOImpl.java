package com.step.hryshkin.dao.impl;

import com.step.hryshkin.dao.OrderGoodDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class OrderGoodDAOImpl implements OrderGoodDAO {
    private static final Logger LOGGER = LogManager.getLogger(OrderGoodDAOImpl.class);

    @Override
    public void createNewOrderGoodDAO() {
        //TODO фундаментально переделать
        /*
        try (Connection connection = ConnectCreator.getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO ORDERGOODS (ORDERID, GOODID) values (?,?)")) {
                statement.setLong(1, orderGood.getOrderId());
                statement.setLong(2, orderGood.getGoodId());
                statement.executeUpdate();
            }
        } catch (SQLException throwable) {
            LOGGER.error("SQLException at OrderGoodDAOImpl at CreateNewOrderGood" + throwable);
        }

         */
    }
}
