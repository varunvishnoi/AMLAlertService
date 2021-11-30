package com.aml.alerting;

import com.sun.istack.internal.NotNull;

import java.sql.Timestamp;

/*Responsible for alerting by writing on console*/
public class PrintOnConsoleAlerting implements Alerting {
    private String accountId;
    private Double amount;
    private Long secs;
    private Timestamp currentTransactionTimestamp;

    public PrintOnConsoleAlerting(@NotNull String accountId, @NotNull Double amount, @NotNull Long secs, @NotNull Timestamp currentTransactionTimestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.secs = secs;
        this.currentTransactionTimestamp = currentTransactionTimestamp;
    }

    /*Responsible for alerting*/
    public void alert() {
        if(accountId != null && amount != null && secs != null && currentTransactionTimestamp != null) {
            System.out.println("Transaction Amount Limit breached for Account: "+ accountId+" at "+ currentTransactionTimestamp +". Total Transaction Amount: £"+ amount+ " in last "+secs+" Seconds");
        } else {
            throw new NullPointerException("accountId or amount or secs or currentTransactionTimestamp can't be Null");
        }
    }
}
