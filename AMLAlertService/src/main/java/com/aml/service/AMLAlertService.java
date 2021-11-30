package com.aml.service;

import com.aml.alerting.Alerting;
import com.aml.alerting.PrintOnConsoleAlerting;
import com.aml.cache.TransactionCache;
import com.aml.pojo.Transaction;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class AMLAlertService {

    private TransactionCache transactionCache;
    private String inputTransactionFilePath;
    private Long lookBackPeriodInSeconds;

    /*Constructor Exposed to make sure whenever AMLAlertService is created it is created with all 3 mandatory with Not Null properties*/
    public AMLAlertService(@NotNull TransactionCache transactionCache, @NotNull String inputTransactionFilePath, @NotNull Long lookBackPeriodInSeconds) {
        this.transactionCache = transactionCache;
        this.inputTransactionFilePath = inputTransactionFilePath;
        this.lookBackPeriodInSeconds = lookBackPeriodInSeconds;
    }

    /*Responsible for doing AML Alert Service Processing
     * Step 1: Read Transactions from CSV.
     * Step 2: Skip 1st Header line.
     * Step 3: Loop for each line read from CSV except 1st header line and do following steps:
     * Step 3.1:     Split line based on comma and collect items in Array. We are assuming here no other comma is present inside CSV except delimiter.
     * Step 3.2:     Convert Array to Transaction POJO.
     * Step 3.3:     Fetch all Transactions into a List against Account Id received on Current Transaction in last 60 seconds including current Transaction.
     *               Also, add current transaction into Cache.
     * Step 3.4:     If Total Amount on Transaction is Above or equal to 50K then alert otherwise no action required.
     * */
    public void process() throws Exception {
        if(transactionCache == null || inputTransactionFilePath == null || lookBackPeriodInSeconds ==null) {
            throw new NullPointerException("transactionCache or inputTransactionFilePath or lookBackPeriodInSeconds can't be Null");
        }
        //Step 1:
        try (BufferedReader br = new BufferedReader(new FileReader(inputTransactionFilePath))) {
            //Step 2:
            br.readLine();
            String line = null;
            //Step 3:
            while ((line = br.readLine()) != null) {
                //Step 3.1
                String[] values = line.split(",");
                //Step 3.2
                Transaction transaction = new Transaction(Timestamp.valueOf(values[0]), values[1], Double.valueOf(values[2]));
                //Step 3.3
                List<Transaction> transactionsInGivenLookBackPeriod = findTransactionsCapturedInGivenLookBackPeriod(transaction, lookBackPeriodInSeconds);
                //Step 3.4
                alertAMLDownstreamSystem(transactionsInGivenLookBackPeriod, transaction, lookBackPeriodInSeconds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*If Total Amount on Transaction is Above or equal to 50K then alert otherwise no action required.
    * Here Stream API is used to collect and do sum of amounts on Transactions in list
    * If Total amount is >=50K then alert otherwise no action*/
    public void alertAMLDownstreamSystem(List<Transaction> transactionsInLast60Secs, Transaction currentTransaction, Long lookBackPeriod) {
        if(transactionsInLast60Secs == null || currentTransaction == null || lookBackPeriod ==null) {
            throw new NullPointerException("transactionsInLast60Secs or currentTransaction or lookBackPeriod can't be Null");
        }
        Double totalAmount = transactionsInLast60Secs.stream().mapToDouble(transaction -> transaction.getAmount()).sum();
        if (totalAmount >= 50000) {
            Alerting alerting = new PrintOnConsoleAlerting(transactionsInLast60Secs.get(0).getAccountId(), totalAmount, lookBackPeriod, currentTransaction.getTransactionDateTime());
            alerting.alert();
        }
    }

    /*Fetch all Transactions into a List against Account Id received on Current Transaction in last 60 seconds including current Transaction.
    * Also, add current transaction into Cache.
    * Here Stream API is used and filtered based on transaction time
    * Comparison is made between on each item's TransactionDateTime in list vs Current Transaction's TransactionDateTime - 60 seconds
    * If Item's TransactionDateTime is > Current Transaction's TransactionDateTime - 60 seconds Then Item is added onto he filtered list
    * At last, filtered items are collected as list*/
    public List<Transaction> findTransactionsCapturedInGivenLookBackPeriod(Transaction transaction, Long secs) {
        if(transaction == null || secs == null) {
            throw new NullPointerException("transactionsInLast60Secs or currentTransaction or lookBackPeriod can't be Null");
        }
        transactionCache.add(transaction);
        return transactionCache.getAllTransactionsBasedOnAccountId(transaction.getAccountId())
                .stream()
                .filter(tran -> tran.getTransactionDateTime().compareTo(new Timestamp(transaction.getTransactionDateTime().getTime() - (secs * 1000L))) > 0)
                .collect(Collectors.toList());
    }
}
