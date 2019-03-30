package it.polimi.sw2019.model;

public abstract class Space {

    private Space nord;
    private Space est;
    private Space sud;
    private Space ovest;
    private String room;

    public Space getNord() {
        return nord;
    }

    public Space getEst() {
        return est;
    }

    public Space getOvest() {
        return ovest;
    }

    public Space getSud() {
        return sud;
    }

    public String getRoom() {
        return room;
    }
}
