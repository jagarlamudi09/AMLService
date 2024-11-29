package com.example.model;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class TransactionTest {
    @Test
    public void testCreate() {
        Transaction t = Transaction.create("10:10:20", "100", "1");
        assertEquals(LocalTime.of(10, 10, 20), t.getTransactionTime());
        assertEquals(100d, t.getAmount(), 0.001d);
        assertEquals("1", t.getAccount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTime() {
        Transaction.create("10:10:60", "100.0", "1");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmptyTime() {
        Transaction.create("", "100.0", "1");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAmount() {
        Transaction.create("10:10:50", "", "1");
    }

    @Test
    public void testEquals() {
        Transaction t = Transaction.create("10:10:20", "100", "1");
        Transaction t1 = Transaction.create("10:10:20", "100", "1");
        assertEquals(t1, t1);
    }
}