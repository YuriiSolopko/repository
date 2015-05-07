package com.js.taxi.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Jura on 05.04.2015.
 */
@Entity
@Table(name = "ORDERS")
public class Order {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "ORDER_SEQ",
//            allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ORDER_ID")
    private Long orderId;

    @ManyToOne
    private Client client;

    @Column(name = "ORDER_DATE")
    private Calendar orderDate;

    @Column(name = "SUM")
    private Integer sum;

    @Column(name = "ADDRESS_FROM")
    private String addressFrom;

    @Column(name = "ADDRESS_TO")
    private String addressTo;

    public Order() {
    }

    public Order(Long id, Client client, Calendar orderDate, Integer sum, String addressFrom, String addressTo) {
        this.orderId = id;
        this.client = client;
        this.orderDate = orderDate;
        this.sum = sum;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOrderDate() {
        if (orderDate == null) {
            return "";
        }
        return DATE_FORMAT.format(orderDate.getTime());
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }
}
