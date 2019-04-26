package it.polimi.sw2019.exception;

/**
 * This exception manage the case in which the user select an existing direction, but that is headed to a wall
 * @author Mattia Iamundo
 */
public class IllegalDirectionException extends Exception{
    public IllegalDirectionException(String s){
        super(s);
    }

    public IllegalDirectionException(){
        super();
    }

}
