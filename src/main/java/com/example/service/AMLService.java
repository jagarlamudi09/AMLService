package com.example.service;

import com.example.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class AMLService {
    private static final Logger logger = LoggerFactory.getLogger(AMLService.class.getName());

    private static final double TRANSACTION_TOTAL_AMOUNT_THRESHOLD = 50_000;
    private static final int WINDOW_LENGTH_IN_SECONDS = 60;
    // holds recent transactions for the account
    private final Map<String, Deque<Transaction>> accountTransactionsMap = new HashMap<>();
    private final DataService dataService;

    public AMLService(DataService dataProvider) {
        this.dataService = dataProvider;
    }

    public void process() {
        Collection<Transaction> transactionList = dataService.loadData();
        for (Transaction transaction : transactionList) {
            Deque<Transaction> accountTransactions = accountTransactionsMap.computeIfAbsent(transaction.getAccount(), v -> new LinkedList<>());
            // remove the transactions older than specified window
            while (!accountTransactions.isEmpty() && ChronoUnit.SECONDS.between(accountTransactions.getFirst().getTransactionTime(), transaction.getTransactionTime()) > WINDOW_LENGTH_IN_SECONDS) {
                accountTransactions.removeFirst();
            }
            accountTransactions.add(transaction);
            double totalTransactionAmount = totalTransactionAmount(accountTransactions);
            //logger.info("{}", accountTransactions);
            if (totalTransactionAmount > TRANSACTION_TOTAL_AMOUNT_THRESHOLD) {
                logger.warn(" ALERT::  All Transactions {} in the last  [{}] seconds : total transaction amount [{}] breached the threshold [{}]", accountTransactions, WINDOW_LENGTH_IN_SECONDS, totalTransactionAmount, TRANSACTION_TOTAL_AMOUNT_THRESHOLD);
            }
        }

    }

    private double totalTransactionAmount(Collection<Transaction> transactions) {
        double totalTransactionAmount = 0d;
        for (Transaction accountTransaction : transactions) {
            totalTransactionAmount += accountTransaction.getAmount();
        }
        return totalTransactionAmount;
    }
}
