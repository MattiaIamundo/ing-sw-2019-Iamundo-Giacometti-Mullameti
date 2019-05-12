package it.polimi.sw2019.model;

import java.io.Serializable;

/**Class Connection : describes the connection between to spaces , the possibility to move from one to the other
 * @author Merita Mullameti
 */
public class Connection implements Serializable {
    //the space the player's in
    private Space spaceFirst;
    //the space the player wants to move to
    private Space spaceSecond;
    //boolean variable to decide if there is a wall or not , if there is possibile to move from spaceFirst to spaceSecond
    private boolean isWall;
    /**Constructor of the class
     * @param spaceFirst the space the player's in
     * @param spaceSecond the space the player wants to move to
     * @param isWall true if there is a wall and false if there isn't
     */

    public Connection(Space spaceFirst , Space spaceSecond , boolean isWall){
        this.spaceFirst=spaceFirst;
        this.spaceSecond=spaceSecond;
        this.isWall=isWall;
    }

    /**
     *@return the space the player's in
     */
    public Space getSpaceFirst(){
        return spaceFirst;
    }
    /**
     *@return the space the player wants to move to
     */
    public Space getSpaceSecond(){
        return spaceSecond;
    }
    /**
     *@return true if there is a wall and false if there isn't
     */
    public boolean isWall(){
        return isWall;
    }
}
