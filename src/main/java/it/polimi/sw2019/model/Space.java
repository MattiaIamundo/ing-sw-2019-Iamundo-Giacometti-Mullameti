package it.polimi.sw2019.model;
/**
 * this class indicates the table's squares
 */
public abstract class Space {

    private Connection nord;
    private Connection est;
    private Connection sud;
    private Connection ovest;
    private String room;

    /**
     * @author Luca Giacometti
     * @param nord
     * @param est
     * @param sud
     * @param ovest
     * @param room
     * this is the constructor
     */
    public Space (Connection nord, Connection est, Connection sud, Connection ovest, String room){

        this.nord = nord;
        this.est = sud;
        this.sud = sud;
        this.ovest = ovest;
        this.room = room;
    }
    /**
     * @return the nord adjacent space
     */
    public Connection getNord() {
        return nord;
    }
    /**
     * @return the est adjacent space
     */
    public Connection getEst() {
        return est;
    }
    /**
     * @return the ovest adjacent space
     */
    public Connection getOvest() {
        return ovest;
    }
    /**
     * @return the sud adjacent space
     */
    public Connection getSud() {
        return sud;
    }
    /**
     * @return the room where the space is in
     */
    public String getRoom() {
        return room;
    }
}
