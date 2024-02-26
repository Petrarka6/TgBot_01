package com.example.tgbot.servise;

public class Palindrom {

    public int ToPalindrome (int InVal)
    {
        int  counter=0;


        while (true) {

            String InValStr= String.valueOf(InVal);

            if (Test(InValStr))
            { System.out.println("до палиндрома  "+counter);
                break;
            }
            //     else { System.out.println("B  "+counter);}//отладочное
          InVal++;
            counter++;
        }


        return counter;
    }
    public  boolean Test (String Str)
    {

        int InValStrLen =Str.length();// вообще, можно одной строкой но я не буду
        System.out.println( "Длинна введенного числа =  "+InValStrLen);

        char[] InValChar=Str.toCharArray();

        for ( char d: InValChar)  //давайте посмотрим на наш массив в консольке
        {
            System.out.println(d);
        }

        for (int g=0; g<(InValStrLen/2); g++) {

            if(!( InValChar[0+g] ==  InValChar[InValStrLen-g-1] )){
                return false;   //если какие то элементы равноудаленные от начала и конца не равны, ретурним фолс
            }
        }
        return true;
    }


    public void Paaaal ()
    {
        System.out.println("dffff");

    }

}
