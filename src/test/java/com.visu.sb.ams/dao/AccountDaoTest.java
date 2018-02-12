package com.visu.sb.ams.dao;

import com.visu.sb.ams.entity.Account;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    private Connection connection;
    private Statement statement;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(TestConstant.H2_CONNECTION_URL, TestConstant.H2_USER_NAME, TestConstant.H2_PASSWORD);
        statement = connection.createStatement();
        statement.executeUpdate(TestQuery.INSERT_ACCOUNT_DATA_USER1);
    }

    @After
    public void tearDown() throws SQLException {
        statement.executeUpdate(TestQuery.DELETE_ALL_ROWS);
        statement.close();
        connection.close();
    }

    @Test
    public void testGetById() {
        Account account = accountDao.getById(BigInteger.ONE);

        Assert.assertNotNull(account);
        Assert.assertEquals(BigInteger.ONE, account.getId());
        Assert.assertTrue(BigDecimal.valueOf(1000L).compareTo(account.getBalance()) == 0);
    }

    @Test
    @Transactional
    public void testUpdate() {
        Account initialAccount = accountDao.getById(BigInteger.ONE);
        Assert.assertTrue(BigDecimal.valueOf(1000L).compareTo(initialAccount.getBalance()) == 0);

        BigDecimal amount = BigDecimal.valueOf(500L);
        initialAccount.setBalance(amount);
        accountDao.update(initialAccount);

        Account updatedAccount = accountDao.getById(BigInteger.ONE);
        Assert.assertTrue(amount.compareTo(updatedAccount.getBalance()) == 0);
    }
}
