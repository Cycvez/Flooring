/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.service;

import com.mycompany.flooring.dao.ExportDataDao;
import com.mycompany.flooring.dao.FlooringException;
import com.mycompany.flooring.dao.OrderNumberDao;
import com.mycompany.flooring.dao.OrdersDao;
import com.mycompany.flooring.dao.ProductDao;
import com.mycompany.flooring.dao.TaxesDao;
import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author carlo
 */
public class FlooringService {

    private OrdersDao ordersDao;
    private ProductDao productDao;
    private TaxesDao taxesDao;
    private OrderNumberDao numberDao;
    private ExportDataDao exportDao;

    FlooringService(OrdersDao ordersDao, ProductDao productDao, TaxesDao taxesDao,
            OrderNumberDao numberDao, ExportDataDao exportDao) {
        this.ordersDao = ordersDao;
        this.productDao = productDao;
        this.taxesDao = taxesDao;
        this.numberDao = numberDao;
        this.exportDao = exportDao;
    }

    public void checkDate(String date) throws InvalidInformationException, FlooringException {
        if (date.isEmpty() || date.length() != 8) {
            throw new InvalidInformationException("Invalid Input..."
                    + "returning to Main");
        }
    }

    public List<Order> ordersOnDate(String date) throws FlooringException {
        List<Order> ordersOnDate = ordersDao.searchOrderByDate(date);
        return ordersOnDate;
    }

    public List<Product> displayAllProducts() throws FlooringException {
        List<Product> productInformation = productDao.productList();
        return productInformation;
    }

    public int getOrderNumber() throws FlooringException {
        int orderNumber = numberDao.getOrderNumber();
        return orderNumber;
    }

    public Product getProductInformation(String productType) throws FlooringException {
        Product p = productDao.getProductInfo(productType);
        return p;
    }

    public Taxes getStateTaxesInformation(String state) throws FlooringException,InvalidInformationException {
       
        Taxes t = taxesDao.loadTaxRates(state);
        return t;
    }

    public Order calculateOrder(String date, int orderNumber, String customerName, Taxes t, Product p,
            BigDecimal area) {
        Order newOrder = new Order(orderNumber);
        BigDecimal materialCost = area.multiply(p.getCostPerSquareFoot());
        BigDecimal laborCost = area.multiply(p.getLaborCostPerSquareFoot());
        BigDecimal totalTax = (materialCost.add(laborCost))
                .add(t.getTaxRate().divide(new BigDecimal("100"), 2, RoundingMode.DOWN));
        BigDecimal totalCost = materialCost.add(laborCost.add(totalTax));

        newOrder.setOrderNumber(orderNumber);
        newOrder.setCustomerName(customerName);
        newOrder.setTaxes(t);
        newOrder.setProduct(p);
        newOrder.setArea(area);
        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTotalTax(totalTax);
        newOrder.setTotalCost(totalCost);
        newOrder.setTimeStamp(date);
        return newOrder;
    }

    public void saveNewOrder(Order newOrder) throws FlooringException {
        ordersDao.addOrder(newOrder);
    }

    public Order getOrder(String date, int orderNumber) throws FlooringException {
        Order editOrder = ordersDao.getOrderByOrderNum(date, orderNumber);
        editOrder.setTimeStamp(date);
        return editOrder;
    }

    public void saveEdittedOrder(Order editOrder) throws FlooringException {
        String date = editOrder.getTimeStamp();
        int orderNumber = editOrder.getOrderNumber();
        ordersDao.editOrder(date, orderNumber, editOrder);
    }

    public void removeOrder(Order removeOrder) throws FlooringException {
        String date = removeOrder.getTimeStamp();
        int orderNumber = removeOrder.getOrderNumber();
        ordersDao.removeOrder(date, orderNumber);
    }

    public void exportData() throws FlooringException {
        exportDao.exportData();
    }
}
