/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.controller;

import com.mycompany.flooring.dao.FlooringException;
import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import com.mycompany.flooring.service.FlooringService;
import com.mycompany.flooring.service.InvalidInformationException;
import com.mycompany.flooring.ui.FlooringDisplay;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author carlo
 */
public class FlooringController {

    private FlooringDisplay display;
    private FlooringService service;

    public FlooringController(FlooringDisplay display,
            FlooringService service) {
        this.display = display;
        this.service = service;
    }

    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            display.mainMenu();
            try {
                int selection = display.getSelection();
                switch (selection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;

                    case 6:
                        keepRunning = false;
                        break;
                }
            } catch (InvalidInformationException | NumberFormatException | FlooringException ex) {
                display.errorMessage(ex.getMessage());
            }
        }

    }

    public void displayOrders() throws InvalidInformationException, FlooringException {
        String date = display.getDisplayOrdersDate();
        service.checkDate(date);
        //service.OrdersOnDate Returns a List<Order> 
        //displayOrders turns that list of orders int a printable string
        display.displayOrders(service.ordersOnDate(date));
    }

    public void addOrder() throws FlooringException, InvalidInformationException {
        String date;
        int orderNumber;
        String customerName;
        Taxes t;
        String productType;
        BigDecimal area;
        Order newOrder;

        date = display.addOrderDisplay();
        orderNumber = service.getOrderNumber();
        customerName = display.getCustomerName();
        String userState = display.getState();
        String state = userState.toUpperCase();

//        if (state.length() != 2) {
//            display.incorrectStateInput();
//        } else {
        try {
            t = service.getStateTaxesInformation(state);
            while (t == null) {
                display.incorrectStateInput();
                userState = display.getState();
                state = userState.toUpperCase();
                t = service.getStateTaxesInformation(state);
            }

            List<Product> products = service.displayAllProducts();
            productType = display.displayAndGetProductTypes(products);
            Product p = service.getProductInformation(productType);

//                while(p==null){
//                
//                }
            while (!products.contains(p)) {
                display.productUnavailable();
                products = service.displayAllProducts();
                productType = display.displayAndGetProductTypes(products);
                p = service.getProductInformation(productType);

            }
            area = display.getArea();
            if (area.compareTo(new BigDecimal("150.00")) == -1) {
                display.areaNotValid();
            } else {
                newOrder = service.calculateOrder(date, orderNumber, customerName, t, p, area);
                display.displayCurrentOrders(newOrder);

                int select = display.displayAddOrderFinal();
                switch (select) {
                    case 1:
                        service.saveNewOrder(newOrder);
                        break;
                    case 2:
                        break;
                    default:
                        display.unkownCommand();
                }
            }
        } catch (InvalidInformationException ex) {
            display.errorMessage(ex.getMessage());
        }
//        }
    }

    public void editOrder() throws InvalidInformationException, FlooringException {
        String date = display.displayEditDate();
        service.checkDate(date);
        int orderNumber = display.getOrderNumber();
        Order editOrder = service.getOrder(date, orderNumber);
        display.displayCurrentOrders(editOrder);

        boolean keepRunning = true;
        while (keepRunning) {
            display.editOptions();
            int editSelection = display.getEditSelection();
            switch (editSelection) {
                case 1:
                    editCustomerName(editOrder);
                    break;
                case 2:
                    editState(editOrder);
                    break;
                case 3:
                    editProductType(editOrder);
                    break;
                case 4:
                    editArea(editOrder);
                    break;
                case 5:
                    keepRunning = saveEditOrder(editOrder);
                    break;

                case 6:
                    keepRunning = false;
                    break;
            }

        }

    }

    public void editCustomerName(Order editOrder) {
        Order newOrder = editOrder;

        String newName = display.getCustomerName();
        newOrder.setCustomerName(newName);
        display.displayCurrentOrders(newOrder);
    }

    public void editState(Order editOrder) throws InvalidInformationException, FlooringException {
        Order newOrder = editOrder;

        Taxes t;
        String userState = display.getState();
        String state = userState.toUpperCase();
        t = service.getStateTaxesInformation(state);

        newOrder.setTaxes(t);
        int orderNumber = newOrder.getOrderNumber();
        String date = newOrder.getTimeStamp();
        String customerName = newOrder.getCustomerName();
        Product p = newOrder.getProduct();
        BigDecimal area = newOrder.getArea();
        newOrder = service.calculateOrder(date, orderNumber, customerName, t, p, area);

        display.displayCurrentOrders(newOrder);
    }

    public void editProductType(Order editOrder) throws InvalidInformationException, FlooringException {
        Order newOrder = editOrder;

        Product p;
        List<Product> products = service.displayAllProducts();
        String productType = display.displayAndGetProductTypes(products);
        p = service.getProductInformation(productType);

        newOrder.setProduct(p);
        int orderNumber = newOrder.getOrderNumber();
        String date = newOrder.getTimeStamp();
        String customerName = newOrder.getCustomerName();
        Taxes t = newOrder.getTaxes();
        BigDecimal area = newOrder.getArea();
        newOrder = service.calculateOrder(date, orderNumber, customerName, t, p, area);

        display.displayCurrentOrders(newOrder);
    }

    public void editArea(Order editOrder) throws InvalidInformationException, FlooringException {
        Order newOrder = editOrder;

        BigDecimal area = display.getArea();
        newOrder.setArea(area);

        int orderNumber = newOrder.getOrderNumber();
        String customerName = newOrder.getCustomerName();
        Taxes t = newOrder.getTaxes();
        Product p = newOrder.getProduct();
        String date = newOrder.getTimeStamp();

        newOrder = service.calculateOrder(date, orderNumber, customerName, t, p, area);

        display.displayCurrentOrders(newOrder);
    }

    public boolean saveEditOrder(Order editOrder) throws InvalidInformationException, FlooringException {
        display.displayCurrentOrders(editOrder);
        int select = display.saveEdittedOrder();
        switch (select) {
            case 1:
                service.saveEdittedOrder(editOrder);
                return false;
            case 2:
                return true;
            default:
                display.unkownCommand();
                return true;

        }

    }

    public void removeOrder() throws InvalidInformationException, FlooringException {
        String date = display.displayRemoveDate();
        service.checkDate(date);
        int orderNumber = display.getOrderNumber();
        Order removeOrder = service.getOrder(date, orderNumber);
        display.displayCurrentOrders(removeOrder);

        int select = display.removeOrder();
        switch (select) {
            case 1:
                service.removeOrder(removeOrder);
                break;
            case 2:
                break;
            default:
                display.unkownCommand();
                break;

        }

    }

    public void exportData() throws InvalidInformationException, FlooringException {
        int select = display.exportData();
        switch (select) {
            case 1:
                service.exportData();
                break;
            case 2:
                break;
            default:
                display.unkownCommand();
                break;

        }
    }
}

