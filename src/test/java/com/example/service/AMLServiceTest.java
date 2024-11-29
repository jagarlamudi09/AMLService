package com.example.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AMLServiceTest {
    private AMLService amlService;

    @Test
    public void testProcess() {
        DataService dataService = new CSVDataService("src/test/resources/file.csv");
        amlService = new AMLService(dataService);
        assertEquals(1, amlService.process());
    }

    @Test
    public void testProcess_no_alerts() {
        DataService dataService = new CSVDataService("src/test/resources/file_with_no_alerts.csv");
        amlService = new AMLService(dataService);
        assertEquals(0, amlService.process());
    }

}