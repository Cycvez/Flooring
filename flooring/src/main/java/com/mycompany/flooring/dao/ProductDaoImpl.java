/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Product;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao {

    public static final String FILE = "Data/products.txt";
    public static final String DELIMITER = ",";

    private Map<String, Product> productList = new HashMap<>();

    private String marshallProduct(Product product) {
        String productAsText = product.getProductType() + DELIMITER;
        productAsText += product.getCostPerSquareFoot() + DELIMITER;
        productAsText += product.getLaborCostPerSquareFoot();
        return productAsText;
    }

    private Product unMarshallProduct(String productAsText) {
        String[] productInfoPart = productAsText.split(DELIMITER);
        String productType = productInfoPart[0];

        Product productFromFile = new Product(productType);

        productFromFile.setCostPerSquareFoot(new BigDecimal(productInfoPart[1]));
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productInfoPart[2]));

        return productFromFile;
    }

    private void loadProductFile() throws FlooringException {
        Scanner sc;

        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringException("Could Not Load File :( ", ex);
        }

        String currentLine;
        Product currentProduct;

        while (sc.hasNextLine()) {

            currentLine = sc.nextLine();
            if (currentLine.startsWith("ProductType")) {
                currentLine = sc.nextLine();
            }
            currentProduct = unMarshallProduct(currentLine);
            productList.put(currentProduct.getProductType(), currentProduct);
        }
        sc.close();
    }

    @Override
    public List<Product> productList() throws FlooringException {
        loadProductFile();
        return new ArrayList<>(productList.values());
    }
    @Override
    public Product getProductInfo(String productType) throws FlooringException{
        loadProductFile();
        return productList.get(productType);

    }

}
