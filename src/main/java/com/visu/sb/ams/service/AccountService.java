package com.visu.sb.ams.service;

import com.visu.sb.ams.entity.Account;
import com.visu.sb.ams.exception.AccountNotExist;
import com.visu.sb.ams.util.OperationResult;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AccountService {

    Account getAccountById(BigInteger id) throws AccountNotExist;

    OperationResult put(BigInteger accountId, BigDecimal amount);

    OperationResult get(BigInteger accountId, BigDecimal amount);

    OperationResult transfer(BigInteger senderId, BigInteger receiverId, BigDecimal amount);
}
