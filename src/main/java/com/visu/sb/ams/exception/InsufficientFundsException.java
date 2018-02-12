package com.visu.sb.ams.exception;

import java.math.BigInteger;

public class InsufficientFundsException extends AccountServiceException{

    public InsufficientFundsException(BigInteger accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getMessage() {
        return "Account " + accountId + " has insufficient balance";
    }
}
