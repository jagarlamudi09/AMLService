package com.example.service;

import org.junit.Before;
import org.junit.Test;

public class AMLServiceTest {
    private AMLService amlService;

    @Before
    public void setup() {
        DataService dataService = new CSVDataService("src/test/resources/AccountTransactions.csv");
        amlService = new AMLService(dataService);
    }

    @Test
    public void testProcess() {
        amlService.process();
    }

}