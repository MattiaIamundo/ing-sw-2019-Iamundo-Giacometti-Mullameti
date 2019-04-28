package it.polimi.sw2019.model;


import org.junit.Assert;
import org.junit.Test;


public class TestPowerUpClass {

    @Test
    public void testPowerUp()
    {
        PowerUp card = new PowerUp("blue", "name", null);

        Assert.assertEquals("blue" , card.getColor());
        Assert.assertEquals("name" , card.getName());

    }

    @Test
    public void  testGetColor(){

        PowerUp card = new PowerUp("blue", "name", null);

        Assert.assertEquals("blue" , card.getColor());
    }

    @Test
    public void  testGetName(){

        PowerUp card = new PowerUp("blue", "name", null);

        Assert.assertEquals("name" , card.getName());
    }
}

