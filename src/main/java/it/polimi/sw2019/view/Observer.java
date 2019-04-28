package it.polimi.sw2019.view;

/**
 * this class implements the Observer
 * @param <T> the type of the parameter
 * @author Luca Giacometti
 */
public interface Observer <T> {

     /**
      * this method initializes the updating
      * @param message the object which have to be updated
      */
     void update(T message);
}
