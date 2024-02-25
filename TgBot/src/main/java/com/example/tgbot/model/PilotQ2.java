package com.example.tgbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PilotQ2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Firstname, LastName;
    private int age;
}
