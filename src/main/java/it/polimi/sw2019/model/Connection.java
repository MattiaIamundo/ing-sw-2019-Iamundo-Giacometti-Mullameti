package it.polimi.sw2019.model;

public class Connection {
    private Space spaceFirst;
    private Space spaceSecond;
    private boolean isWall;

    public Space getSpaceFirst(){
        return spaceFirst;
    }

    public Space getSpaceSecond(){
        return spaceSecond;
    }

    public boolean isWall(){
        return isWall;
    }
}
