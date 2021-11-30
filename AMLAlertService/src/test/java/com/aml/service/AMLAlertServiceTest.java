package com.aml.service;

import com.aml.cache.TransactionCache;
import com.aml.helper.TestHelper;
import com.aml.pojo.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.any;

public class AMLAlertServiceTest {

    public List<Transaction> transactionsLess50KList;
    public List<Transaction> transactionsEqual50KList;
    public List<Transaction> transactionsGreater50KList;
    public TransactionCache transactionCache2TranOnAcc1;
    public TransactionCache transactionCache5TranOnAcc1;
    public TransactionCache transactionCache5TranOnAcc1Where2TranscationsOnEdge;
    public Transaction currentTransactionAcc1;
    public Transaction currentTransactionAcc4;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setup() {
        transactionsLess50KList = TestHelper.getTransactionsListAmountLess50K();
        transactionsEqual50KList = TestHelper.getTransactionsListAmountEqual50K();
        transactionsGreater50KList =  TestHelper.getTransactionsListAmountGreater50K();
        transactionCache2TranOnAcc1 = TestHelper.transactionCache2TranOnAcc1();
        transactionCache5TranOnAcc1 = TestHelper.transactionCache5TranOnAcc1();
        transactionCache5TranOnAcc1Where2TranscationsOnEdge = TestHelper.transactionCache5TranOnAcc1Where2TranscationsOnEdge();
        currentTransactionAcc1 = TestHelper.getTransactionAcc1();
        currentTransactionAcc4 = TestHelper.getTransactionAcc4();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void testProcess_WhenTransactionCacheNull() {
        AMLAlertService amlAlertService = new AMLAlertService(null, any(String.class), 60L);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> amlAlertService.process());
        assertEquals("transactionCache or inputTransactionFilePath or lookBackPeriodInSeconds can't be Null", actualException.getMessage());
    }

    @Test
    public void testProcess_WhenFilePathNull() {
        AMLAlertService amlAlertService = new AMLAlertService(any(TransactionCache.class), null, 60L);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> amlAlertService.process());
        assertEquals("transactionCache or inputTransactionFilePath or lookBackPeriodInSeconds can't be Null", actualException.getMessage());
    }

    @Test
    public void testProcess_WhenLookBackPeriodNull() {
        AMLAlertService amlAlertService = new AMLAlertService(any(TransactionCache.class), any(String.class), null);
        NullPointerException actualException = assertThrows(NullPointerException.class, () -> amlAlertService.process());
        assertEquals("transactionCache or inputTransactionFilePath or lookBackPeriodInSeconds can't be Null", actualException.getMessage());
    }

    @Test
    public void testAlertAMLDownstreamSystem_AmountLessThan50K() {
        AMLAlertService amlAlertService = new AMLAlertService(any(TransactionCache.class), any(String.class), 60L);
        amlAlertService.alertAMLDownstreamSystem(transactionsLess50KList, currentTransactionAcc1, 60L);
        assertEquals("", outContent.toString());
    }

    @Test
    public void testAlertAMLDownstreamSystem_AmountEquals50K() {
        AMLAlertService amlAlertService = new AMLAlertService(any(TransactionCache.class), any(String.class), 60L);
        amlAlertService.alertAMLDownstreamSystem(transactionsEqual50KList, currentTransactionAcc1, 60L);
        assertEquals("Transaction Amount Limit breached for Account: ACC_1 at 2021-11-29 12:55:27.0. Total Transaction Amount: £50000.0 in last 60 Seconds\r\n", outContent.toString());
    }

    @Test
    public void testAlertAMLDownstreamSystem_AmountGreater50K() {
        AMLAlertService amlAlertService = new AMLAlertService(any(TransactionCache.class), any(String.class), 60L);
        amlAlertService.alertAMLDownstreamSystem(transactionsGreater50KList, currentTransactionAcc1, 60L);
        assertEquals("Transaction Amount Limit breached for Account: ACC_1 at 2021-11-29 12:55:27.0. Total Transaction Amount: £60000.0 in last 60 Seconds\r\n", outContent.toString());
    }

    @Test
    public void testFindTransactionsCapturedInGivenLookBackPeriod_OnlyCurrentTransactionInLast60Secs() {
        AMLAlertService amlAlertService = new AMLAlertService(transactionCache2TranOnAcc1, any(String.class), 60L);
        List<Transaction> transactionsInLast60Secs = amlAlertService.findTransactionsCapturedInGivenLookBackPeriod(currentTransactionAcc4, 60L);
        assertEquals(1, transactionsInLast60Secs.size());
    }

    @Test
    public void testFindTransactionsCapturedInGivenLookBackPeriod_2TransactionsFromCacheAnd1CurrentTransaction() {
        AMLAlertService amlAlertService = new AMLAlertService(transactionCache2TranOnAcc1, any(String.class), 60L);
        List<Transaction> transactionsInLast60Secs = amlAlertService.findTransactionsCapturedInGivenLookBackPeriod(currentTransactionAcc1, 60L);
        assertEquals(3, transactionsInLast60Secs.size());
    }

    @Test
    public void testFindTransactionsCapturedInGivenLookBackPeriod_2TransactionsOutOf5FromCacheAnd1CurrentTransaction() {
        AMLAlertService amlAlertService = new AMLAlertService(transactionCache5TranOnAcc1, any(String.class), 60L);
        List<Transaction> transactionsInLast60Secs = amlAlertService.findTransactionsCapturedInGivenLookBackPeriod(currentTransactionAcc1, 60L);
        assertEquals(3, transactionsInLast60Secs.size());
    }

    @Test //Edge Case
    public void testFindTransactionsCapturedInGivenLookBackPeriod_2TransactionsOn60SecsWindowEdgeFromCacheAnd1CurrentTransaction() {
        AMLAlertService amlAlertService = new AMLAlertService(transactionCache5TranOnAcc1Where2TranscationsOnEdge, any(String.class), 60L);
        List<Transaction> transactionsInLast60Secs = amlAlertService.findTransactionsCapturedInGivenLookBackPeriod(currentTransactionAcc1, 60L);
        assertEquals(3, transactionsInLast60Secs.size());
    }
}
