package it.polimi.sw2019.exception;


/**
 * This exception identifies a Typo in the name of the direction
 * @author Mattia Iamundo
 */
public class InvalidDirectionException extends Exception{
    public InvalidDirectionException(String s){
        super(s);
    }
}
