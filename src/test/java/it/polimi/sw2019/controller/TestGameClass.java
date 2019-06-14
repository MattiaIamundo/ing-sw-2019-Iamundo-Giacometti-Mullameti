package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.Weapon;
import org.junit.Assert;
import org.junit.Test;

public class TestGameClass {

    @Test
    public void testCreatePowerUp() {
        Game controller = new Game();

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

        Game controller = new Game();

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

        Game controller = new Game();

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

        Game controller = new Game();

        controller.createMap(0);
        Assert.assertNotNull(controller.getGameboard().getMap());

    }

    @Test
    public void testcreateMap2() {

        Game controller = new Game();

        controller.createMap(1);
        Assert.assertNotNull( controller.getGameboard().getMap() );

    }

    @Test
    public void testcreateMap3() {

        Game controller = new Game();

        controller.createMap(2);
        Assert.assertNotNull( controller.getGameboard().getMap() );

    }

    @Test
    public void testcreateMap4() {

        Game controller = new Game();

        controller.createMap(3);
        Assert.assertNotNull( controller.getGameboard().getMap() );
    }
}
