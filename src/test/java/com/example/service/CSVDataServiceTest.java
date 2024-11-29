package com.example.service;

import com.example.model.Transaction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CSVDataServiceTest {
    private CSVDataService dataProvider;

    @Test
    public void testLoadDataFromCSV() {
        dataProvider = new CSVDataService("src/test/resources/file.csv");
        List<Transaction> transactions = dataProvider.loadData();
        assertEquals(4, transactions.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFilename() {
        dataProvider = new CSVDataService("src/test/resources/file1.csv");
        List<Transaction> transactions = dataProvider.loadData();
        assertTrue(transactions.isEmpty());
    }


    @Test
    public void testFileWithValidData() {
        dataProvider = new CSVDataService("src/test/resources/invalid_data.csv");
        List<Transaction> transactions = dataProvider.loadData();
        assertTrue(transactions.isEmpty());
    }

}