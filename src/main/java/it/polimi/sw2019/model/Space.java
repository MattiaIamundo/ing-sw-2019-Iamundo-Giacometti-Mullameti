package it.polimi.sw2019.model;

import java.io.Serializable;

/**
 * this class indicates the table's squares
 * @author Luca Giacometti
 */
public abstract class Space implements Cloneable, Serializable {

    private Connection north;
    private Connection east;
    private Connection south;
    private Connection west;
    private String room;

    /**
     * this is the constructor
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
     * north space's getter
     * @return the north adjacent space
     */
    public Connection getNorth() {
        return north;
    }

    /**
     * north space's setter
     * @param c the connection to set
     */
    public void setNorth(Connection c) {
        this.north = c;
    }

    /**
     * east connection's getter
     * @return the east adjacent space
     */
    public Connection getEast() {
        return east;
    }

    /**
     * east's connection setter
     * @param c the connection to set
     */
    public void setEast(Connection c) {
        this.east = c;
    }

    /**
     * west connection's getter
     * @return the west adjacent connection
     */
    public Connection getWest() {
        return west;
    }

    /**
     * south connection' setter
     * @param c the connection to set
     */
    public void setSouth(Connection c) {
        this.south = c;
    }

    /**
     * south connection's getter
     * @return the sud adjacent space
     */
    public Connection getSouth() {
        return south;
    }

    /**
     * west connection's setter
     * @param c the connection to set
     */
    public void setWest(Connection c) {
        this.west = c;
    }

    /**
     * room's getter
     * @return the color of the room where the space is in
     */
    public String getRoom() {
        return room;
    }
}
