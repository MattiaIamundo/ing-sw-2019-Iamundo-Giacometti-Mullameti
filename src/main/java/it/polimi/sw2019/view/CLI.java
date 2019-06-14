package it.polimi.sw2019.view;

import java.util.List;
import java.util.Scanner;

public class CLI implements UIinterface{

    private Scanner s;

    public CLI() {
        s = new Scanner(System.in);
    }

    @Override
    public void requestNickname( boolean isTheFirstTime, List<String> nicknameInTheGame ) {

        if( isTheFirstTime ) {
            //the player doesn't insert a nickname yet
            System.out.println("Welcome to ADRENALINE!\n");
            System.out.println("This is the list of the players's nickname:\n");

            if ( nicknameInTheGame.isEmpty() ) {

                System.out.println("There are no nickname registrated!\n");
            }
            else {

                for (String st : nicknameInTheGame) {
                    System.out.println(" -  " + st + "\n");
                }
            }

            System.out.println("Now choose your nickname!\n");

        }
        else {
        //that nickname is already inserted
            System.out.println("This nickname is already in the list!\n");
            System.out.println("This is the list:\n");

            for (String st : nicknameInTheGame) {
                System.out.println(" -  " + st + " \n");
            }

            System.out.println("Choose again your nickname please:\n");
        }

    }

    public void reconnection() {

        System.out.println("Welcome to ADRENALINE!\n");
        System.out.println("If you want to reconnect to the game, write 'Reconnection'!\n");
        System.out.println("Else write" + " 'quit'" + "\n");
    }

    public void sendOk() {
        //System.out.println("ok\n");
    }
}
