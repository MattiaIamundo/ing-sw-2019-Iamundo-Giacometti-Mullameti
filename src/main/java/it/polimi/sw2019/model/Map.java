package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidSpaceException;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * this class is stand of the game's maps
 * @author Luca Giacometti
 */
public class Map implements Serializable {
    //list is set to 4 because the map is a 4 x-coordinate x 3 y-coordinate
    private  ArrayList <ArrayList <Space>> list = new ArrayList <> (4);
    // this: ArrayList<Space> innerList0 = new ArrayList<Space>(3);
    //is the constructor's parameters form
    /**
     * this is the constructor
     * @param innerList0 the list of spaces who have x-coordinate equals 0
     * @param innerList1 the list of spaces who have x-coordinate equals 1
     * @param innerList2 the list of spaces who have x-coordinate equals 2
     * @param innerList3 the list of spaces who have x-coordinate equals 3
     */
    public Map (ArrayList<Space> innerList0, ArrayList<Space> innerList1,
                ArrayList<Space> innerList2, ArrayList<Space> innerList3){
        this.list.add(0,innerList0);
        this.list.add(1,innerList1);
        this.list.add(2,innerList2);
        this.list.add(3,innerList3);
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

    public int[] getCoordinates(Space position) throws InvalidSpaceException {
        int x = 0;
        int y = 0;
        int[] coordinates = {x, y};

        for (x = 0; x < list.size(); x++) {
            for (y = 0; y < list.get(x).size(); y++) {
                if (position == getSpace(x, y)) {
                    return coordinates;
                }
            }
        }
        throw new InvalidSpaceException("space not exist");
    }

    public int getMaxX(){
        return list.size();
    }

    public int getMaxY(){
        return list.get(0).size();
    }
}
