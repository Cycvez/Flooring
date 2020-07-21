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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author carlo
 */
public class OrdersDaoImplTest {

    OrdersDao dao = new OrdersDaoImpl();

    public OrdersDaoImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {

    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        List<Order> orders = dao.searchOrderByDate("Test");
        for (Order order : orders) {
            dao.removeOrder("Test", order.getOrderNumber());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrdersDaoImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        dao.addOrder(testing1());
        dao.addOrder(testing2());

    }

    /**
     * Test of searchOrderByDate method, of class OrdersDaoImpl.
     */
    @Test
    public void testSearchOrderByDate() throws Exception {

        dao.addOrder(testing1());
        dao.addOrder(testing2());

        List<Order> orders = dao.searchOrderByDate("Test");
        assertEquals(2, orders.size());

    }

    /**
     * Test of removeOrder method, of class OrdersDaoImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        dao.addOrder(testing1());
        dao.addOrder(testing2());
        
        assertEquals(2, dao.searchOrderByDate("Test").size());

        dao.removeOrder("Test", 1);
        assertEquals(1, dao.searchOrderByDate("Test").size());

    }

    /**
     * Test of editOrder method, of class OrdersDaoImpl.
     */
    @Test
    public void testEditOrder() throws Exception {
        Order editOrder=dao.addOrder(testing2());
        editOrder.setCustomerName("EDIT");
        
        dao.editOrder("Test", editOrder.getOrderNumber(), editOrder);
        
        assertEquals("EDIT",editOrder.getCustomerName());
    }

    /**
     * Test of getOrderByOrderNum method, of class OrdersDaoImpl.
     */
    @Test
    public void testGetOrderByOrderNum() throws Exception {
        dao.addOrder(testing1());
        dao.addOrder(testing2());

        Order test = dao.getOrderByOrderNum("Test", 1);
        assertEquals(test.getCustomerName(), "testONE");
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
        test.setTotalCost(new BigDecimal("10.00"));
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
