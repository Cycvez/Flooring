/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Order;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface OrdersDao {

    Order addOrder(Order newOrder) throws FlooringException;

    List<Order> searchOrderByDate(String date) throws FlooringException;

    Order getOrderByOrderNum(String date, int orderNumber) throws FlooringException;

    void removeOrder(String date, int orderNumber) throws FlooringException;

    void editOrder(String date, int orderNumber,Order editOrder) throws FlooringException;

    List<Order> getAllOrders() throws FlooringException;
}
