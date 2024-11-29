package com.example.model;

import com.example.util.Util;

import java.time.LocalTime;


public class Transaction {
    private LocalTime transactionTime;
    private double amount;
    private String account;

    public static Transaction create(String time, String amount, String account) {
        double amt = Double.parseDouble(amount);
        if (amt < 1) {
            throw new IllegalArgumentException("Invalid amount, amount should be > 0");
        }
        if (!Util.validateTime(time)) {
            throw new IllegalArgumentException("Invalid time, should be in format (hh:mm:ss)");
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        LocalTime parse = LocalTime.parse(time);
        transaction.setTransactionTime(parse);
        transaction.setAmount(amt);
        return transaction;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
