package com.example.tgbot.repository;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository


public class BankRepos {
    private static final Map<Long, BigDecimal> storage = new HashMap();

    public  BigDecimal getBalanceForId(Long accountId) {

//        storage.put(0L, new BigDecimal(99990));
//        storage.put(1L, new BigDecimal(1587));
//        storage.put(2L, new BigDecimal(99992));
//        storage.put(3L, new BigDecimal(99993));
        return (BigDecimal)storage.get(accountId);

    }

    public BigDecimal save(Long id, BigDecimal amount) {
        storage.put(id, amount);
        return amount;
    }
}
