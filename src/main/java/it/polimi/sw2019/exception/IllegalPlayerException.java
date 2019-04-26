package it.polimi.sw2019.exception;

/**
 * @author Mattia Iamundo
 */
public class IllegalPlayerException extends Exception{
    private final ErrorCode code;
    public IllegalPlayerException(String s, ErrorCode code){
        super(s);
        this.code = code;
    }

    public ErrorCode getCode(){
        return code;
    }
}
