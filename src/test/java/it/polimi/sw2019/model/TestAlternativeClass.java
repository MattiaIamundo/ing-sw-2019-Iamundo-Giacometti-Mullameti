package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestAlternativeClass {

    @Test
    public void testAlternative (){
        String[] s = {"blue", "null"};
        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , s , "descriptionAlternativePower",null );

        Assert.assertEquals("name", weapon.getName());
        Assert.assertNull(weapon.getPower());
        Assert.assertEquals("descriptionPower", weapon.getDescriptionPower());
        Assert.assertNull(weapon.getAlternativePower());
        Assert.assertArrayEquals(s, weapon.getExtraCost());
        Assert.assertEquals("descriptionAlternativePower", weapon.getDescriptionAlternativePower());
    }

    @Test
    public void testGetExtraCost (){
        String[] s = {"blue", "null"};
        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , s, "descriptionAlternativePower",null );

        Assert.assertArrayEquals(s, weapon.getExtraCost());
    }

    @Test
    public void testGetDescriptionAlternativePower (){
        String[] s = {"blue", "null"};
        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , s , "descriptionAlternativePower",null );

        Assert.assertEquals("descriptionAlternativePower",weapon.getDescriptionAlternativePower());
    }

    @Test
    public void testGetAlternativePower (){
        String[] s = {"blue", "null"};
        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , s , "descriptionAlternativePower",null );

        Assert.assertEquals(null, weapon.getAlternativePower());
    }
}
