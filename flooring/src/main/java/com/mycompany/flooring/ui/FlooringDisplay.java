/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.ui;

import com.mycompany.flooring.dao.FlooringException;
import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

/**
 *
 * @author carlo
 */
public class FlooringDisplay {

    public FlooringDisplay(UserIO io) {
        this.io = io;
    }

    private UserIO io;

    public void mainMenu() {
        io.print("****************************");
        io.print("*    <<Main Menu>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add Order");
        io.print("* 3. Edit Existing Order");
        io.print("* 4. Remove Order");
        io.print("* 5. Export all Orders");
        io.print("* 6. Quit");
        io.print("*");
        io.print("****************************\n");
    }

    public int getSelection() {
        return io.readInt("Please Make a Selection", 1, 6);
    }

    public void unkownCommand() {
        io.print("UnkownCommand");
        io.print("Returning to Main Menu");
    }

    public void errorMessage(String message) {
        io.print("ERROR: " + message);
    }

    public String getDisplayOrdersDate() {
        io.print("== Display Orders ==");
//        try {
        String date = io.readString("Enter Date for Orders you wish to Display \n[MMDDYYY]");
//        } catch (NumberFormatException ex) {
//            io.print("thats not a valid number");
//        }
        return date;
    }

    public void displayOrders(List<Order> ordersOnDate) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area"
                + ",CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for (Order currentOrder : ordersOnDate) {
            String orderInfo = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                    currentOrder.getOrderNumber(),
                    currentOrder.getCustomerName(),
                    currentOrder.getTaxes().getStateAbbreviations(),
                    currentOrder.getTaxes().getTaxRate(),
                    currentOrder.getProduct().getProductType(),
                    currentOrder.getArea(),
                    currentOrder.getProduct().getCostPerSquareFoot(),
                    currentOrder.getProduct().getLaborCostPerSquareFoot(),
                    currentOrder.getMaterialCost(),
                    currentOrder.getLaborCost(),
                    currentOrder.getTotalTax(),
                    currentOrder.getTotalCost());

            io.print(orderInfo);
        }
        io.readString("Please hit enter to continue.");

    }

    public String addOrderDisplay() {
        io.print("==Add New Order==");
        LocalDate date = LocalDate.now();
        String formatted = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        io.print(formatted);
        String orderDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        io.print("Your Order Date Will Be: " + orderDate);
        return orderDate;
    }

    public void incorrectStateInput() {
        io.print("Sorry that is not a valid state");
    }
    
    public void productUnavailable(){
        io.print("Sorry That Product is not available");
    }

    public String displayAndGetProductTypes(List<Product> productsAvailable) {
        io.print("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");
        for (Product currentProduct : productsAvailable) {
            String productInfo = String.format("%s %s %s",
                    currentProduct.getProductType(),
                    currentProduct.getCostPerSquareFoot(),
                    currentProduct.getLaborCostPerSquareFoot());

            io.print(productInfo);
        }
        String productType = io.readString("Which Product are you Interested in?");
        return productType;
    }

//    public Order getNewOrderInfo(int orderNumber, List<Product> productsAvailable) {
//        io.print("Your Order Number Will Be: " + orderNumber);
//        String customerName = io.readString("Please Enter Customer Name:");
//        String state = io.readString("Please Enter State");
//        String productType = displayAndGetProductTypes(productsAvailable);
//        String area = io.readString("Please enter the area of floor");
//
//        Order newOrder = new Order(orderNumber);
//
//        newOrder.setOrderNumber(orderNumber);
//        newOrder.setCustomerName(customerName);
////Taxes Info
//        Taxes t = new Taxes();
//        if (state.length() > 2) {
//            t.setStateAbbreviations(state);
//        } else {
//            t.setStateFullName(state);
//        }
//        newOrder.setTaxes(t);
////Product Info
//        Product p = new Product(productType);
//        newOrder.setProduct(p);
//        newOrder.setArea(new BigDecimal(area));
//
//        return newOrder;
//    }
    public String getCustomerName() {
        String customerName = io.readString("Please Enter Customer Name:");
        return customerName;
    }

    public String getState() {
        String state = io.readString("Please Enter State");
        return state;
    }

    public BigDecimal getArea() {
        String areaInString = io.readString("Please enter the area of floor");
        BigDecimal area = new BigDecimal(areaInString);
        return area;
    }

    public void displayCurrentOrders(Order currentOrder) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area"
                + ",CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

        String orderInfo = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                currentOrder.getOrderNumber(),
                currentOrder.getCustomerName(),
                currentOrder.getTaxes().getStateAbbreviations(),
                currentOrder.getTaxes().getTaxRate(),
                currentOrder.getProduct().getProductType(),
                currentOrder.getArea(),
                currentOrder.getProduct().getCostPerSquareFoot(),
                currentOrder.getProduct().getLaborCostPerSquareFoot(),
                currentOrder.getMaterialCost(),
                currentOrder.getLaborCost(),
                currentOrder.getTotalTax(),
                currentOrder.getTotalCost());

        io.print(orderInfo);
    }
    
    public void areaNotValid(){
        io.print("Area needs to be at least 150 Ft");
    }

    public int displayAddOrderFinal() {
        io.print("Would You like to Place Order?");
        return io.readInt("1:YES   2:NO", 1, 2);
    }

    public String displayEditDate() {
        io.print("==Edit Order==");
        String date = io.readString("Enter Date for Orders you wish to Edit \n[MMDDYYY]");
        return date;
    }

    public int getOrderNumber() {
        return io.readInt("Please Enter OrderNumber");
    }

    public void editOptions() {
        io.print("----------------------------");
        io.print("    <<Edit Options>>");
        io.print("  What would you like to Edit?");
        io.print("  1) Edit Customer Name");
        io.print("  2) Edit State");
        io.print("  3) Edit Product Type");
        io.print("  4) Edit Area");
        io.print("  5) Save new Order");
        io.print("  6) Exit Edit");
        io.print("----------------------------\n");
    }

    public int getEditSelection() {
        return io.readInt("Please Make a Selection", 1, 6);

    }

    public int saveEdittedOrder() {
        io.print("Would You save this editted Order?");
        return io.readInt("1:YES   2:NO", 1, 2);
    }

    public String displayRemoveDate() {
        io.print("==Remove Order==");
        String date = io.readString("Enter Date for Order you wish to Remove \n[MMDDYYY]");
        return date;
    }

    public int removeOrder() {
        io.print("Would You like to remove this Order?");
        return io.readInt("1:YES   2:NO", 1, 2);
    }

    public int exportData() {
        io.print("Would You like to Export all Active Orders?");
        return io.readInt("1:YES   2:NO", 1, 2);
    }

}
