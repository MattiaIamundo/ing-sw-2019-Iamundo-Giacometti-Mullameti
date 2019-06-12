package it.polimi.sw2019.network.RMI;

import java.rmi.*;
import java.util.Scanner;

public class ClientRMI {

    public static void main( String[] args ) {

        boolean ok = false;
        int number = 1;
        System.out.println ("Looking for light bulb service");
        try {
            // Check to see if a registry was specified
            String registry = "localhost";
        // Registration format
        // registry_hostname (optional):port /service
        String registration = "rmi://" + registry + "/RMIinterface";
        // Lookup the service in the registry, and obtain a remote service
        Remote remoteService = Naming.lookup ( registration );
        // Cast to a RMIinterface interface
        RMIinterface bulbService = (RMIinterface) remoteService;
        // Turn it on
        System.out.println ("Invoking bulbservice.on()");
        bulbService.on();
        // See if bulb has changed
        System.out.println ("Bulb state : " + bulbService.isOn()  );

        Scanner scanner = new Scanner(System.in);
        String name = "null";

        bulbService.getLocker().lock();

        while ( !ok ) {

            System.out.println("Insert a nickname for your player: \n");
            name = scanner.nextLine();

            bulbService.setN(name);
            if ( bulbService.isThisNamePresent( bulbService.getN() ) ) {

                System.out.println("The nickname is already present...\n");
            }
            else {
                number = bulbService.numberOfPlayer();
                ok = true;
            }
        }
        name = bulbService.getN();

        PlayerRMI pt = new PlayerRMI();
        System.out.println(pt.getTurnToGame() + "\n");
        PlayerRMI p = bulbService.createPlayer( pt , number, name );

        bulbService.addPlayer( p );

        bulbService.getLocker().unlock();

        // Conserve power
        System.out.println ("Invoking bulbservice.off()");
        bulbService.off();
        // See if bulb has changed
        System.out.println ("Bulb state : " + bulbService.isOn() );
        } catch (NotBoundException nbe) {
            System.out.println ("No light bulb service available in registry!");
        } catch (RemoteException re)           {
            System.out.println ("RMI Error - " + re);
        } catch (Exception e)           {
            System.out.println ("Error - " + e);
        }
    }
}