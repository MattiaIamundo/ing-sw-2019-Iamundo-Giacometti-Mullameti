package it.polimi.sw2019.exception;

/**
 * this exception identifies an incorrect space
 * @author Luca Giacometti
 */
public class InvalidSpaceException extends Exception {
    public InvalidSpaceException (String s){
        super(s);
    }
}
