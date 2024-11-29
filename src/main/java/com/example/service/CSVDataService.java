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

/**
 * Implementation of DataService - to load the transaction details from CSV file
 * with the format specified below:
 * Time,Amount,Account
 */
public class CSVDataService implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(CSVDataService.class.getName());
    private final String fileName;

    /**
     * @param fileName - Name of the CSV File with transaction info
     *                 <p>
     *                 If the specified file is not found, throws IllegalArgumentException
     */
    public CSVDataService(String fileName) {
        this.fileName = fileName;
        File f = new File(fileName);
        if (!f.exists() || f.isDirectory()) {
            throw new IllegalArgumentException("File doesn't exist :" + fileName);
        }
    }

    /**
     * @return returns the collection of transactions loaded from the CSV file.
     * If any Transactions info is invalid - ignores and logs the info
     * <p>
     * If any exceptions while parsing the csv file, returns empty collection
     */

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
