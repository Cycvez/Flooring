/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Taxes;

/**
 *
 * @author carlo
 */
public class TaxesDaoMock implements TaxesDao{

    @Override
    public Taxes loadTaxRates(String state) throws FlooringException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
