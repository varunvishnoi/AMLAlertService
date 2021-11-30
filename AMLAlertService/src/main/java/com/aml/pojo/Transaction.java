package com.aml.pojo;

import com.sun.istack.internal.NotNull;

import java.sql.Timestamp;
import java.util.Objects;

/*  com.aml.pojo.Transaction POJO - This com.aml.pojo.Transaction POJO have 3 properties:
*       com.aml.pojo.Transaction Date and Time
*       Account to which com.aml.pojo.Transaction belongs
*       And Amount of com.aml.pojo.Transaction   */

public class Transaction {
    Timestamp transactionDateTime;
    String accountId;
    Double amount;

    /*Constructor Exposed to make sure whenever com.aml.pojo.Transaction is created it is created with all 3 mandatory with Not Null properties*/
    public Transaction(@NotNull Timestamp transactionDateTime, @NotNull String accountId, @NotNull Double amount) {
        this.transactionDateTime = transactionDateTime;
        this.accountId = accountId;
        this.amount = amount;
    }

    public Timestamp getTransactionDateTime() {
        return transactionDateTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionDateTime.equals(that.transactionDateTime) && accountId.equals(that.accountId) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDateTime, accountId, amount);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "transactionDateTime=" + transactionDateTime +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
