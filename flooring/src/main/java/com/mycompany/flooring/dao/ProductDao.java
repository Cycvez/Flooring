/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Product;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface ProductDao {
    
    List<Product> productList() throws FlooringException;
    Product getProductInfo(String productType) throws FlooringException;
    
}
