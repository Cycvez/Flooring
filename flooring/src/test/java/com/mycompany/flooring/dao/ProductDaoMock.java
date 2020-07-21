/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Product;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author carlo
 */
public class ProductDaoMock implements ProductDao {
     private Map<String, Product> productList = new HashMap<>();

    @Override
    public List<Product> productList() throws FlooringException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getProductInfo(String productType) throws FlooringException {
        if (productType.equals("null")) {
            return null;
        }
        Product p = new Product();
        p.setProductType(productType);
        p.setCostPerSquareFoot(BigDecimal.ZERO);
        p.setLaborCostPerSquareFoot(BigDecimal.ZERO);
        return p;
    }

}
