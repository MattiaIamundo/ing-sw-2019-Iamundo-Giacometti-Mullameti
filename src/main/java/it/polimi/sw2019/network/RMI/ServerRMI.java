package it.polimi.sw2019.network.RMI;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteRef;

public class ServerRMI {


    public static void main( String[] args ) {


        System.out.println ("Loading RMI service");
        try {
            LocateRegistry.createRegistry(1099);
            // Load the service
            RMIinterfaceImpl bulbService = new RMIinterfaceImpl();
            // Examine the service, to see where it is stored

            RemoteRef location = bulbService.getRef();
            System.out.println (location.remoteToString());
            // Check to see if a registry was specified
            String registry = "localhost";

            // Registration format //registry_hostname :port /service
            //Note the :port field is optional
            String registration = "rmi://" + registry + "/RMIinterface";
            // Register with service so that clients can find us
            Naming.rebind( registration, bulbService );


        } catch (RemoteException re) {
            System.err.println ("Remote Error - " + re);
        } catch (Exception e) {
            System.err.println ("Error - " + e);
        }
    }
}