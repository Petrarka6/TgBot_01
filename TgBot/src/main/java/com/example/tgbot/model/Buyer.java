package com.example.tgbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Buyer {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String Firstname;
    long chatIdd;
    Long Balance;

}
