package com.example.service;

import com.example.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * AML Service class.
 * <p>
 * This class loads and processes the records and logs if any transactions breach the threshold
 */

public class AMLService {
    private static final Logger logger = LoggerFactory.getLogger(AMLService.class.getName());

    // Sliding window length - to check the recent transactions with in this window from the latest transaction
    private static final int WINDOW_LENGTH_IN_SECONDS = 60;

    // Total transaction
    private static final double TRANSACTION_TOTAL_AMOUNT_THRESHOLD = 50_000;

    // holds recent transactions for the account
    private final Map<String, Deque<Transaction>> accountTransactionsMap = new HashMap<>();
    // Data Service - provides the transactions data
    private final DataService dataService;

    /**
     * Constructor to create AML Service
     *
     * @param dataService - loads the transaction data
     */

    public AMLService(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * * This loads and processes the records and logs if any transactions breach the threshold
     *
     * @return total number of transactions breached the threshold
     */
    public int process() {
        int noOfBreachedTransactions = 0;
        Collection<Transaction> transactionList = dataService.loadData();
        for (Transaction transaction : transactionList) {
            Deque<Transaction> accountTransactions = accountTransactionsMap.computeIfAbsent(transaction.getAccount(), v -> new LinkedList<>());
            // remove the transactions older than specified window
            while (!accountTransactions.isEmpty() && ChronoUnit.SECONDS.between(accountTransactions.getFirst().getTransactionTime(), transaction.getTransactionTime()) > WINDOW_LENGTH_IN_SECONDS) {
                accountTransactions.removeFirst();
            }
            accountTransactions.add(transaction);
            double totalTransactionAmount = totalTransactionAmount(accountTransactions);
            if (totalTransactionAmount > TRANSACTION_TOTAL_AMOUNT_THRESHOLD) {
                noOfBreachedTransactions++;
                logger.warn(" ALERT::  All Transactions {} in the last  [{}] seconds : total transaction amount [{}] breached the threshold [{}]", accountTransactions, WINDOW_LENGTH_IN_SECONDS, totalTransactionAmount, TRANSACTION_TOTAL_AMOUNT_THRESHOLD);
            }
        }
        return noOfBreachedTransactions;
    }

    private double totalTransactionAmount(Collection<Transaction> transactions) {
        double totalTransactionAmount = 0d;
        for (Transaction accountTransaction : transactions) {
            totalTransactionAmount += accountTransaction.getAmount();
        }
        return totalTransactionAmount;
    }
}
