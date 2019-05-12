package it.polimi.sw2019.model;

import java.io.Serializable;

/**
 * this class indicates the table's squares
 * @author Luca Giacometti
 */
public abstract class Space implements Serializable {

    private Connection north;
    private Connection east;
    private Connection south;
    private Connection west;
    private String room;

    /**
     * @param north the north connection
     * @param east the east connection
     * @param south the south connection
     * @param west the west connection
     * @param room the space's room
     */
    public Space (Connection north, Connection east, Connection south, Connection west, String room){

        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.room = room;
    }
    /**
     * @return the north adjacent space
     */
    public Connection getNorth() {
        return north;
    }
    /**
     * @return the east adjacent space
     */
    public Connection getEast() {
        return east;
    }
    /**
     * @return the oveast adjacent space
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
