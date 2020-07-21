/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author carlo
 */
public class Taxes {

    private String stateAbbreviations;
    private String stateFullName;
    private BigDecimal taxRate;

    public Taxes(){
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.stateAbbreviations);
        hash = 29 * hash + Objects.hashCode(this.stateFullName);
        hash = 29 * hash + Objects.hashCode(this.taxRate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Taxes other = (Taxes) obj;
        if (!Objects.equals(this.stateAbbreviations, other.stateAbbreviations)) {
            return false;
        }
        if (!Objects.equals(this.stateFullName, other.stateFullName)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }
    
    public Taxes(String stateAbbreviations) {
        this.stateAbbreviations = stateAbbreviations;
    }

    public String getStateAbbreviations() {
        return stateAbbreviations;
    }

    public void setStateAbbreviations(String stateAbbreviations) {
        this.stateAbbreviations = stateAbbreviations;
    }

    public String getStateFullName() {
        return stateFullName;
    }

    public void setStateFullName(String stateFullName) {
        this.stateFullName = stateFullName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

}
