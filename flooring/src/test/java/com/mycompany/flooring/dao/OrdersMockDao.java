/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlo
 */
public class OrdersMockDao implements OrdersDao {

    @Override
    public Order addOrder(Order newOrder) throws FlooringException {
        newOrder = testing1();
        return newOrder;
    }

    @Override
    public List<Order> searchOrderByDate(String date) throws FlooringException {
        if (date.equals("null")) {
            return null;
        }
        
        List<Order> orderList = new ArrayList<>();
        orderList.add(testing1());
        orderList.add(testing2());
        return orderList;

    }

    @Override
    public Order getOrderByOrderNum(String date, int orderNumber) throws FlooringException {
        if (date.equals("null")) {
            return null;
        }
        return new Order();
    }

    @Override
    public void removeOrder(String date, int orderNumber) throws FlooringException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editOrder(String date, int orderNumber, Order editOrder) throws FlooringException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrders() throws FlooringException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Order testing1() {

        Order test = new Order();
        test.setCustomerName("testONE");
        test.setOrderNumber(1);

        Product testP = new Product();
        testP.setCostPerSquareFoot(new BigDecimal("10.00"));
        testP.setLaborCostPerSquareFoot(new BigDecimal("10.00"));
        testP.setProductType("testProduct");

        Taxes testT = new Taxes();
        testT.setStateAbbreviations("TS");
        testT.setStateFullName("testState");
        testT.setTaxRate(new BigDecimal("10.00"));

        test.setProduct(testP);
        test.setTaxes(testT);
        test.setArea(new BigDecimal("10.00"));

        test.setMaterialCost(new BigDecimal("10.00"));
        test.setLaborCost(new BigDecimal("10.00"));
        test.setTotalTax(new BigDecimal("10.00"));
        test.setTotalCost(new BigDecimal("400.1000"));
        test.setTimeStamp("Test");

        return test;

    }

    public Order testing2() {

        Order test = new Order();
        test.setCustomerName("testTWO");
        test.setOrderNumber(2);

        Product testP = new Product();
        testP.setCostPerSquareFoot(new BigDecimal("20.00"));
        testP.setLaborCostPerSquareFoot(new BigDecimal("20.00"));
        testP.setProductType("secondOrder");

        Taxes testT = new Taxes();
        testT.setStateAbbreviations("ZZ");
        testT.setStateFullName("secondState");
        testT.setTaxRate(new BigDecimal("20.00"));

        test.setProduct(testP);
        test.setTaxes(testT);
        test.setArea(new BigDecimal("20.00"));

        test.setMaterialCost(new BigDecimal("20.00"));
        test.setLaborCost(new BigDecimal("20.00"));
        test.setTotalTax(new BigDecimal("20.00"));
        test.setTotalCost(new BigDecimal("20.00"));
        test.setTimeStamp("Test");

        return test;

    }

}
