package it.polimi.sw2019.model;

public class ConnectionJson {

    //the space the player's in
    private String spaceFirst;
    //the space the player wants to move to
    private String spaceSecond;
    //boolean variable to decide if there is a wall or not , if there is possibile to move from spaceFirst to spaceSecond
    private boolean isWall;


    public String getSpaceSecond() {
        return spaceSecond;
    }

    public boolean isWall() {
        return isWall;
    }

    public String getSpaceFirst() {
        return spaceFirst;
    }
}
