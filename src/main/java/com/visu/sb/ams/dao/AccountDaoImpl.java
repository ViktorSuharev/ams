package com.visu.sb.ams.dao;

import com.visu.sb.ams.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Repository
public class AccountDaoImpl implements AccountDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account getById(BigInteger id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public void update(Account account) {
        entityManager.persist(account);
        LOGGER.debug("account {} was updated with {}", account.getId(), account.getBalance());
    }
}
