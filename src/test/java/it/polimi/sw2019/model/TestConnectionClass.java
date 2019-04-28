package it.polimi.sw2019.model;

import org.junit.Assert;
import org.junit.Test;

public class TestConnectionClass {

    @Test
    public void testConnection(){

        Connection connection = new Connection(null , null , false);
        Assert.assertFalse(connection.isWall());
        Assert.assertNull(connection.getSpaceFirst());
        Assert.assertNull(connection.getSpaceSecond());
    }

    @Test
    public void testGetSpaceFirst(){

        Connection connection = new Connection(null , null , false);
        Assert.assertNull(connection.getSpaceFirst());
    }

    @Test
    public void testGetSpaceSecond(){

        Connection connection = new Connection(null , null , false);
        Assert.assertNull(connection.getSpaceSecond());
    }

    @Test
    public void testIsWall(){

        Connection connection = new Connection(null , null , false);
        Assert.assertFalse(connection.isWall());
    }
}
