package com.visu.sb.ams.exception;

import java.math.BigInteger;

public class AccountNotExist extends AccountServiceException{

    public AccountNotExist(BigInteger accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getMessage() {
        return "Account " + accountId + " does not exist";
    }
}
