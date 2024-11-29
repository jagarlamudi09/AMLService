package com.example.service;

import com.example.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface DataService {

    /**
     * Loads the transactions details
     *
     * @return Collection of transactions
     */
    Collection<Transaction> loadData();
}
