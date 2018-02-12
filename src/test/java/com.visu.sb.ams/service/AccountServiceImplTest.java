package com.visu.sb.ams.service;

import com.visu.sb.ams.entity.Account;
import com.visu.sb.ams.exception.AccountNotExist;
import com.visu.sb.ams.util.OperationResult;
import com.visu.sb.ams.util.Status;
import com.visu.sb.ams.util.TestConstant;
import com.visu.sb.ams.util.TestQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    private static final BigDecimal AMOUNT_LESS_BALANCE = BigDecimal.TEN;
    private static final BigDecimal BALANCE = BigDecimal.valueOf(1000L);
    private static final BigDecimal AMOUNT_MORE_BALANCE = BigDecimal.valueOf(1100L);

    @Autowired
    private AccountService accountService;

    private Connection connection;
    private Statement statement;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(TestConstant.H2_CONNECTION_URL, TestConstant.H2_USER_NAME, TestConstant.H2_PASSWORD);
        statement = connection.createStatement();
        statement.executeUpdate(TestQuery.INSERT_ACCOUNT_DATA_USER1);
        statement.executeUpdate(TestQuery.INSERT_ACCOUNT_DATA_USER2);
    }

    @After
    public void tearDown() throws SQLException {
        statement.executeUpdate(TestQuery.DELETE_ALL_ROWS);
        statement.close();
        connection.close();
    }

    @Test
    public void testGetAccountById() throws AccountNotExist {
        Account account = accountService.getAccountById(BigInteger.ONE);

        Assert.assertNotNull(account);
        Assert.assertEquals(BigInteger.ONE, account.getId());
        Assert.assertTrue(BigDecimal.valueOf(1000L).compareTo(account.getBalance()) == 0);
    }

    @Test(expected = AccountNotExist.class)
    public void testGetInexistentAccountById() throws AccountNotExist {
        accountService.getAccountById(BigInteger.ZERO);
    }

    @Test
    public void testPut() throws Exception {
        Account initialAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(BALANCE.compareTo(initialAccount.getBalance()) == 0);

        BigDecimal amount = BigDecimal.valueOf(100L);
        OperationResult result = accountService.put(BigInteger.ONE, amount);
        Assert.assertEquals(Status.SUCCESS, result.getStatus());
        Assert.assertEquals("Operation processed successfully", result.getDescription());

        BigDecimal updatedBalance = initialAccount.getBalance().add(amount);

        Account updatedAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(updatedBalance.compareTo(updatedAccount.getBalance()) == 0);
    }

    @Test
    public void testPutForInexistentAccount() {
        OperationResult result = accountService.put(BigInteger.ZERO, BigDecimal.TEN);
        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ZERO + " does not exist", result.getDescription());
    }

    @Test
    public void testGetAmountLessThanBalance() throws Exception {
        Account initialAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(BALANCE.compareTo(initialAccount.getBalance()) == 0);

        OperationResult result = accountService.get(BigInteger.ONE, AMOUNT_LESS_BALANCE);

        Assert.assertEquals(Status.SUCCESS, result.getStatus());
        Assert.assertEquals("Operation processed successfully", result.getDescription());

        BigDecimal updatedBalance = initialAccount.getBalance().subtract(AMOUNT_LESS_BALANCE);
        Account updatedAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(updatedBalance.compareTo(updatedAccount.getBalance()) == 0);
    }

    @Test
    public void testGetAmountMoreThanBalance() throws Exception {
        Account initialAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(BALANCE.compareTo(initialAccount.getBalance()) == 0);

        OperationResult result = accountService.get(BigInteger.ONE, AMOUNT_MORE_BALANCE);

        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ONE + " has insufficient balance", result.getDescription());

        Account updatedAccount = accountService.getAccountById(BigInteger.ONE);
        Assert.assertTrue(BALANCE.compareTo(updatedAccount.getBalance()) == 0);
    }

    @Test
    public void testGetForInexistentAccount() {
        OperationResult result = accountService.get(BigInteger.ZERO, BigDecimal.TEN);
        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ZERO + " does not exist", result.getDescription());
    }

    @Test
    public void testTransferWithSufficientSenderBalance() throws Exception {
        BigInteger senderId = BigInteger.ONE;
        BigInteger receiverId = BigInteger.valueOf(2L);

        Account sender = accountService.getAccountById(senderId);
        Assert.assertTrue(BALANCE.compareTo(sender.getBalance()) == 0);

        Account receiver = accountService.getAccountById(receiverId);
        Assert.assertTrue(BALANCE.compareTo(receiver.getBalance()) == 0);

        OperationResult result = accountService.transfer(senderId, receiverId, AMOUNT_LESS_BALANCE);

        Assert.assertEquals(Status.SUCCESS, result.getStatus());
        Assert.assertEquals("Operation processed successfully", result.getDescription());

        BigDecimal updatedSenderBalance = sender.getBalance().subtract(AMOUNT_LESS_BALANCE);
        Account updatedSenderAccount = accountService.getAccountById(senderId);
        Assert.assertTrue(updatedSenderBalance.compareTo(updatedSenderAccount.getBalance()) == 0);

        BigDecimal updatedReceiverBalance = sender.getBalance().add(AMOUNT_LESS_BALANCE);
        Account updatedReceiverAccount = accountService.getAccountById(receiverId);
        Assert.assertTrue(updatedReceiverBalance.compareTo(updatedReceiverAccount.getBalance()) == 0);
    }

    @Test
    public void testTransferWithInsufficientSenderBalance() throws Exception {
        BigInteger senderId = BigInteger.ONE;
        BigInteger receiverId = BigInteger.valueOf(2L);

        Account sender = accountService.getAccountById(senderId);
        Assert.assertTrue(BALANCE.compareTo(sender.getBalance()) == 0);

        Account receiver = accountService.getAccountById(receiverId);
        Assert.assertTrue(BALANCE.compareTo(receiver.getBalance()) == 0);

        OperationResult result = accountService.transfer(senderId, receiverId, AMOUNT_MORE_BALANCE);

        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ONE + " has insufficient balance", result.getDescription());

        Account updatedSenderAccount = accountService.getAccountById(senderId);
        Assert.assertTrue(BALANCE.compareTo(updatedSenderAccount.getBalance()) == 0);

        Account updatedReceiverAccount = accountService.getAccountById(receiverId);
        Assert.assertTrue(BALANCE.compareTo(updatedReceiverAccount.getBalance()) == 0);
    }

    @Test
    public void testTransferFromInexistentAccount() {
        OperationResult result = accountService.transfer(BigInteger.ZERO, BigInteger.ONE, BigDecimal.TEN);
        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ZERO + " does not exist", result.getDescription());
    }

    @Test
    public void testTransferToInexistentAccount() {
        OperationResult result = accountService.transfer(BigInteger.ONE, BigInteger.ZERO, BigDecimal.TEN);
        Assert.assertEquals(Status.FAILED, result.getStatus());
        Assert.assertEquals("Account " + BigInteger.ZERO + " does not exist", result.getDescription());
    }
}