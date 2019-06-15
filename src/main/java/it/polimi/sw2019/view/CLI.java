package it.polimi.sw2019.view;

import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ViewContEvent;

import java.util.List;
import java.util.Scanner;

public class CLI implements UIinterface{

    private PlayerView pv = null;
    private ViewContEvent vce = null;
    private Scanner s = null;
    private String string = "nope";

    public CLI(PlayerView playerView, ViewContEvent viewContEvent) {
        s = new Scanner(System.in);
        pv = playerView;
        vce = viewContEvent;
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
            string = s.nextLine();
            VCLogin vcLogin = new VCLogin(string);
            pv.sendNickname(vce, vcLogin);
        }
        else {
        //that nickname is already inserted
            System.out.println("This nickname is already in the list!\n");
            System.out.println("This is the list:\n");

            for (String st : nicknameInTheGame) {
                System.out.println(" -  " + st + " \n");
            }

            System.out.println("Choose again your nickname please:\n");
            string = s.nextLine();
            VCLogin vcLogin = new VCLogin(string);
            pv.sendNickname(vce, vcLogin);
        }

    }

    @Override
    public void requestColor(boolean firstTime, boolean duplicated, List<String> colorlist) {

        String idiom = "Now choose your color please:";

        if ( firstTime ) {
            System.out.println("This is the list of player's color:\n");

            for (String col : colorlist) {
                System.out.println(" -  " + col + " \n");
            }

            System.out.println(idiom + "\n");
            string = s.nextLine();
        }
        else {
            if ( duplicated ) {
                System.out.println("This color is already chosen:\n");
                System.out.println("This is the list of color you can choose:\n");

                for (String col : colorlist) {
                    System.out.println(" -  " + col + " \n");
                }
                System.out.println(idiom + "\n");
                string = s.nextLine();
            }
            else {
                System.out.println("This color is not present in the list:\n");
                System.out.println("This is the list of color you can choose:\n");

                for (String col : colorlist) {
                    System.out.println(" -  " + col + " \n");
                }
                System.out.println(idiom + "\n");
                string = s.nextLine();
            }
        }


        VCColor vcColor = new VCColor(string);
        pv.sendColor(vce,vcColor);
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
