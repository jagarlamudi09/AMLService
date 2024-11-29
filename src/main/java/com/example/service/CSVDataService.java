package com.example.service;

import com.example.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVDataService implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(CSVDataService.class.getName());
    private final String fileName;

    public CSVDataService(String fileName) {
        this.fileName = fileName;
        File f = new File(fileName);
        if (!f.exists() || f.isDirectory()) {
            throw new IllegalArgumentException("File doesn't exist :" + fileName);
        }

    }

    @Override
    public List<Transaction> loadData() {
        String[] HEADERS = {"Time", "Amount", "Account"};
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records;
        try {
            Reader in = new FileReader(fileName);
            records = csvFormat.parse(in);
        } catch (IOException e) {
            logger.error("Couldn't load the file", e);
            return Collections.emptyList();
        }

        List<Transaction> transactions = new ArrayList<>();
        for (CSVRecord record : records) {
            try {
                String time = record.get("Time");
                String amount = record.get("Amount");
                String account = record.get("Account");
                Transaction transaction = Transaction.create(time, amount, account);
                transactions.add(transaction);
            } catch (Exception e) {
                logger.error("Couldn't create the record", e);
            }
        }
        return transactions;
    }
}
