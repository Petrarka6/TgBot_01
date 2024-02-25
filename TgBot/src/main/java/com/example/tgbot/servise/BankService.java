package com.example.tgbot.servise;


import java.math.BigDecimal;


import com.example.tgbot.model.TransferBalance;
import com.example.tgbot.repository.BankRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankService {
    private BankRepos bankRepos;

    public  BigDecimal Getbalance(Long accountId) throws IllegalAccessException {

        BigDecimal balance =bankRepos.getBalanceForId(accountId);
//
//    return (balance);
        if (balance.equals(new BigDecimal(1587))) {
            throw new IllegalArgumentException("ашипка 1587");
        } else {
            return balance;
        }
    }

    public  BigDecimal addMoney(Long to, BigDecimal amount) {
        BigDecimal CurrentBalanse = bankRepos.getBalanceForId(to);
        if (CurrentBalanse==null)
        {
            bankRepos.save(to,amount);
            return amount;
        }
        else
        {
            BigDecimal updateBalance = CurrentBalanse.add(amount);
            bankRepos.save(to,updateBalance);
            return updateBalance;
        }


    }

    public  void MakeTransfer(TransferBalance transferBalance) {
        BigDecimal fromBalance = bankRepos.getBalanceForId(transferBalance.getFrom());
        BigDecimal toBalance = bankRepos.getBalanceForId(transferBalance.getTo());
        if (fromBalance == null || toBalance == null) { throw new IllegalArgumentException("No Id");}


        if (transferBalance.getAmount().compareTo(fromBalance) > 0){ throw new IllegalArgumentException("No Money no Honey");}


        fromBalance.subtract(transferBalance.getAmount());
        toBalance.add(transferBalance.getAmount());
        bankRepos.save(transferBalance.getFrom(),fromBalance);
        bankRepos.save(transferBalance.getTo(),toBalance);



    }


}