//Switch for addOrder
//    public void addOrder() throws InvalidInformationException, FlooringException {
//
//        boolean keepRunning = true;
//        String date;
//        String customerName;
//        Taxes t;
//        String productType;
//        BigDecimal area;
//        Order newOrder;
//        
//        while (keepRunning) {
//            int steps = 1;
//            switch (steps) {
//                case 1://generate date information
//                     date = display.addOrderDisplay();
//                    break;
//                case 2://get customer name
//                     customerName=display.getCustomerName();
//                    break;
//                case 3://get state taxes information
//                    String state = display.getState();
//                     t= service.getStateTaxesInformation(state);
//                    break;
//                case 4://get product information
//                    List<Product> products=service.displayAllProducts();
//                     productType = display.displayAndGetProductTypes(products);
//                    Product p=service.getProductInformation(productType);
//                    break;
//                case 5://get area information
//                     area=display.getArea();
//                    break;
//                    
//                case 6: //calculate new Order
//                     newOrder=service.calculateOrder(date,10, customerName, t, p, area);
//                    display.displayCurrentOrders(newOrder);
//                    break;
//                case 7:
//            }
//            steps++;
//        }
//        //Date as string below
//        String date = display.addOrderDisplay();
//        Order newOrder = display.getNewOrderInfo(10, service.productInformation());
//
//    }

