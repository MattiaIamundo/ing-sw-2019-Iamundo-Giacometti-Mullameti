package it.polimi.sw2019.network.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RMIinterfaceImpl extends UnicastRemoteObject implements RMIinterface {

    // Boolean flag to maintain light bulb state information
    private boolean lightOn;
    private boolean gameover = false;
    private String n = "null";
    private Lock locker = new ReentrantLock();
    private ArrayList<PlayerRMI> players = new ArrayList<>(5);

    // A constructor must be provided for the remote object
    public RMIinterfaceImpl() throws RemoteException{
        // Default value of off
          setBulb(false);
    }
    // Remotely accessible "on" method - turns on the light
    public void on() {
        // Turn bulb on
        setBulb (true);
    }
    // Remotely accessible "off" method - turns off the light
    public void off() {
        // Turn bulb off
        setBulb (false);
    }
    // Remotely accessible "isOn" method, returns state of bulb
    public boolean isOn() {
        return getBulb();
    }
    // Locally accessible "setBulb" method, changes state of      // bulb
    public void setBulb(boolean value)      {
        lightOn = value;
    }
    // Locally accessible "getBulb" method, returns state of      // bulb
    public boolean getBulb()      {
        return lightOn;
    }

    public String getN() {
        return n;
    }

    public void setN(String name) {
        this.n = name;
    }

    public boolean getGameOver() {
        return gameover;
    }

    public void setGameover() {
        this.gameover = true;
    }

    public synchronized void addPlayer( PlayerRMI my ) {

        if ( players.size() <= 4 ) {
            players.add(my);
        }
        else if ( players.size() == 5 ) {

            System.out.println("Ci sono già 5 giocatori che giocano\n");
        }
    }

    public synchronized boolean isThisNamePresent(String name) {

        if ( !players.isEmpty()) {

            for (PlayerRMI p : players) {

                if (p.getNickname().equals(name)) {

                    return true;
                }
            }
        }
        return false;
    }

    public synchronized Lock getLocker() {
        return this.locker;
    }

    public synchronized PlayerRMI createPlayer(PlayerRMI p, int i, String name) {

        p.setNickname(name);
        p.setTurnToGame(i);
        return p;
    }

    public synchronized int numberOfPlayer () {

        int i = 1;

        if ( !players.isEmpty() ) {

            for (PlayerRMI p : players) {
                i++;
            }
        }
        return i;
    }

}
