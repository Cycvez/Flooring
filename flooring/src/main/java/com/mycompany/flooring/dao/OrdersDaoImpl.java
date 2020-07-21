/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class OrdersDaoImpl implements OrdersDao {

    private static final String DELIMITER = ",";
    // private final DateTimeFormatter FORMAT_MMDDYYYY =
    // DateTimeFormatter.ofPattern("MMddyyyy");

    private String FILE;

    public OrdersDaoImpl() {

    }

    Map<Integer, Order> orders = new HashMap<>();

    private String marshallOrder(Order order) {
        String orderAsText = order.getOrderNumber() + DELIMITER;
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getTaxes().getStateAbbreviations() + DELIMITER;
        orderAsText += order.getTaxes().getTaxRate() + DELIMITER;
        orderAsText += order.getProduct().getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getProduct().getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTotalTax() + DELIMITER;
        orderAsText += order.getTotalCost() + DELIMITER;
        return orderAsText;
    }

    private Order unMarshallOrder(String orderAsText) {
        String[] orderInfoPart = orderAsText.split(DELIMITER);

        int orderNumber = Integer.parseInt(orderInfoPart[0]);

        Order orderUnMarshalling = new Order(orderNumber);

        orderUnMarshalling.setCustomerName(orderInfoPart[1]);

        Taxes t = new Taxes();
        t.setStateAbbreviations(orderInfoPart[2]);
        orderUnMarshalling.setTaxes(t);
        t.setTaxRate(new BigDecimal(orderInfoPart[3]));

        Product p = new Product();
        p.setProductType(orderInfoPart[4]);
        orderUnMarshalling.setProduct(p);
        orderUnMarshalling.setArea(new BigDecimal(orderInfoPart[5]));
        p.setCostPerSquareFoot(new BigDecimal(orderInfoPart[6]));
        p.setLaborCostPerSquareFoot(new BigDecimal(orderInfoPart[7]));
        orderUnMarshalling.setMaterialCost(new BigDecimal(orderInfoPart[8]));
        orderUnMarshalling.setLaborCost(new BigDecimal(orderInfoPart[9]));
        orderUnMarshalling.setTotalTax(new BigDecimal(orderInfoPart[10]));
        orderUnMarshalling.setTotalCost(new BigDecimal(orderInfoPart[11]));

        return orderUnMarshalling;
    }
// Causing Error in Tests
    private void loadOrderFile() throws FlooringException {
        orders.clear();

        Scanner sc;

        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringException("No Orders Found :( ", ex);
        }
        String currentLine;
        Order currentOrder;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.startsWith("OrderNumber")) {
                currentLine = sc.nextLine();
            }

            currentOrder = unMarshallOrder(currentLine);
            orders.put(currentOrder.getOrderNumber(), currentOrder);

        }
        sc.close();
    }

    private void writeOrderFile() throws FlooringException {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new FileWriter(FILE));
        } catch (IOException ex) {
            throw new FlooringException("Could not Save Order Data", ex);
        }

        String orderAsText;

        pw.println("OrderNumber,CustomerName,State,TaxRate,ProductType,"
                + "Area,CostPerSquareFoot,LaborCostPerSquareFoot," + "MaterialCost,LaborCost,Tax,Total");

        for (Map.Entry<Integer, Order> currentOrder : orders.entrySet()) {
            orderAsText = marshallOrder(currentOrder.getValue());
            pw.println(orderAsText);
            pw.flush();

        }

        pw.close();
    }

    @Override
    public Order addOrder(Order newOrder) throws FlooringException {
        FILE = "Orders/Orders_" + newOrder.getTimeStamp() + ".txt";
        try {
            loadOrderFile();
        } catch (FlooringException ex) {
        }

        orders.put(newOrder.getOrderNumber(), newOrder);
        writeOrderFile();
        return newOrder;
    }

    @Override
    public List<Order> searchOrderByDate(String date) throws FlooringException {

        FILE = "Orders/Orders_" + date + ".txt";
        loadOrderFile();
        return new ArrayList<>(orders.values());
    }
    // return orders.values().stream().filter((o) -> o.getTimeStamp().equals(date))
    // .collect(Collectors.toList());

    @Override
    public void removeOrder(String date, int orderNumber) throws FlooringException {
        FILE = "Orders/Orders_" + date + ".txt";
        try {
            loadOrderFile();
        } catch (FlooringException ex) {
        }
        orders.remove(orderNumber);
        writeOrderFile();
    }

    @Override
    public void editOrder(String date, int orderNumber, Order editOrder) throws FlooringException {
        FILE = "Orders/Orders_" + date + ".txt";
        try {
            loadOrderFile();
        } catch (FlooringException ex) {
        }
        orders.replace(orderNumber, editOrder);
        writeOrderFile();
    }

    @Override
    public List<Order> getAllOrders() throws FlooringException {
        loadOrderFile();
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrderByOrderNum(String date, int orderNumber) throws FlooringException {
        FILE = "Orders/Orders_" + date + ".txt";
        loadOrderFile();
        Order editOrder = orders.get(orderNumber);
        return editOrder;

    }
}
