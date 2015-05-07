package com.js.taxi.dao;

import com.js.taxi.domain.Order;

import java.util.List;

/**
 * Created by Jura on 05.04.2015.
 */
public interface OrderDao {
    public Long create(Order order);
    public Order read(Long id);
    public void update(Order order);
    public void delete(Order order);
    public List<Order> findAll();
    public List<Order> findOrdersBySum(Integer from, Integer to);
    public List<Order> findPortion(int firstResult, int maxResults);
    public Long rowCount();
}
