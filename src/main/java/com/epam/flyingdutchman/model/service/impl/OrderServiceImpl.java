package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.model.dao.OrderDao;
import com.epam.flyingdutchman.model.dao.impl.OrderDaoImpl;
import com.epam.flyingdutchman.model.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao = OrderDaoImpl.getInstance();


}
