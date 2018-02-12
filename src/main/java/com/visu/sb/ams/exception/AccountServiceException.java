package com.visu.sb.ams.exception;

import java.math.BigInteger;

public abstract class AccountServiceException extends Exception {

    protected BigInteger accountId;

    public AccountServiceException() {
    }
}
