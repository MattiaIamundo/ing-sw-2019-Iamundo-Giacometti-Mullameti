package it.polimi.sw2019.utility;

import java.util.List;

public class SpaceJson {

    private String typeSpace;
    private String room;
    private List<ConnectionJson> connectionJsons;

    public String getTypeSpace() {
        return typeSpace;
    }

    public String getRoom() {
        return room;
    }

    public List<ConnectionJson> getConnectionJsons() {
        return connectionJsons;
    }
}
