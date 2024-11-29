package com.example.service;

import com.example.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface DataService {
    Collection<Transaction> loadData();
}
