package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.weaponeffect.EffectController;
import it.polimi.sw2019.events.weapon_event.PowerChooseEv;
import it.polimi.sw2019.events.weapon_event.PowerSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.PowerUpOutOfBoundException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.utility.SimplifiedPowerUp;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeaponEffectManagerTest {
    Logger logger = Logger.getLogger("test.WeaponEffectManager");

    @Test
    public void TestInitializtaionOfEffectController(){
        WeaponEffectManager weaponEffectManager = new WeaponEffectManager(null, null, null);

        HashMap<String, EffectController> effectControllerHashMap = weaponEffectManager.getEffectControllers();
        Assert.assertEquals(45, effectControllerHashMap.size());
    }

    @Test
    public void acquirePowerAlternative(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        Catcher catcher = new Catcher();
        String[] basicCost = {"blue"};
        String[] extraCost = {"blue", "red"};
        ArrayList<SimplifiedPowerUp> expectedAvailablePowerUps = new ArrayList<>();
        HashMap<String, Integer> expectedAvailableAmmo = new HashMap<>();
        HashMap<String, ArrayList<String>> expectedPowers = new HashMap<>();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("red", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            Electroscythe basic = new Electroscythe();
            ReaperMode alternative = new ReaperMode();
            Alternative weapon = new Alternative("Electroscythe", basic, null, alternative, extraCost, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.addObserver(catcher);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            expectedAvailablePowerUps.add(new SimplifiedPowerUp("Teleporter", "red"));
            expectedAvailableAmmo.put("red", 1);
            expectedAvailableAmmo.put("blue", 2);
            expectedAvailableAmmo.put("yellow", 0);
            expectedPowers.put("Electroscythe", null);
            expectedPowers.put("ReaperMode", new ArrayList<>(Arrays.asList(extraCost)));

            Assert.assertTrue(catcher.message.getItsAlternative());
            Assert.assertEquals(expectedAvailablePowerUps.get(0).getName(), catcher.message.getAvailablePowerUps().get(0).getName());
            Assert.assertEquals(expectedAvailablePowerUps.get(0).getColor(), catcher.message.getAvailablePowerUps().get(0).getColor());
            Assert.assertArrayEquals(expectedAvailableAmmo.keySet().toArray(), catcher.message.getAvailableAmmo().keySet().toArray());
            Assert.assertEquals(1, catcher.message.getAvailableAmmo().get("red").intValue());
            Assert.assertEquals(2, catcher.message.getAvailableAmmo().get("blue").intValue());
            Assert.assertArrayEquals(expectedPowers.keySet().toArray(), catcher.message.getPowers().keySet().toArray());
            Assert.assertArrayEquals(extraCost, catcher.message.getPowers().get("ReaperMode").toArray());
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 PowerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 weapons");
        }
    }

    @Test
    public void acquirePowerAdditive(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        Catcher catcher = new Catcher();
        String[] basicCost = {"blue"};
        HashMap<String, Integer> expectedAvailableAmmo = new HashMap<>();
        HashMap<String, ArrayList<String>> expectedPowers = new HashMap<>();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("red", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            LockRifle lockRifle = new LockRifle();
            SecondLock secondLock = new SecondLock();
            Additive weapon = new Additive("LockRifle", lockRifle, null, secondLock, "red", null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.addObserver(catcher);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            expectedAvailableAmmo.put("red", 1);
            expectedAvailableAmmo.put("blue", 2);
            expectedAvailableAmmo.put("yellow", 0);
            expectedPowers.put("LockRifle", null);
            expectedPowers.put("SecondLock", new ArrayList<>(Arrays.asList("red")));

            Assert.assertFalse(catcher.message.getItsAlternative());
            Assert.assertEquals("Teleporter", catcher.message.getAvailablePowerUps().get(0).getName());
            Assert.assertEquals("red", catcher.message.getAvailablePowerUps().get(0).getColor());
            Assert.assertArrayEquals(expectedAvailableAmmo.keySet().toArray(), catcher.message.getAvailableAmmo().keySet().toArray());
            Assert.assertEquals(1, catcher.message.getAvailableAmmo().get("red").intValue());
            Assert.assertEquals(2, catcher.message.getAvailableAmmo().get("blue").intValue());
            Assert.assertArrayEquals(expectedPowers.keySet().toArray(), catcher.message.getPowers().keySet().toArray());
            Assert.assertArrayEquals(expectedPowers.get("SecondLock").toArray(), catcher.message.getPowers().get("SecondLock").toArray());
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 PowerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 weapons");
        }
    }

    @Test
    public void acquirePowerDoubleAdditive(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        Catcher catcher = new Catcher();
        String[] basicCost = {"blue", "red"};
        HashMap<String, Integer> expectedAvailableAmmo = new HashMap<>();
        HashMap<String, ArrayList<String>> expectedPowers = new HashMap<>();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("yellow", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            MachineGun machineGun = new MachineGun();
            FocusShot focusShot = new FocusShot();
            TurretTripod turretTripod = new TurretTripod();
            DoubleAdditive weapon = new DoubleAdditive("Machine Gun", machineGun, null, focusShot, "yellow", turretTripod, "blue", null, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.addObserver(catcher);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            expectedAvailableAmmo.put("red", 1);
            expectedAvailableAmmo.put("blue", 2);
            expectedAvailableAmmo.put("yellow", 0);
            expectedPowers.put("MachineGun", null);
            expectedPowers.put("FocusShot", new ArrayList<>(Collections.singletonList("yellow")));
            expectedPowers.put("TurretTripod", new ArrayList<>(Collections.singletonList("blue")));

            Assert.assertFalse(catcher.message.getItsAlternative());
            Assert.assertEquals("Teleporter", catcher.message.getAvailablePowerUps().get(0).getName());
            Assert.assertEquals("yellow", catcher.message.getAvailablePowerUps().get(0).getColor());
            Assert.assertArrayEquals(expectedAvailableAmmo.keySet().toArray(), catcher.message.getAvailableAmmo().keySet().toArray());
            Assert.assertEquals(1, catcher.message.getAvailableAmmo().get("red").intValue());
            Assert.assertEquals(2, catcher.message.getAvailableAmmo().get("blue").intValue());
            Assert.assertArrayEquals(expectedPowers.keySet().toArray(), catcher.message.getPowers().keySet().toArray());
            Assert.assertArrayEquals(expectedPowers.get("FocusShot").toArray(), catcher.message.getPowers().get("FocusShot").toArray());
            Assert.assertArrayEquals(expectedPowers.get("TurretTripod").toArray(), catcher.message.getPowers().get("TurretTripod").toArray());
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 PowerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "You already have 3 weapons");
        }
    }

    private class Catcher implements Observer<PowerChooseEv>{
        private PowerChooseEv message;

        @Override
        public void update(PowerChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void update_Alternative_CanPay(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        String[] basicCost = {"blue"};
        String[] extraCost = {"blue", "red"};
        ArrayList<String> choosenPowers = new ArrayList<>();

        try {
            choosenPowers.add("ReaperMode");
            Thrower thrower = new Thrower(choosenPowers, null);
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("red", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            Electroscythe basic = new Electroscythe();
            ReaperMode alternative = new ReaperMode();
            Alternative weapon = new Alternative("Electroscythe", basic, null, alternative, extraCost, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            thrower.addObserver(controller);
            thrower.throwMessage();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void update_Additive_CanPay(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        String[] basicCost = {"blue"};
        ArrayList<String> choosenPowers = new ArrayList<>();

        try {
            choosenPowers.add("LockRifle");
            choosenPowers.add("ReaperMode");
            Thrower thrower = new Thrower(choosenPowers, null);
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("red", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            LockRifle lockRifle = new LockRifle();
            SecondLock secondLock = new SecondLock();
            Additive weapon = new Additive("LockRifle", lockRifle, null, secondLock, "red", null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            thrower.addObserver(controller);
            thrower.throwMessage();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void update_DoubleAdditive_CantPay(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        String[] basicCost = {"blue", "red"};
        ArrayList<String> choosenPowers = new ArrayList<>();

        try {
            choosenPowers.add("MachineGun");
            choosenPowers.add("FocusShot");
            Thrower thrower = new Thrower(choosenPowers, null);
            game.createMap("zero");
            game.addPlayers("attacker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("yellow", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            MachineGun machineGun = new MachineGun();
            FocusShot focusShot = new FocusShot();
            TurretTripod turretTripod = new TurretTripod();
            DoubleAdditive weapon = new DoubleAdditive("Machine Gun", machineGun, null, focusShot, "yellow", turretTripod, "blue", null, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            controller.acquirePower(weapon, game.getPlayers().get(0));
            thrower.addObserver(controller);
            thrower.throwMessage();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void update_DoubleAdditive_CanPay(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        String[] basicCost = {"blue", "red"};
        ArrayList<String> chosenPowers = new ArrayList<>();
        ArrayList<SimplifiedPowerUp> chosenPowerUps = new ArrayList<>();

        try {
            chosenPowers.add("MachineGun");
            chosenPowers.add("FocusShot");
            chosenPowers.add("TurretTripod");
            chosenPowerUps.add(new SimplifiedPowerUp("Teleporter", "yellow"));
            Thrower thrower = new Thrower(chosenPowers, chosenPowerUps);
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            game.getPlayers().get(0).setPosition(game.getGameboard().getMap().getSpace(0,0));
            game.getPlayers().get(1).setPosition(game.getGameboard().getMap().getSpace(0,0));
            game.getPlayers().get(2).setPosition(game.getGameboard().getMap().getSpace(0,0));
            game.getPlayers().get(0).addPowerUp(new PowerUp("yellow", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "blue", "blue", null));
            MachineGun machineGun = new MachineGun();
            FocusShot focusShot = new FocusShot();
            TurretTripod turretTripod = new TurretTripod();
            DoubleAdditive weapon = new DoubleAdditive("Machine Gun", machineGun, null, focusShot, "yellow", turretTripod, "blue", null, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            machineGun.setTarget(game.getPlayers().get(1));
            machineGun.setTarget2(game.getPlayers().get(2));
            focusShot.setTarget(game.getPlayers().get(1));
            controller.acquirePower(weapon, game.getPlayers().get(0));
            thrower.addObserver(controller);
            thrower.throwMessage();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    @Test
    public void update_DoubleAdditive_Cyberblade_CanPay(){
        Game game = new Game();
        WeaponEffectManager controller = new WeaponEffectManager((ArrayList<Player>) game.getPlayers(), game.getGameboard().getMap(), game);
        String[] basicCost = {"yellow", "red"};
        ArrayList<String> chosenPowers = new ArrayList<>();
        ArrayList<SimplifiedPowerUp> chosenPowerUps = new ArrayList<>();

        try {
            chosenPowers.add("Shadowstep");
            chosenPowers.add("Cyberblade");
            chosenPowerUps.add(new SimplifiedPowerUp("Teleporter", "yellow"));
            Thrower thrower = new Thrower(chosenPowers, chosenPowerUps);
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.getPlayers().get(0).setPosition(game.getGameboard().getMap().getSpace(0,0));
            game.getPlayers().get(1).setPosition(game.getGameboard().getMap().getSpace(0,0));
            game.getPlayers().get(0).addPowerUp(new PowerUp("yellow", "Teleporter", new Teleporter()));
            game.getPlayers().get(0).addAmmo(new AmmoTriple("red", "yellow", "blue", null));
            Cyberblade cyberblade = new Cyberblade();
            Shadowstep shadowstep = new Shadowstep();
            SliceAndDice sliceAndDice = new SliceAndDice();
            DoubleAdditive weapon = new DoubleAdditive("Cyberblade", cyberblade, null, shadowstep, null, sliceAndDice, "yellow", null, null, basicCost);
            game.getPlayers().get(0).addWeapon(weapon);
            cyberblade.setTarget(game.getPlayers().get(1));
            shadowstep.setMoveto(game.getGameboard().getMap().getSpace(0,0));
            controller.acquirePower(weapon, game.getPlayers().get(0));
            thrower.addObserver(controller);
            thrower.throwMessage();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerUps");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map");
        }
    }

    private class Thrower extends Observable<PowerSetEv> {
        private ArrayList<String> powers;
        private ArrayList<SimplifiedPowerUp> usedPowerUps;

        public Thrower(ArrayList<String> powers, ArrayList<SimplifiedPowerUp> usedPowerUps) {
            this.powers = powers;
            this.usedPowerUps = usedPowerUps;
        }

        public void throwMessage(){
            notify(new PowerSetEv(powers, usedPowerUps));
        }
    }
}
