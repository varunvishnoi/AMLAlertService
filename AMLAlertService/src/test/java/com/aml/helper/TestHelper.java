package com.aml.helper;

import com.aml.cache.TransactionCache;
import com.aml.pojo.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestHelper {

    public static TransactionCache transactionCache2TranOnAcc1() {
        TransactionCache transactionCache = new TransactionCache();
        HashMap<String, ArrayList<Transaction>> cache = new HashMap<>();
        ArrayList<Transaction> transaction1 = new ArrayList<>();
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:20.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:25.000000000"), "ACC_1", 10000D));
        cache.put(transaction1.get(0).getAccountId(), transaction1);
        ArrayList<Transaction> transaction2 = new ArrayList<>();
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        cache.put(transaction2.get(0).getAccountId(), transaction2);
        ArrayList<Transaction> transaction3 = new ArrayList<>();
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        cache.put(transaction3.get(0).getAccountId(), transaction3);
        transactionCache.setCache(cache);
        return transactionCache;
    }

    public static TransactionCache transactionCache5TranOnAcc1() {
        TransactionCache transactionCache = new TransactionCache();
        HashMap<String, ArrayList<Transaction>> cache = new HashMap<>();
        ArrayList<Transaction> transaction1 = new ArrayList<>();
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:01.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:05.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:10.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:15.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:20.000000000"), "ACC_1", 10000D));
        cache.put(transaction1.get(0).getAccountId(), transaction1);
        ArrayList<Transaction> transaction2 = new ArrayList<>();
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        cache.put(transaction2.get(0).getAccountId(), transaction2);
        ArrayList<Transaction> transaction3 = new ArrayList<>();
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        cache.put(transaction3.get(0).getAccountId(), transaction3);
        transactionCache.setCache(cache);
        return transactionCache;
    }

    public static TransactionCache transactionCache5TranOnAcc1Where2TranscationsOnEdge() {
        TransactionCache transactionCache = new TransactionCache();
        HashMap<String, ArrayList<Transaction>> cache = new HashMap<>();
        ArrayList<Transaction> transaction1 = new ArrayList<>();
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:01.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:05.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:53:10.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:54:28.000000000"), "ACC_1", 10000D));
        transaction1.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        cache.put(transaction1.get(0).getAccountId(), transaction1);
        ArrayList<Transaction> transaction2 = new ArrayList<>();
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        transaction2.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_2", 10000D));
        cache.put(transaction2.get(0).getAccountId(), transaction2);
        ArrayList<Transaction> transaction3 = new ArrayList<>();
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        transaction3.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_3", 10000D));
        cache.put(transaction3.get(0).getAccountId(), transaction3);
        transactionCache.setCache(cache);
        return transactionCache;
    }

    public static Transaction getTransactionAcc1() {
        return new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D);
    }

    public static Transaction getTransactionAcc4() {
        return new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_4", 10000D);
    }

    public static List<Transaction> getTransactionsListAmountLess50K() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        return list;
    }

    public static List<Transaction> getTransactionsListAmountEqual50K() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        return list;
    }

    public static List<Transaction> getTransactionsListAmountGreater50K() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        list.add(new Transaction(Timestamp.valueOf("2021-11-29 12:55:27.000000000"), "ACC_1", 10000D));
        return list;
    }
}
