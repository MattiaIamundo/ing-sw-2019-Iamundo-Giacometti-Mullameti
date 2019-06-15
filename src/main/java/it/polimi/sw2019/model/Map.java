package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.InvalidSpaceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is stand of the game's maps
 * @author Luca Giacometti
 */
public class Map implements Serializable {
    //list is set to 4 because the map is a 4 x-coordinate x 3 y-coordinate
    private List <List<Space>> list = new ArrayList <> (4);

    //is the constructor's parameters form
    /**
     * this is the constructor
     * @param innerList0 the list of spaces who have x-coordinate equals 0
     * @param innerList1 the list of spaces who have x-coordinate equals 1
     * @param innerList2 the list of spaces who have x-coordinate equals 2
     * @param innerList3 the list of spaces who have x-coordinate equals 3
     */
    public Map (List<Space> innerList0, List<Space> innerList1, List<Space> innerList2, List<Space> innerList3){
        list.add(0,innerList0);
        list.add(1,innerList1);
        list.add(2,innerList2);
        list.add(3,innerList3);
    }

    /**
     * this method return the single space selected by player
     * @param x it is the x-coordinate of the map
     * @param y it is the y-coordinate of the map
     * @return the space having x and y coordinates
     * @throws InvalidSpaceException exception due to coordinates being out of the considered area
     */
    public Space getSpace(int x , int y) throws InvalidSpaceException {

        if ( x > 3 && y <= 2 )
            throw new InvalidSpaceException("xout");
        else if ( x <= 3 && y > 2 )
            throw new InvalidSpaceException("yout");
        else if (x > 3 && y > 2 )
            throw new InvalidSpaceException("xyout");
        else if (x < 0 || y < 0)
            throw new InvalidSpaceException("neg");
        else
            return (list.get(x)).get(y);
    }
}
