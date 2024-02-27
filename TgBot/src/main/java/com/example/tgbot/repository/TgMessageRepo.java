package com.example.tgbot.repository;

import com.example.tgbot.model.PilotQ2;
import com.example.tgbot.model.TgMessage;
import org.springframework.data.repository.CrudRepository;

public interface TgMessageRepo extends CrudRepository<TgMessage,Long> {

}
