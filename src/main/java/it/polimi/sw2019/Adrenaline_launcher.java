package it.polimi.sw2019;

import it.polimi.sw2019.network.RMI.ClientRMI;
import it.polimi.sw2019.network.Server;
import it.polimi.sw2019.network.Socket.ClientSocket;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Adrenaline_launcher
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        boolean outFromLoop = false;
        //for the ClientSocket
        String host = "127.0.0.1";
        int i = 0;

        //start the server?
        do {
            System.out.println("Do you want to start the server?\n");
            System.out.println("    write 1 to set the server,\n");
            System.out.println("    write 0 to not set the server:\n");

            try {
                i = scanner.nextInt();
                outFromLoop = true;
                new Server();

            } catch (InputMismatchException ex) {
                System.out.println("Please insert 0 or 1!\n");
                scanner.nextLine();
            }

        }while (!outFromLoop);

        //reset the variable
        outFromLoop = false;

        do {
            System.out.println("If you want to use RMI press 0,\n");
            System.out.println("    else write 1 to use Socket:\n");

            try {
                i = scanner.nextInt();
                outFromLoop = true;

            } catch (InputMismatchException ex) {
                System.out.println("Please insert 0 or 1!\n");
                scanner.nextLine();
            }

        }while (!outFromLoop);

        //start a client
        //for GUI or CLI, it is insert inside the constructor
        if( i == 0 ){

            new ClientRMI();
        }
        else {

            new ClientSocket(host, false);
        }
        //devo implementare il modo per dire con la gui
    }

}
