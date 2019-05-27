package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestAmmoDoublePowerUpClass {

    @Test
    public void testAmmoDoublePowerUp(){

        AmmoDoublePowerUp ammoCard= new AmmoDoublePowerUp("blue" ,"yellow" ,null,"name");
        Assert.assertNull(ammoCard.getPowerUp());

    }

    @Test
    public void testGetPowerUp(){

        AmmoDoublePowerUp ammoCard= new AmmoDoublePowerUp("blue" ,"yellow" ,null,"name");
        Assert.assertNull(ammoCard.getPowerUp());
    }
}
