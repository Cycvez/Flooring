/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.service;

import com.mycompany.flooring.dao.ExportDataDao;
import com.mycompany.flooring.dao.ExportDataDaoImpl;
import com.mycompany.flooring.dao.FlooringException;
import com.mycompany.flooring.dao.OrderNumberDao;
import com.mycompany.flooring.dao.OrderNumberDaoImpl;
import com.mycompany.flooring.dao.OrdersDao;
import com.mycompany.flooring.dao.OrdersMockDao;
import com.mycompany.flooring.dao.ProductDao;
import com.mycompany.flooring.dao.ProductDaoImpl;
import com.mycompany.flooring.dao.TaxesDao;
import com.mycompany.flooring.dao.TaxesDaoImpl;
import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author carlo
 */
public class FlooringServiceTest {

    OrdersDao dao = new OrdersMockDao();
    ProductDao pDao = new ProductDaoImpl();
    TaxesDao tDao = new TaxesDaoImpl();
    OrderNumberDao numDao = new OrderNumberDaoImpl();
    ExportDataDao dataDao = new ExportDataDaoImpl();

    FlooringService service = new FlooringService(dao, pDao, tDao, numDao, dataDao);

    public FlooringServiceTest() {
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
     * Test of ordersOnDate method, of class FlooringService.
     */
    @Test
    public void testOrdersOnDate() throws Exception {

        try {
            service.ordersOnDate("Test");
        } catch (FlooringException ex) {
            fail();
        }
    }

    /**
     * Test of displayAllProducts method, of class FlooringService.
     */
    @Test
    public void testDisplayAllProducts() throws Exception {
        List<Product> testingP = service.displayAllProducts();

        assertTrue(testingP.size() > 0);
    }

    /**
     * Test of getProductInformation method, of class FlooringService.
     */
    @Test
    public void testGetProductInformation() throws Exception {
        try {
            service.getProductInformation("Pass");
        } catch (FlooringException ex) {
            fail();
        }
        try {
            service.getProductInformation("Pass");
        } catch (FlooringException ex) {
            fail();
        }
    }

    /**
     * Test of getStateTaxesInformation method, of class FlooringService.
     */
    @Test
    public void testGetStateTaxesInformation() throws Exception {
        Taxes tx = service.getStateTaxesInformation("TX");

        assertEquals(tx.getStateAbbreviations(), "TX");

    }

    /**
     * Test of calculateOrder method, of class FlooringService.
     */
    @Test
    public void testCalculateOrder() throws Exception{
       Order testCalc= new Order();
       testCalc= dao.addOrder(testCalc);
       Taxes t= testCalc.getTaxes();
       Product p= testCalc.getProduct();
       Order results = service.calculateOrder("Test", 0, "testONE", t, p, new BigDecimal("10.00"));
       
       assertEquals(results.getCustomerName(),testCalc.getCustomerName()); 
       assertEquals(results.getTaxes().getTaxRate(), testCalc.getTaxes().getTaxRate());
       assertEquals(results.getTotalCost(),testCalc.getTotalCost());
        
        
    }

    /**
     * Test of getOrder method, of class FlooringService.
     */
    @Test
    public void testGetOrder() throws Exception {
        Order test = service.getOrder("Test", 0);
        assertEquals(test.getOrderNumber(), 0);
        assertEquals(test.getTimeStamp(), "Test");
    }
}
