package com.example.tgbot.servise;

import java.io.*;
import java.util.*;

import com.example.tgbot.config.BotConfig;
import com.example.tgbot.model.Buyer;
import com.example.tgbot.model.PilotQ2;

import com.example.tgbot.repository.BuyerRepo;
import com.example.tgbot.repository.ListNames2;
import com.example.tgbot.repository.PilotQ2Repo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//import java.io.FileInputStream;
///import java.io.FileNotFoundException;

@Component

public class TelegramBot extends TelegramLongPollingBot {
final BotConfig config;
    @Autowired
    private PilotQ2Repo pilotQ2Repo;

    @Autowired
    private BuyerRepo buyerRepo;

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


        long chatId =update.getMessage().getChatId();

        Buyer addBuyer = new Buyer();

        addBuyer.setId(chatId);
        addBuyer.setChatIdd(chatId);
        addBuyer.setFirstname(update.getMessage().getChat().getFirstName());
       boolean р =buyerRepo.existsById(chatId);
       if(! buyerRepo.existsById(chatId))
       {
           addBuyer.setBalance(10000L);
           buyerRepo.save(addBuyer);
       }

        if(update.hasMessage()&&(update.getMessage().hasText())) {

               String messegeText =update.getMessage().getText();

               Date date =new Date();
               Long LongMillis= date.getTime();


              if ( messegeText.equals("/start")){
                  startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                  sendMessage(chatId, "Смотри че могу  " +
                          "\n /leha -рассказать про Лёху" +
                          "\n /wisdom - поделиться мудростью  " +
                          "\n Lena 1234 -  задачку на палиндром решить"  +
                          "\n AddPilot Кирилл Иванов 42 - добавить пилота в базу"+
                          "\n /GetPilots - посмотреть пилотов из базы"+
                          "\n DeletePilot {id} - удалить пилота из базы"+
                          "\n /DeleteAllPilots  - удалить всех пилотов из базы"+
                          "\n /BuyCat  -  Купить кота за 100"+
                          "\n /BuyTaz  -  Купить таз  за 300"+
                          "\n /GetMyBalance  - Посмотреть свой баланс"



                  );
              } else if (( messegeText.equals("/BuyCat"))) { //Да, я в курсе что код 2 раза повторяется, из за 2 раз изобретать что то  смысла нет ящитаю

                  Long OldBalance=  buyerRepo.findById(chatId).get().getBalance();
                  if (OldBalance>=100){

                      sendMessage(chatId, "Вот твой кэт");
                      Random rn = new Random();
                      int CatNo =rn.nextInt (5-1+1)+1;
                      String Catname="KatsPic/kat"+CatNo+".jpg";
                      try {   sendMessagePicture(chatId, Catname);}
                      catch (FileNotFoundException e) {  throw new RuntimeException(e);}
                      addBuyer.setBalance(OldBalance-100);
                      buyerRepo.save(addBuyer);

                  } else {

                      sendMessage(chatId, "Денег у тебя мало, никакого тебе кота");
                  }

              } else if (( messegeText.equals("/BuyTaz"))) {

                    Long OldBalance=  buyerRepo.findById(chatId).get().getBalance();
                    if (OldBalance>=300){
                        sendMessage(chatId, "Вот твой таз");
                        Random rn = new Random();
                        int CatNo =rn.nextInt (5-1+1)+1;
                        String Catname="TazPic/taz"+CatNo+".jpg";
                        try {   sendMessagePicture(chatId, Catname);}
                        catch (FileNotFoundException e) {  throw new RuntimeException(e);}

                        addBuyer.setBalance(OldBalance-300);
                        buyerRepo.save(addBuyer);
                }
                else {
                        sendMessage(chatId, "Денег у тебя мало, никакого тебе таза");
                }



              } else if (( messegeText.equals("/GetMyBalance"))) {


                  sendMessage(chatId, buyerRepo.findById(chatId).get().getBalance().toString());
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
                //  sendMessage(chatId, "Пилоты пока не поддерживаются но скоро будут");
                  Iterable<PilotQ2> pilot = pilotQ2Repo.findAll();
                  //   sendMessage(chatId, pilotQ2Repo.toString());
                   sendMessage(chatId, pilot.toString());

              }else if (messegeText.contains("Math")) {



              sendMessage(chatId, "равно ");
              }else if (messegeText.contains("Lena")) {

                  String[] LenaArrStr = messegeText.split(" ");
                  if(LenaArrStr.length!=2 ){sendMessage(chatId, "Чет не правильно вводиш"); return;}

                  try{
                      int ag = Integer.parseInt(LenaArrStr[1]);
                      Palindrom palindrom = new Palindrom();
                      int Top= palindrom.ToPalindrome(ag);
                      sendMessage(chatId, "До Палидрома     " + String.valueOf(Top));
                  }
                  catch (NumberFormatException rf) {
                      sendMessage(chatId, "Цыфру введи мормально"); return;
                  }

              }else if (messegeText.contains("AddPilot")) {

                  int error =0;
                  String[] pilotArrStr = messegeText.split(" ");
                  if(pilotArrStr.length!=4 ){sendMessage(chatId, "Чет не правильно вводиш"); return;}
                 PilotQ2 addpilot = new PilotQ2();
                  ListNames2 Ln2 = new ListNames2();
               //   String[] ListNam= Ln2.ListNamessStr.split(", ");
                  List<String> ListNam = new ArrayList<String>(Arrays.asList(Ln2.ListNamessStr.split(", ")));
                  if (ListNam.contains(pilotArrStr[1])){
                      addpilot.setFirstname(pilotArrStr[1]);
                  }else{
                      sendMessage(chatId, "Нет такого имени, не добавлю"); return;
                  }


                 addpilot.setLastName(pilotArrStr[2]);
                  int ag =0;
                  try{
                      ag = Integer.parseInt(pilotArrStr[3]);

                      if (ag<18){sendMessage(chatId, "Молодой ишо, не довавлю"+"\uD83D\uDE1D"); return; }
                      if (ag>80){sendMessage(chatId, "Староват уже, не довавлю"+"\uD83D\uDE1D"); return; }
                      addpilot.setAge(ag);
                   }
                  catch (NumberFormatException rf) {
                      sendMessage(chatId, "Формат возраста кривой, не добавлю"); return;

                  }


                 pilotQ2Repo.save(addpilot);

              sendMessage(chatId, "Добавил  " + "\uD83D\uDE0E");

//              try {
//                  sendHelpButton(chatId);
//                  } catch (FileNotFoundException e)
//                  { throw new RuntimeException(e);}


              }


              else if (messegeText.contains("DeletePilot")) {
                  String[] parts = messegeText.split(" ");  //да, код повторяется, знаю, пока убирать не буду
                  if(parts.length!=2 ){sendMessage(chatId, "Чет не правильно вводиш"); return;}
                 int ag =0;
                  try{
                    //  ag = Integer.parseInt(parts[1]);
                      Long aq = Long.parseLong(parts[1]);
                      if( pilotQ2Repo.existsById(aq)){

                          pilotQ2Repo.deleteById(aq);
                          sendMessage(chatId, "Удалил  " + "\uD83D\uDE0E");
                      }else {
                          sendMessage(chatId, "Нет у нас таких  " + "\uD83D\uDE0E");
                      }
                  }
                  catch (NumberFormatException rf) {
                      sendMessage(chatId, "Айдишник введи мормально"); return;
                  }
              }else if (messegeText.contains("DeleteAllPilots")) {
                  pilotQ2Repo.deleteAll();
                sendMessage(chatId, "Всех удалил ");


              } else if (messegeText.contains("SetBalance ")) {
                  String[] parts = messegeText.split(" ");
                  if(parts.length!=3 ){sendMessage(chatId, "Чет не правильно вводиш"); return;}
                  long SetBa;
                  long SetBaId;

                  try{
                      SetBaId = Long.parseLong(parts[1]);//как то можно сразу инт но пофиг, гдето выше есть
                  }
                  catch (NumberFormatException rf) {
                      sendMessage(chatId, "Айдишник введи мормально"); return;
                  }

                  try{
                      SetBa = Long.parseLong(parts[2]);//как то можно сразу инт но пофиг, гдето выше есть
                  }
                  catch (NumberFormatException rf) {
                      sendMessage(chatId, "Сумму введи мормально"); return;
                  }

                   if( buyerRepo.existsById(SetBaId)){
                      addBuyer.setFirstname(buyerRepo.findById(SetBaId).get().getFirstname());
                       addBuyer.setId(SetBaId);
                       addBuyer.setBalance(SetBa);//заполнили и отправили
                       buyerRepo.save(addBuyer);
                       sendMessage(chatId, "Поменял"); return;
                   }else{
                       sendMessage(chatId, "Не знаю таких"); return;
                   }





              }else if ((messegeText.contains("GetBaseBalance"))&&(chatId==5021216246L)) {

                  Iterable<Buyer> burrr = buyerRepo.findAll();
                  //   sendMessage(chatId, pilotQ2Repo.toString());
                  for(Buyer sss : burrr){
                      sendMessage(chatId,  sss.toString());
                  }
                 /// sendMessage(chatId,  burrr.toString());
              }

              else{

                  sendMessage(chatId, "Пока не умею такого");
                  try {   sendMessagePicture(chatId, "Kot_debil.jpg");}
                  catch (FileNotFoundException e) {  throw new RuntimeException(e);}
              }





        }
        try {
            sendHelpButton(chatId);
        } catch (FileNotFoundException e)
        { throw new RuntimeException(e);}







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

    private void sendHelpButton (long chatId ) throws FileNotFoundException {

        SendMessage sendMessage2 = new SendMessage();
        sendMessage2.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new    ReplyKeyboardMarkup();

        sendMessage2.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("/start");
        keyboardFirstRow.add("/GetPilots");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("/DeleteAllPilots");
        keyboardSecondRow.add("/GetMyBalance");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardThirdRow.add("/BuyCat");
        keyboardThirdRow.add("/BuyTaz");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardFourRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardFourRow.add("/leha");
        keyboardFourRow.add("/wisdom");


        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourRow);

        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage2.setChatId(String.valueOf(chatId));
      //  sendMessage2.setReplyToMessageId(message.getMessageId());
        sendMessage2.setText("Че дальше?");
        try {
            execute(sendMessage2);
        } catch (TelegramApiException e) {
            int f=8;
        }

    }


}


