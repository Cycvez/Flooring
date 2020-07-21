/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Order;
import com.mycompany.flooring.dto.Product;
import com.mycompany.flooring.dto.Taxes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class ExportDataDaoImpl implements ExportDataDao {

    File directory = new File("Orders");
    File[] filenames = directory.listFiles();

    List<File> orderFiles = new ArrayList<>();
    Map<Integer, Order> orders = new HashMap<>();
    private static final String DELIMITER = ",";

    public ExportDataDaoImpl() {

    }

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

    private void loadOrderFile(File f) throws FlooringException {

        Scanner sc;

        try {
            sc = new Scanner(new FileReader(f));
        } catch (FileNotFoundException ex) {
            throw new FlooringException("No Orders Found :( ", ex);
        }
        String currentLine;
        Order currentOrder;
        String dateOnFile = f.getName();
        String date = dateOnFile.substring(7, 15);
        String dateFormatted = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.startsWith("OrderNumber")) {
                currentLine = sc.nextLine();
            }
            currentOrder = unMarshallOrder(currentLine);

            currentOrder.setTimeStamp(dateFormatted);
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        sc.close();
    }

    private void writeSingleFileOrder() throws FlooringException {
        PrintWriter pw;
//        String currentFile = this.directory.getName();

        try {
            pw = new PrintWriter(new FileWriter("BackUp/DataExport.txt"));
        } catch (IOException ex) {
            throw new FlooringException("Could not Save Order Data", ex);
        }

        String orderAsText;

        for (Map.Entry<Integer, Order> currentOrder : orders.entrySet()) {

            String date = currentOrder.getValue().getTimeStamp();

            orderAsText = marshallOrder(currentOrder.getValue());
            pw.println(orderAsText + date);
            pw.flush();

        }

        pw.close();
    }

    @Override
    public void exportData() throws FlooringException {
        File[] fileArray = new File("Orders/").listFiles();

        for (File f : fileArray) {

            if (f.getName().length() == 19 && f.getName().endsWith(".txt")) {
                loadOrderFile(f);

                writeSingleFileOrder();

            }

        }
    }

}
