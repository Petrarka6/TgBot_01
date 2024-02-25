package com.example.tgbot.servise;

import java.io.*;
import java.util.*;

import com.example.tgbot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//import java.io.FileInputStream;
///import java.io.FileNotFoundException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
final BotConfig config;


public  TelegramBot(BotConfig config)
{
this.config =config;

}
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&(update.getMessage().hasText())) {

               String messegeText =update.getMessage().getText();
              long chatId =update.getMessage().getChatId();

              if ( messegeText.equals("/start")){
                  startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                  sendMessage(chatId, "Смотри че могу  " +
                          "\n /leha -рассказать про Лёху" +
                          "\n /wisdom - поделиться мудростью  " +
                          "\n   Math 2+4  - калькулятор с 2 числами " +
                          "\n Lena 1234 -  задачку на палиндром решить"  +
                          "\n AddPilot Иванов Кирил 42 - добавить пилота в базу"+
                          "\n /GetPilots - посмотреть пилотов из базы"
                  );




              } else if (( messegeText.equals("/leha"))) {
                  sendMessage(chatId, "Пёс он шелудивый");
              } else if (( messegeText.equals("/wisdom"))) {

                  Random rn = new Random();
                   int citata =rn.nextInt (5-1+1)+1;
                   switch (citata){
                       case 1:   sendMessage(chatId, "Таз без баса как борщ без мяса");  break;

                       case 2: try {   sendMessagePicture(chatId, "Stet1.jpg");}
                       catch (FileNotFoundException e) {  throw new RuntimeException(e);}  break;

                       case 3: try {   sendMessagePicture(chatId, "Volk.jpg");}
                       catch (FileNotFoundException e) {  throw new RuntimeException(e);}  break;

                       case 4: try {   sendMessagePicture(chatId, "Stet2.jpg");}
                       catch (FileNotFoundException e) {  throw new RuntimeException(e);}  break;

                       case 5: try {   sendMessagePicture(chatId, "Lenin.jpg");}
                       catch (FileNotFoundException e) {  throw new RuntimeException(e);}  break;

                   }



              }else if (( messegeText.equals("/GetPilots"))) {
                  sendMessage(chatId, "Пилоты пока не поддерживаются но скоро будут");



              }else if (messegeText.contains("Math")) {
              sendMessage(chatId, "равно ");
              }else if (messegeText.contains("Lena")) {
                 sendMessage(chatId, "До палиндрома ..23 ");
              }else if (messegeText.contains("AddPilot")) {
              sendMessage(chatId, "Добавил  " + "\uD83D\uDE0E");
}


              else{
                  sendMessage(chatId, "Пока не умею такого");
                  try {   sendMessagePicture(chatId, "Kot_debil.jpg");}
                  catch (FileNotFoundException e) {  throw new RuntimeException(e);}
              }



//                switch (messegeText){
//                case "/start":
//
//                     startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
//                    sendMessage(chatId, "Смотри че могу  " +
//                            "\n /leha -рассказать про Лёху" +
//                            "\n /wisdom - поделиться мудростью  " +
//                            "\n   Math 2+4  - калькулятор с 2 числами " +
//                            "\n Lena 1234 -  задачку на палиндром решить"  );
//
//
//
//                    break;
//                    case  "/leha":
//                        sendMessage(chatId, "Пёс он шелудивый");
//                        break;
//                    case "/wisdom":
//                        sendMessage(chatId, "Таз без баса как борщ без мяса");
//                        break;
//                default:
//
//                        sendMessage(chatId, "Пока не умею");
//
//
//             }
        }

    }

    private void startCommandRecived(long chatId, String name){
            String answer = "Здаров "+name+", чем помочь?";
            sendMessage(chatId,answer);
    }
    private void sendMessage (long chatId ,String textToSend)
    {
        SendMessage message =new SendMessage();
        message.setChatId(String.valueOf(chatId) );
        message.setText( textToSend);
        try{
            execute(message);
        }
       catch (TelegramApiException e) {
         // throw new RuntimeException(e);
        }


    }
    private void sendMessagePicture (long chatId ,String PicToSend) throws FileNotFoundException {

File xx=new File(PicToSend);


     SendPhoto messagef = new SendPhoto();
          messagef.setChatId(String.valueOf(chatId));
            messagef.setPhoto(new InputFile(xx));

//
//
//        InputFile rr= new InputFile("C:/ww/Kot_debil.png");
        try {

          //  messagef.setChatId(String.valueOf(chatId));
           // messagef.setPhoto(rr);
            execute(messagef);
        } catch (TelegramApiException e) {
         //  e.printStackTrace();

            int f=8;
        }

    }
}


