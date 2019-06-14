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

    public void setNorth(Connection c) {
        this.north = c;
    }
    /**
     * @return the east adjacent space
     */
    public Connection getEast() {
        return east;
    }

    public void setEast(Connection c) {
        this.east = c;
    }

    /**
     * @return the oveast adjacent space
     */
    public Connection getWest() {
        return west;
    }

    public void setSouth(Connection c) {
        this.south = c;
    }

    /**
     * @return the sud adjacent space
     */
    public Connection getSouth() {
        return south;
    }

    public void setWest(Connection c) {
        this.west = c;
    }

    /**
     * @return the room where the space is in
     */
    public String getRoom() {
        return room;
    }
}
