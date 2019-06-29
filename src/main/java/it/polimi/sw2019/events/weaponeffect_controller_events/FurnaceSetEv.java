package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Furnace, the basic effect of Furnace
 */
public class FurnaceSetEv {
    private String room;

    /**
     * @param room is the room selected as target, must be one of those contained in the rooms list of the choose event, can be null if there isn't available rooms
     */
    public FurnaceSetEv(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }
}
