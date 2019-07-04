package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestGameClass {
    Logger logger = Logger.getLogger("test.Game");
    Game controller;

    @Before
    public void setUp(){
        controller = new Game();
    }

    @Test
    public void testCreatePowerUp() {

        controller.createPowerUp();

        for(PowerUp pow : controller.getGameboard().getPowerUp()) {

            System.out.println(pow.getColor());
            System.out.println(pow.getName());
            System.out.println(pow.getEffectToString());

        }

        System.out.println(controller.getGameboard().getPowerUp().size());
    }

    @Test
    public void testCreateWeapon() {


        controller.createWeapon();

        for (Weapon pon : controller.getGameboard().getWeapon()) {

            System.out.println(pon.getName());
            System.out.println(pon.getPower());
            System.out.println(pon.getDescriptionPower());
            System.out.println(pon.getCost());
        }

        controller.createWeapon();

        System.out.println(controller.getGameboard().getWeapon().size());
    }

    @Test
    public void testCreateAmmo() {


        controller.createAmmo();

        for ( Ammo am : controller.getGameboard().getAmmo() ) {
            System.out.println(am.getColorFirst());
            System.out.println(am.getColorSecond());
            System.out.println(am.getImageName());
        }

        controller.createAmmo();
    }

    @Test
    public void testcreateMap1() {


        controller.createMap("zero");
        Assert.assertNotNull(controller.getGameboard().getMap());

    }

    @Test
    public void testcreateMap2() {


        controller.createMap("one");
        Assert.assertNotNull( controller.getGameboard().getMap() );

    }

    @Test
    public void testcreateMap3() {


        controller.createMap("two");
        Assert.assertNotNull( controller.getGameboard().getMap() );

    }

    @Test
    public void testcreateMap4() {


        controller.createMap("three");
        Assert.assertNotNull( controller.getGameboard().getMap() );
    }

    @Test
    public void searchPlayerTest(){
        Player returnedPlayer;

        controller.addPlayers("attacker");
        returnedPlayer = controller.searchPlayer("attacker");

        Assert.assertEquals(returnedPlayer, controller.getPlayers().get(0));
    }

    @Test
    public void setAmmo0Test(){
        controller.createMap("zero");
        controller.createAmmo();
        controller.setAmmo("zero");

        try {
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 2)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(3, 2)).takeAmmo());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    @Test
    public void setAmmo1Test(){
        controller.createMap("one");
        controller.createAmmo();
        controller.setAmmo("one");

        try {
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 2)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 1)).takeAmmo());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    @Test
    public void setAmmo2Test(){
        controller.createMap("two");
        controller.createAmmo();
        controller.setAmmo("two");

        try {
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(2, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 2)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 1)).takeAmmo());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    @Test
    public void setAmm30Test(){
        controller.createMap("three");
        controller.createAmmo();
        controller.setAmmo("three");

        try {
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(2, 1)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(0, 2)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 0)).takeAmmo());
            Assert.assertNotNull(((SpaceAmmo) controller.getGameboard().getMap().getSpace(1, 1)).takeAmmo());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    @Test
    public void setWeaponTest(){
        controller.createMap("zero");
        controller.createWeapon();
        controller.setWeapon();

        try {
            Assert.assertNotNull(((SpaceGeneration) controller.getGameboard().getMap().getSpace(0,1)).listWeapon());
            Assert.assertNotNull(((SpaceGeneration) controller.getGameboard().getMap().getSpace(2,2)).listWeapon());
            Assert.assertNotNull(((SpaceGeneration) controller.getGameboard().getMap().getSpace(3,0)).listWeapon());

        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map");
        }
    }
}
