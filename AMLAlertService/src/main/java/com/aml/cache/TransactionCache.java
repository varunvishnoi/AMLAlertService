package com.aml.cache;

import com.aml.pojo.Transaction;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*Responsible for Holding Cache for AMLAlertService:*/
public final class TransactionCache {

    /*Transactions are added into Dictionary format where
    * Key is Account Id and
    * Value is List of Transaction which belongs to single Account Id*/
    private HashMap<String, ArrayList<Transaction>> cache = new HashMap<>();


    /*Responsible for adding Transactions in Cache:
    * If Cache already contain account id associated with Transaction then add Transaction onto list associated with account id.
    * If Cache doesn't contain account id associated with Transaction then create new entry where key is account id received
    *   over transaction and value is new list with transaction on it*/
    public void add(@NotNull Transaction transaction) {
        if (transaction ==null) {
            throw new NullPointerException("transaction can't be Null");
        }
        String key = transaction.getAccountId();
        if(cache.containsKey(key)) {
            cache.get(key).add(transaction);
        } else {
            ArrayList<Transaction> list = new ArrayList<>();
            list.add(transaction);
            cache.put(key, list);
        }
    }

    /*Responsible for getting List of Transactions based on Account Id
    * In Case Transactions on given key not found then return empty list to support stream functions*/
    public List<Transaction> getAllTransactionsBasedOnAccountId(@NotNull String key) {
        if (key ==null) {
            throw new NullPointerException("key can't be Null");
        }
        if(cache.containsKey(key)) {
            return cache.get(key);
        } else {
            return Collections.emptyList();
        }
    }

    public HashMap<String, ArrayList<Transaction>> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, ArrayList<Transaction>> cache) {
        this.cache = cache;
    }
}
