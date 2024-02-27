package com.example.tgbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TgMessage {///логи попробуем писать

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //не хочу разбираться как в базе длинное хранить, потом
    private Long Millis;
    private String Message;
    private String UserName;

}
