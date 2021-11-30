package com.aml.alerting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PrintOnConsoleAlertingTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAlert_Positive() {
        String accountId = "ACC ID 1";
        Double amount = 10000D;
        Long lookBackPeriodInSeconds = 60L;
        Timestamp timestamp = Timestamp.valueOf("2021-11-29 12:55:27.000000000");
        PrintOnConsoleAlerting printOnConsoleAlerting = new PrintOnConsoleAlerting(accountId, amount, lookBackPeriodInSeconds, timestamp);
        printOnConsoleAlerting.alert();
        assertEquals("Transaction Amount Limit breached for Account: ACC ID 1 at 2021-11-29 12:55:27.0. Total Transaction Amount: £10000.0 in last 60 Seconds\r\n", outContent.toString());
    }

    @Test
    public void testAlert_WhenAccountIdNull() {
        String accountId = null;
        Double amount = 10000D;
        Long lookBackPeriodInSeconds = 60L;
        Timestamp timestamp = Timestamp.valueOf("2021-11-29 12:55:27.000000000");
        PrintOnConsoleAlerting printOnConsoleAlerting = new PrintOnConsoleAlerting(accountId, amount, lookBackPeriodInSeconds, timestamp);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> printOnConsoleAlerting.alert());
        assertEquals("accountId or amount or secs or currentTransactionTimestamp can't be Null", actualException.getMessage());
    }

    @Test
    public void testAlert_WhenAmountNull() {
        String accountId = "ACC ID 1";
        Double amount = null;
        Long lookBackPeriodInSeconds = 60L;
        Timestamp timestamp = Timestamp.valueOf("2021-11-29 12:55:27.000000000");
        PrintOnConsoleAlerting printOnConsoleAlerting = new PrintOnConsoleAlerting(accountId, amount, lookBackPeriodInSeconds, timestamp);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> printOnConsoleAlerting.alert());
        assertEquals("accountId or amount or secs or currentTransactionTimestamp can't be Null", actualException.getMessage());
    }

    @Test
    public void testAlert_WhenLookBackPeriodNull() {
        String accountId = "ACC ID 1";
        Double amount = 10000D;
        Long lookBackPeriodInSeconds = null;
        Timestamp timestamp = Timestamp.valueOf("2021-11-29 12:55:27.000000000");
        PrintOnConsoleAlerting printOnConsoleAlerting = new PrintOnConsoleAlerting(accountId, amount, lookBackPeriodInSeconds, timestamp);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> printOnConsoleAlerting.alert());
        assertEquals("accountId or amount or secs or currentTransactionTimestamp can't be Null", actualException.getMessage());
    }

    @Test
    public void testAlert_WhenTransactionDateTimeNull() {
        String accountId = "ACC ID 1";
        Double amount = 10000D;
        Long lookBackPeriodInSeconds = 60L;
        Timestamp timestamp = null;
        PrintOnConsoleAlerting printOnConsoleAlerting = new PrintOnConsoleAlerting(accountId, amount, lookBackPeriodInSeconds, timestamp);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> printOnConsoleAlerting.alert());
        assertEquals("accountId or amount or secs or currentTransactionTimestamp can't be Null", actualException.getMessage());
    }
}
