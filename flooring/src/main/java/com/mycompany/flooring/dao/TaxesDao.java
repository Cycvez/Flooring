/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Taxes;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface TaxesDao {
    
    Taxes loadTaxRates(String state) throws FlooringException;
}
