package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class TestPlayerPlanceClass {

    @Test
    public void testPlayerPlance(){

        PlayerPlance board = new PlayerPlance();

        Assert.assertFalse(board.getFirstAdrenaline());
        Assert.assertFalse(board.getSecondAdrenaline());
        Assert.assertFalse(board.getIsKilled());
        Assert.assertFalse(board.getOverkilled());

    }

    //set killed to be test

    @Test
    public void testGetKilled(){

        PlayerPlance board = new PlayerPlance();
        board.setKilled(0);
        board.setKilled(1);
        board.setKilled(2);
        board.setKilled(3);
        board.setKilled(4);
        board.setKilled(5);
        boolean[] killed = board.getKilled();

        Assert.assertTrue( killed[0]  );
        Assert.assertTrue( killed[1]  );
        Assert.assertTrue( killed[2]  );
        Assert.assertTrue( killed[3]  );
        Assert.assertTrue( killed[4]  );
        Assert.assertTrue( killed[5]  );

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

    //it has to have an exception
    @Test
    public void testGiveDamage() {

        PlayerPlance board = new PlayerPlance();
        AmmoTriple ammo = new AmmoTriple("red", "blu", "green", "name");
        SpaceAmmo position = new SpaceAmmo (null, null, null, null, "red");
        Player shooter = new Player("name", 0, position, board);
        Player player = new Player("player", 0, position, null);
        ArrayList<String> trackDamage = new ArrayList<>();
        trackDamage.add("name");
        trackDamage.add("name");
        trackDamage.add("name");
        trackDamage.add("name");

        board.giveDamage( shooter, 4);
        Assert.assertArrayEquals( board.getDamageTrack().toArray(), trackDamage.toArray());

        trackDamage.add("player");
        trackDamage.add("player");
        trackDamage.add("player");

        board.giveDamage( player,3);
        Assert.assertArrayEquals( board.getDamageTrack().toArray(), trackDamage.toArray());

        trackDamage.add("name");
        trackDamage.add("name");
        trackDamage.add("name");
        trackDamage.add("name");
        trackDamage.add("name");

        board.giveDamage( shooter, 5);
        Assert.assertArrayEquals( board.getDamageTrack().toArray(), trackDamage.toArray());
        //we have to implement the exception
        //to go out the bounds
    }

    //to be continued
}
