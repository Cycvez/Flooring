/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

/**
 *
 * @author carlo
 */
public interface OrderNumberDao {
    int getOrderNumber() throws FlooringException;
    
    void setNextOrderNumber() throws FlooringException;
}
