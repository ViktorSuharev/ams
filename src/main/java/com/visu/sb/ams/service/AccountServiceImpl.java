package com.visu.sb.ams.service;

import com.visu.sb.ams.dao.AccountDao;
import com.visu.sb.ams.entity.Account;
import com.visu.sb.ams.exception.AccountNotExist;
import com.visu.sb.ams.exception.InsufficientFundsException;
import com.visu.sb.ams.util.OperationResult;
import com.visu.sb.ams.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public OperationResult put(BigInteger accountId, BigDecimal amount) {
        LOGGER.debug("put begin");
        OperationResult operationResult = new OperationResult(Status.SUCCESS);
        try {
            Account account = getAccountById(accountId);
            BigDecimal balance = account.getBalance();
            account.setBalance(balance.add(amount));
            accountDao.update(account);
        } catch (Exception e) {
            operationResult = new OperationResult(Status.FAILED, e.getMessage());
        }
        LOGGER.debug("put end");
        return operationResult;
    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public OperationResult get(BigInteger accountId, BigDecimal amount) {
        LOGGER.debug("get begin");
        OperationResult operationResult = new OperationResult(Status.SUCCESS);
        try {
            Account account = getAccountById(accountId);
            validateWithdraw(account, amount);
            BigDecimal balance = account.getBalance();
            account.setBalance(balance.subtract(amount));
            accountDao.update(account);
        } catch (Exception e) {
            operationResult = new OperationResult(Status.FAILED, e.getMessage());
        }
        LOGGER.debug("get end");
        return operationResult;
    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public OperationResult transfer(BigInteger senderId, BigInteger receiverId, BigDecimal amount) {
        LOGGER.debug("transfer begin");
        OperationResult operationResult = new OperationResult(Status.SUCCESS);
        try {
            get(senderId, amount);
            put(receiverId, amount);
        } catch (Exception e) {
            operationResult = new OperationResult(Status.FAILED, e.getMessage());
        }
        LOGGER.debug("transfer end");
        return operationResult;
    }

    @Override
    public Account getAccountById(BigInteger id) throws AccountNotExist {
        Account account = accountDao.getById(id);
        if (account == null) {
            LOGGER.error("account {} does not exist", id);
            throw new AccountNotExist(id);
        }

        return account;
    }

    private void validateWithdraw(Account account, BigDecimal amount) throws InsufficientFundsException {
        if (account.getBalance().compareTo(amount) < 0) {
            LOGGER.error("account {} has insufficient balance", account.getId());
            throw new InsufficientFundsException(account.getId());
        }
    }
}
