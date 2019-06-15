package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestSpaceAmmoClass {

    @Test
    public void testSpaceAmmo(){
        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);

        Assert.assertNull(spaceAmmo.getNorth());
        Assert.assertNull(spaceAmmo.getEast());
        Assert.assertNull(spaceAmmo.getSouth());
        Assert.assertNull(spaceAmmo.getWest());
        Assert.assertNull(spaceAmmo.getRoom());
        Assert.assertNull(spaceAmmo.takeAmmo());

    }

    @Test
    public void testGetNorth() {

        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.getNorth());
    }

    @Test
    public void testGetEast() {

        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.getEast());
    }

    @Test
    public void testGetSouth() {

        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.getSouth());
    }

    @Test
    public void testGetWest() {

        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.getWest());
    }

    @Test
    public void testGetRoom() {

        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.getRoom());
    }

    @Test
    public void testTakeAmmo(){
        SpaceAmmo spaceAmmo = new SpaceAmmo(null, null , null ,null ,null);
        Assert.assertNull(spaceAmmo.takeAmmo());
    }

    //to be continued...
}
