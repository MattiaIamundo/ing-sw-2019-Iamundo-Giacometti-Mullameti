package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;

import java.util.ArrayList;

/**
 * this class implements the Observable
 * @param <T> the type of the parameter
 * @author Luca Giacometti
 */
public class Observable <T> {
    //this is the list of the observer.
    private ArrayList < Observer <T> > observers = new ArrayList<>(5);

    /**
     * this method adds an observer to the list
     * @param newObserver is the new observer to insert in the list
     */
    public void addObserver(Observer newObserver){
        //stopping the variable players
        synchronized (observers) {

            observers.add(newObserver);
        }
    }

    /**
     * this method removes an observer form the list
     * @param removeObserver is the observer to remove
     */
    public void removeObserver(Observer removeObserver){
        //stopping the variable players
        synchronized (observers) {

            observers.remove(removeObserver);
        }
    }

    /**
     * this methods make an update to the message
     * @param message the variable to be update
     */
    protected void notify(T message) {
        //stopping the variable players
        synchronized (observers) {

            for(Observer<T> observer : observers){
                //Observer or observer normal??
                observer.update(message);
            }
        }
    }

}
