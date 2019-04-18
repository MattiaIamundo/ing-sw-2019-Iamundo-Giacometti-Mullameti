package it.polimi.sw2019.exception;

/**
 * @author Mattia Iamundo
 */
public class UnreachablePlayerException extends Exception{

    /**
     * this exception is caused by selecting an existing player that isn't in the range of the power actually used
     * @param s it's the nickname of the player
     */
    public UnreachablePlayerException(String s){
        super(s);
    }
}
