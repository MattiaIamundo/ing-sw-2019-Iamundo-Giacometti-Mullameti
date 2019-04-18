package it.polimi.sw2019.exception;

/**
 * @author Mattia Iamundo
 */
public class IllegalPlayerexception extends Exception{
    private final ErrorCode code;
    public IllegalPlayerexception(String s, ErrorCode code){
        super(s);
        this.code = code;
    }

    public ErrorCode getCode(){
        return code;
    }
}
