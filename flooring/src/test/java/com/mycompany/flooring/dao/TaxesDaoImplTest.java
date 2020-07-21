/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Taxes;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author carlo
 */
public class TaxesDaoImplTest {
    
    TaxesDao dao= new TaxesDaoImpl();
    
    public TaxesDaoImplTest() {
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
     * Test of loadTaxRates method, of class TaxesDaoImpl.
     */
    @Test
    public void testLoadTaxRates() throws Exception {
        Taxes test= new Taxes();
        String state="TX";
        String fullState="Texas";
        BigDecimal taxRates=new BigDecimal("4.45");
        
        test.setStateAbbreviations(state);
        test.setStateFullName(fullState);
        test.setTaxRate(taxRates);
        
        Taxes fromDao=dao.loadTaxRates("TX");
        
        assertEquals(fromDao,test);
    }
    
}
