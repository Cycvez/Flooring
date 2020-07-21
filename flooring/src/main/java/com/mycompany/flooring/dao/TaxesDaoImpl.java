/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooring.dao;

import com.mycompany.flooring.dto.Taxes;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TaxesDaoImpl implements TaxesDao {

    public static final String FILE = "Data/Taxes.txt";
    public static final String DELIMITER = ",";

    private Map<String, Taxes> stateTaxes = new HashMap<>();

    private String marshallStateTaxes(Taxes state) {
        String stateTaxesAsText = state.getStateAbbreviations() + DELIMITER;
        stateTaxesAsText += state.getStateFullName() + DELIMITER;
        stateTaxesAsText += state.getTaxRate();
        return stateTaxesAsText;
    }

    private Taxes unMarshallStateTaxes(String stateTaxestAsText) {
        String[] stateTaxesInfoPart = stateTaxestAsText.split(DELIMITER);
        String stateAbbreviations = stateTaxesInfoPart[0];

        Taxes stateUnMarshalling = new Taxes(stateAbbreviations);

        stateUnMarshalling.setStateFullName(stateTaxesInfoPart[1]);
        stateUnMarshalling.setTaxRate(new BigDecimal(stateTaxesInfoPart[2]));

        return stateUnMarshalling;
    }

    private void loadTaxFile() throws FlooringException {
        Scanner sc;

        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringException("Could Not Load State :( ", ex);
        }
        String currentLine;
        Taxes currentState;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            if (currentLine.startsWith("State,")) {
                currentLine = sc.nextLine();
            }
            currentState = unMarshallStateTaxes(currentLine);
            stateTaxes.put(currentState.getStateAbbreviations(), currentState);
        }
        sc.close();
    }

    @Override
    public Taxes loadTaxRates(String state) throws FlooringException {
        loadTaxFile();
        return stateTaxes.get(state);

    }

}
