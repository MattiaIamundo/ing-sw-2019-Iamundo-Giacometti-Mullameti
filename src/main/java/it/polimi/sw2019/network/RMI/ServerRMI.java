package it.polimi.sw2019.network.RMI;

import java.rmi.registry.LocateRegistry;

public class ServerRMI {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("Server ready\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
