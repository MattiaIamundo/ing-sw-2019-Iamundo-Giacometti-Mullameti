package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;



public class TestPlayerPlanceClass {

    @Test
    public void testPlayerPlance(){

        PlayerPlance board = new PlayerPlance();

        Assert.assertFalse(board.getFirstAdrenaline());
        Assert.assertFalse(board.getSecondAdrenaline());
        Assert.assertFalse(board.getIsKilled());
        Assert.assertFalse(board.getOverkilled());

    }

    @Test
    public void testGetKilled(){

        PlayerPlance board = new PlayerPlance();


    }
    @Test
    public void testGetFirstAdrenaline(){

        PlayerPlance board = new PlayerPlance();
        Assert.assertFalse(board.getFirstAdrenaline());
    }

    @Test
    public void testGetSecondAdrenaline(){

        PlayerPlance board = new PlayerPlance();
        Assert.assertFalse(board.getSecondAdrenaline());
    }

    @Test
    public void testGetIsKilled(){

        PlayerPlance board = new PlayerPlance();
        Assert.assertFalse(board.getIsKilled());
    }

    @Test
    public void testGetOverKilled(){

        PlayerPlance board = new PlayerPlance();
        Assert.assertFalse(board.getOverkilled());
    }

   //to be continued...
}
