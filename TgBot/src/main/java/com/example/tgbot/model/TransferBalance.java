package com.example.tgbot.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransferBalance {


    private Long From;
    private Long To;
    private BigDecimal Amount;
}

