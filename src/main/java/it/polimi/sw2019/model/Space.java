package it.polimi.sw2019.model;
/**
 * this class indicates the table's squares
 */
public abstract class Space {

    private Connection north;
    private Connection east;
    private Connection south;
    private Connection west;
    private String room;

    /**
     * @author Luca Giacometti
     * @param north
     * @param east
     * @param south
     * @param west
     * @param room
     * this is the constructor
     */
    public Space (Connection north, Connection east, Connection south, Connection west, String room){

        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.room = room;
    }
    /**
     * @return the nord adjacent space
     */
    public Connection getNorth() {
        return north;
    }
    /**
     * @return the est adjacent space
     */
    public Connection getEast() {
        return east;
    }
    /**
     * @return the ovest adjacent space
     */
    public Connection getWest() {
        return west;
    }
    /**
     * @return the sud adjacent space
     */
    public Connection getSouth() {
        return south;
    }
    /**
     * @return the room where the space is in
     */
    public String getRoom() {
        return room;
    }
}
