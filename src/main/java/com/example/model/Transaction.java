package com.example.model;

import com.example.util.Util;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Model class to hold the Transaction details
 */

public class Transaction {
    // Time of the transaction
    private final LocalTime transactionTime;
    //Transaction Amount
    private final double amount;
    // Account
    private final String account;

    /**
     * Factory method to create the instances of Transaction
     *
     * @param time    - Transaction time as String in "hh:mm:ss" format
     * @param amount  - amount specified as String
     * @param account - Account name
     * @return instance of Transaction class
     * @throws IllegalArgumentException if  amount not valid double or is < 0 or time is not in correct format ( "hh:mm:ss").
     */
    public static Transaction create(String time, String amount, String account) {
        double amt = Double.parseDouble(amount);
        if (amt < 1) {
            throw new IllegalArgumentException("Invalid amount, amount should be > 0");
        }
        if (!Util.validateTime(time)) {
            throw new IllegalArgumentException("Invalid time, should be in format (hh:mm:ss)");
        }
        return new Transaction(LocalTime.parse(time), amt, account);
    }

    private Transaction(LocalTime transactionTime, double amount, String account) {
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.account = account;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }


    public double getAmount() {
        return amount;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(transactionTime, that.transactionTime) && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionTime, amount, account);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionTime=" + transactionTime +
                ", amount=" + amount +
                ", account='" + account + '\'' +
                '}';
    }
}
