package it.polimi.sw2019.network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;

public interface RMIinterface extends Remote {

    void on () throws RemoteException;

    void off() throws RemoteException;

    boolean isOn() throws RemoteException;

    void addPlayer(PlayerRMI e) throws RemoteException;

    boolean isThisNamePresent(String name) throws RemoteException;

    PlayerRMI createPlayer(PlayerRMI p, int i, String name) throws RemoteException;

    Lock getLocker() throws RemoteException;

    int numberOfPlayer() throws RemoteException;

    void setN(String name) throws RemoteException;

    String getN() throws RemoteException;
/*
    PlayerRMI getP () throws RemoteException;

    void setP (PlayerRMI pi) throws RemoteException;

 */
}
