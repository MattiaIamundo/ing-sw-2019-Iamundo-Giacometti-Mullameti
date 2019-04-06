package it.polimi.sw2019.model;

public abstract class Space {

    private Connection nord;
    private Connection est;
    private Connection sud;
    private Connection ovest;
    private String room;

    public Connection getNord() {
        return nord;
    }

    public Connection getEst() {
        return est;
    }

    public Connection getOvest() {
        return ovest;
    }

    public Connection getSud() {
        return sud;
    }

    public String getRoom() {
        return room;
    }
}
