package com.visu.sb.ams.dao;

import com.visu.sb.ams.entity.Account;

import java.math.BigInteger;

public interface AccountDao {

    Account getById(BigInteger id);

    void update(Account account);

}
