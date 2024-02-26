package com.example.tgbot.controller;


import java.math.BigDecimal;

import com.example.tgbot.model.PilotQ2;
import com.example.tgbot.repository.PilotQ2Repo;
import com.example.tgbot.servise.BankService;

import com.example.tgbot.model.TransferBalance;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/balance")
@AllArgsConstructor
public class BalanceController {
    private static final Logger log = LoggerFactory.getLogger(BalanceController.class);
    private BankService bankService;
   private PilotQ2Repo pilotQ2Repo;

    @GetMapping ("/Pilot/")
    public String PilotGet (PilotQ2 pilotQ2)
    {
        Iterable<PilotQ2> pilot = pilotQ2Repo.findAll();
        return pilot.toString();
    }
    @PostMapping ({"/PilotNew"})
    public String NewPilot (@RequestBody PilotQ2 pilotQ2w)
    {
        pilotQ2Repo.save(pilotQ2w);
        return "fsd";

    }


    @GetMapping({"/GetBalance/{accountId}"})
    public BigDecimal GetBalance(@PathVariable Long accountId) throws IllegalAccessException {
    //    return (new BigDecimal(345));
        return bankService.Getbalance(accountId);
    }
    //
//    @GetMapping({"/aaaa/{accountIdsa}"})
//    public BigDecimal GetBalancsss(@PathVariable Long accountIdsa) {
//        return new BigDecimal(1433);
//    }
//
    @PostMapping({"/Add"})
    public BigDecimal addMoney(@RequestBody TransferBalance transferBalance) {
        return bankService.addMoney(transferBalance.getTo(), transferBalance.getAmount());
    }
    //
    @PostMapping({"/Transfer"})
    public void TransBalance(@RequestBody TransferBalance transferBalance) {
        bankService.MakeTransfer(transferBalance);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public String handle1(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "Не хочу работать((";
    }


}

