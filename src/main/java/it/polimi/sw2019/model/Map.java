package it.polimi.sw2019.model;

import java.util.ArrayList;
/**
 * this class is stand of the game's maps
 * @author Luca Giacometti
 */
public class Map {
    //list is set to 4 because the map is a 4 x-coordinate x 3 y-coordinate
    private static ArrayList <ArrayList <Space>> list = new ArrayList <> (4);
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
     * @param x it is the x-coordinate of the map
     * @param y it is the y-coordinate of the map
     * @return the space having x and y coordinates
     */
    public static Space getSpace(Integer x , Integer y) {
        return (list.get(x)).get(y);
    }
}