package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestAlternativeClass {

    @Test
    public void testAlternative (){

        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , "blu" , "descriptionAlternativePower" );

        Assert.assertEquals("name", weapon.getName());
        Assert.assertNull(weapon.getPower());
        Assert.assertEquals("descriptionPower", weapon.getDescriptionPower());
        Assert.assertNull(weapon.getAlternativePower());
        Assert.assertEquals("blu", weapon.getExtraCost());
        Assert.assertEquals("descriptionAlternativePower", weapon.getDescriptionAlternativePower());
    }

    @Test
    public void testGetExtraCost (){

        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , "blu" , "descriptionAlternativePower" );

        Assert.assertEquals("blu", weapon.getExtraCost());
    }

    @Test
    public void testGetDescriptionAlternativePower (){

        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , "blu" , "descriptionAlternativePower" );

        Assert.assertEquals("descriptionAlternativePower",weapon.getDescriptionAlternativePower());
    }

    @Test
    public void testGetAlternativePower (){

        Alternative weapon = new Alternative("name", null , "descriptionPower",
                null , "blu" , "descriptionAlternativePower" );

        Assert.assertEquals(null, weapon.getAlternativePower());
    }
}
