package com.js.taxi.service;

import com.js.taxi.domain.Client;
import com.js.taxi.domain.Order;
import com.js.taxi.exception.OrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jura on 05.04.2015.
 */
public interface OrderService {

    public boolean createOrder(Long id, Client client, String amount, String addressFrom, String addressTo) throws OrderException;
    public void editOrder(Long id, Client client, String amount, String addressFrom, String addressTo);
    public List<Order> showOrders(Long from, Long to);
    public List<Order> showOrdersByPortion();
    public List<Order> showOrdersBySum(Integer from, Integer to);
    public List<Order> showOrdersByPortions(int firstIndex, int portionSize);
    public Long getOrderRowCount();
    public Order readOrder(Long id);
}
