package com.aml;

import com.aml.cache.TransactionCache;
import com.aml.service.AMLAlertService;


/*Service Runner*/
public class ServiceRunner {

    //CSV Path from where Transaction to be load
    private static final String INPUT_TRANSACTION_FILE_PATH = "C:\\Users\\varun\\Desktop\\Transactions.csv";

    //Breach Threshold window i.e. 60 secs as given in Problem Statement
    private static final Long LOOK_BACK_PERIOD_IN_SECONDS = 60L;

    public static void main(String[] args) {
        // Initialization of Transaction Cache
        TransactionCache transactionCache = new TransactionCache();

        // Start Processing Transactions
        AMLAlertService amlAlertService = new AMLAlertService(transactionCache, INPUT_TRANSACTION_FILE_PATH, LOOK_BACK_PERIOD_IN_SECONDS);
        try {
            amlAlertService.process();
        } catch (Exception e) {
            System.out.println("Exception Found --> "+ e.getMessage());
        }
    }
}
