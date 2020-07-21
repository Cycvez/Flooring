/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class OrderNumberDaoImpl implements OrderNumberDao {

    public static String FILE="Data/OrderNumber.txt";

    private int orderNumber;
    
    private void loadOrderNumber() throws FlooringException {
        Scanner sc;

        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringException("Could Not Load File :( ", ex);
        }

        String currentLine;
        int currentNumber;

        while (sc.hasNextLine()) {

            currentLine = sc.nextLine();

            currentNumber = Integer.parseInt(currentLine);
            orderNumber = currentNumber;
        }
        sc.close();
    }

    private void writeOrderNumber() throws FlooringException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(FILE));
        } catch (IOException ex) {
            throw new FlooringException("Error writing to file");
        }

        pw.println(orderNumber);

        pw.flush();
        pw.close();
    }

    @Override
    public int getOrderNumber() throws FlooringException {
        loadOrderNumber();
        int currentOrderNumber= this.orderNumber;
        setNextOrderNumber();
        return currentOrderNumber;
    }

    @Override
    public void setNextOrderNumber() throws FlooringException {
        loadOrderNumber();
        orderNumber++;
        writeOrderNumber();
    }

}
