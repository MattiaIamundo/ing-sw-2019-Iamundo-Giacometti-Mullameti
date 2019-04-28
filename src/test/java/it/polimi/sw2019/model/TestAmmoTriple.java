package it.polimi.sw2019.model;


import org.junit.Assert;
import org.junit.Test;

public class TestAmmoTriple {

    @Test
    public void testAmmoTriple(){

        AmmoTriple ammoCard = new AmmoTriple("blue" , "blue" , "red");
        Assert.assertEquals("blue" ,ammoCard.getColorFirst());
        Assert.assertEquals("blue" ,ammoCard.getColorSecond());
        Assert.assertEquals("red" ,ammoCard.getColorThird());

    }
    @Test
    public void testGetColorFirst() {

        AmmoTriple ammoCard = new AmmoTriple("blue" , "blue" , "red");
        Assert.assertEquals("blue" ,ammoCard.getColorFirst());
    }

    @Test
    public void testGetColorSecond() {

        AmmoTriple ammoCard = new AmmoTriple("blue" , "blue" , "red");
        Assert.assertEquals("blue" ,ammoCard.getColorSecond());
    }

    @Test
    public void testGetColorThird() {

        AmmoTriple ammoCard = new AmmoTriple("blue" , "blue" , "red");
        Assert.assertEquals("red" ,ammoCard.getColorThird());
    }
}

