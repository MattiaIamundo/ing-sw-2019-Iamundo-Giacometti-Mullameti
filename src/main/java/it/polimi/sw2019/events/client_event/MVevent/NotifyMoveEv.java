package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.events.NotifyReturn;

public class NotifyMoveEv extends NotifyReturn {

    private String north;
    private String east;
    private String south;
    private String west;
    private String room;

    public NotifyMoveEv(String nickname, String north, String east, String south, String west, String room) {
        this.setNickname(nickname);
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.room = room;
    }

    public String getNorth() {
        return north;
    }

    public String getEast() {
        return east;
    }

    public String getSouth() {
        return south;
    }

    public String getWest() {
        return west;
    }

    public String getRoom() {
        return room;
    }
}
