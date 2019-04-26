package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luca Giacometti
 */
public class TestSkullClass {

    @Test
    public void testSkull (){
        Skull skull = new Skull();

        Assert.assertTrue( skull.getIsPresent());
        Assert.assertFalse(skull.getIsOverkilled());
        Assert.assertNull(skull.getKiller());
    }

    @Test
    public void testSetKiller (){

        Skull skull = new Skull();
        Player killer = new Player("name", 0, null, null);

        skull.setKiller( killer );
        Assert.assertEquals(killer, skull.getKiller());
    }

    @Test
    public void testGetKiller (){

        Skull skull = new Skull();
        Player killer = new Player("name", 0, null, null);
        skull.setKiller(killer);

        Assert.assertEquals(killer, skull.getKiller());

    }

    @Test
    public void testSetIsPresent (){

        Skull skull = new Skull();

        skull.setIsPresent();
        Assert.assertFalse(skull.getIsPresent());
    }

    @Test
    public void testGetIsPresent (){

        Skull skull = new Skull();

        Assert.assertTrue(skull.getIsPresent());
    }

    @Test
    public void testSetIsOverkilled (){

        Skull skull = new Skull();

        skull.setIsOverkilled();
        Assert.assertTrue(skull.getIsOverkilled());
    }

    @Test
    public void testGetIsOverkilled (){

        Skull skull = new Skull();

        Assert.assertFalse(skull.getIsOverkilled());
    }
}
