package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestDoubleAdditive {

    @Test
    public void testDoubleAdditive() {

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals("name", weapon.getName());
        Assert.assertNull(weapon.getPower());
        Assert.assertEquals("descriptionPower", weapon.getDescriptionPower());
        Assert.assertNull(weapon.getFirstAdditivePower());
        Assert.assertEquals("green", weapon.getFirstExtraCost());
        Assert.assertEquals( null, weapon.getSecondAdditivePower());
        Assert.assertEquals("red", weapon.getSecondExtraCost());
        Assert.assertEquals( "descriptionFirstAdditivePower", weapon.getDescriptionFirstAdditivePower());
        Assert.assertEquals( "descriptionSecondAdditivePower", weapon.getDescriptionSecondAdditivePower());
    }

    @Test
    public void testGetFirstExtraCost (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals("green", weapon.getFirstExtraCost());
    }

    @Test
    public void testGetSecondExtraCost (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals("red", weapon.getSecondExtraCost());
    }

    @Test
    public void testGetDescriptionFirstAdditivePower (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals("descriptionFirstAdditivePower", weapon.getDescriptionFirstAdditivePower());
    }

    @Test
    public void testGetDescriptionSecondAdditivePower (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals("descriptionSecondAdditivePower", weapon.getDescriptionSecondAdditivePower());
    }

    @Test
    public void testGetFirstAdditivePower (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals( null, weapon.getFirstAdditivePower());
    }

    @Test
    public void testGetSecondAdditivePower (){

        DoubleAdditive weapon = new DoubleAdditive (
                "name", null, "descriptionPower",
                null, "green", null,
                "red", "descriptionFirstAdditivePower",
                "descriptionSecondAdditivePower",null);

        Assert.assertEquals( null, weapon.getSecondAdditivePower());
    }
}
