package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static it.polimi.sw2019.view.ContSelect.addTimer;


public class PlayerView extends ObservableByGame implements Observer <Player> {

    private static String name = "null";
    private static int integer = 0;
    private static Scanner input = new Scanner ( System.in );
    private static Scanner output;

    protected void showPlayer() {

    }

    /**
     * this method show the update that one player did
     */
    public void update(Player message) {
        showPlayer();
    }

    protected static void nickname (Socket socket) {

        System.out.println("What is your nickname?\n");

        if (input.hasNextLine()) {
            name = input.nextLine();
        }
        nicknameReturn( name, socket);
    }

    protected static void nicknameReturn (String name, Socket socket) {

        ContSelect.nicknameReturnn(name, socket);
    }

    protected static void timer () {

        System.out.println("Set a timer to wait for 5 players when the third player will be set (in milliseconds):\n");

            if ( input.hasNextLine() ) {
                try {
                    integer = Integer.parseInt(input.nextLine());

                }catch ( NumberFormatException e) {
                    System.out.println("You have to insert a number!\n");
                    timer();
                }

            }
            if ( integer < 5000 ) {

                System.out.println("It has to be greeter than 5000 ms!\n");
                timer();
            }

        addTimer(integer);
    }
}
