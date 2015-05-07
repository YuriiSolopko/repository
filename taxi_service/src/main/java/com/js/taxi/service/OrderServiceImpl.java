package com.js.taxi.service;

import com.js.taxi.dao.ClientDao;
import com.js.taxi.dao.OrderDao;
import com.js.taxi.domain.Client;
import com.js.taxi.domain.Order;
import com.js.taxi.exception.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Jura on 05.04.2015.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ClientDao clientDao;

    @Override
    public boolean createOrder(Long id, Client client, String amount, String addressFrom, String addressTo) throws OrderException {

        if(orderDao.read(id) != null) {
            throw new OrderException("Order with such ID already exists");
        }
        Integer sum = 0;
        try {
            sum = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            throw new OrderException(e.getMessage());
        }
        Calendar date = new GregorianCalendar();
        Order order = new Order(id, client, date, sum, addressFrom, addressTo);
        client.setLastOrderDate(date);
        if(client.getSum()!=null) {
            client.setSum(client.getSum() + sum);
        } else {
            client.setSum(sum);
        }
        orderDao.create(order);
        clientDao.update(client);
        return true;
    }

    @Override
    public void editOrder(Long id, Client client, String amount, String addressFrom, String addressTo) {
        Order order = orderDao.read(id);
        if (client != null) {
            order.setClient(client);
        }
        if (amount != null || !amount.equals("")) {
            Integer sum = Integer.parseInt(amount);
            if (order.getSum() != sum ) {
//                client.setSum(client.getSum() - order.getSum() + sum);
//                clientDao.update(client);
                Client updClient = clientDao.read(client.getClientId());
                updClient.setSum(client.getSum() - order.getSum() + sum);
                clientDao.update(updClient);
            }
            order.setSum(sum);
        }
        if (addressFrom != null || !addressFrom.equals("")) {
            order.setAddressFrom(addressFrom);
        }
        if (addressTo != null || !addressTo.equals("")) {
            order.setAddressTo(addressTo);
        }
        orderDao.update(order);
    }

    @Override
    public List<Order> showOrders(Long from, Long to) {
        return orderDao.findAll();
    }

    @Override
    public List<Order> showOrdersBySum(Integer from, Integer to) {
        return orderDao.findOrdersBySum(from, to);
    }

    @Override
    public List<Order> showOrdersByPortion() {
        return orderDao.findPortion(0,5);
    }

    @Override
    public List<Order> showOrdersByPortions(int firstIndex, int portionSize) {
        return orderDao.findPortion(firstIndex, portionSize);
    }

    @Override
    public Long getOrderRowCount() {
        return orderDao.rowCount();
    }

    @Override
    public Order readOrder(Long id) {
        return orderDao.read(id);
    }
}
