/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author carlo
 */
public class ProductDaoImplTest {
    ProductDao dao= new ProductDaoImpl();
    
    public ProductDaoImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of productList method, of class ProductDaoImpl.
     */
    @Test
    public void testProductList() throws Exception {
       List<Product> productsFromDao=dao.productList();
       assertTrue(productsFromDao.size()==4);
       
    }

    /**
     * Test of getProductInfo method, of class ProductDaoImpl.
     */
    @Test
    public void testGetProductInfo() throws Exception {
        
    Product test= new Product();
    test.setCostPerSquareFoot(new BigDecimal("5.15"));
    test.setProductType("Wood");
    test.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        
        Product fromDao=dao.getProductInfo("Wood");
        assertEquals(fromDao,test);
    }
    
    
}
