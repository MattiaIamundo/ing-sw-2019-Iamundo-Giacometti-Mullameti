package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestAdditiveClass {

    @Test
    public void testAdditive (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertEquals("name", weapon.getName());
        Assert.assertNull(weapon.getPower());
        Assert.assertEquals("descriptionPower", weapon.getDescriptionPower());
        Assert.assertNull(weapon.getAdditivePower());
        Assert.assertEquals("red", weapon.getAdditiveCost());
        Assert.assertEquals( "descriptionAdditivePower", weapon.getDescriptionAdditivePower());
    }


    @Test
    public void testGetName (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertEquals("name", weapon.getName());
    }

    //we have to decide how get the data
    /*public void testGetCost (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower");

    }*/

    @Test
    public void testGetDescriptionPower (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertEquals("descriptionPower", weapon.getDescriptionPower());
    }

    @Test
    public void testGetIsLoad (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertTrue(weapon.getIsLoad());
    }

    @Test
    public void testGetPower (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertNull(weapon.getPower());
    }

    @Test
    public void testGetAdditiveCost (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertEquals("red",weapon.getAdditiveCost());
    }

    @Test
    public void testGetDescriptionAdditivePower (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertEquals("descriptionAdditivePower", weapon.getDescriptionAdditivePower());
    }

    @Test
    public void testGetAdditivePower (){

        Additive weapon = new Additive( "name", null, "descriptionPower",
                null, "red","descriptionAdditivePower",null);

        Assert.assertNull(weapon.getPower());
    }
}
